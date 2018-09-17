package com.karacraft.ribsncuts;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.karacraft.ribsncuts.adapter.CartItemListAdapter;
import com.karacraft.ribsncuts.cart.Controller;
import com.karacraft.ribsncuts.helper.AsyncHttpConnectionTask;
import com.karacraft.ribsncuts.helper.Constants;
import com.karacraft.ribsncuts.helper.CustomToast;
import com.karacraft.ribsncuts.helper.RequestMode;
import com.karacraft.ribsncuts.helper.RequestType;
import com.karacraft.ribsncuts.helper.SharePref;
import com.karacraft.ribsncuts.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.karacraft.ribsncuts.helper.Constants.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartActivity extends Fragment implements CartItemListAdapter.IDataChangeListener,AsyncHttpConnectionTask.AsyncHCTCallback
{

    CartItemListAdapter adapter;
    ICartOperations myInterface;

    ProgressDialog progressDialog;      //Progress Dialog to be shown during long operations

    ListView lv_cart_items;
    Controller controller;

    TextView tv_CartTotalItems;
    TextView tv_CartTotalInPkr;

    Button btnCartClear;
    Button btnCartPlaceOrder;

    public CartActivity()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {
        //Setup Title
        getActivity().setTitle("Shopping Cart");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        lv_cart_items = view.findViewById(R.id.lv_cart_items);
        lv_cart_items.setVisibility(View.VISIBLE);

        tv_CartTotalInPkr = view.findViewById(R.id.tv_cart_total_in_pkr);
        tv_CartTotalItems = view.findViewById(R.id.tv_cart_total_items);
        //Update the Text in UpdateListView

        /** Get Global Controller Class object (See application tag in AndroidManifest.xml )*/
        controller = (Controller) getActivity().getApplicationContext();

        /** Setup Custom Adapter */
        adapter = new CartItemListAdapter(getContext(),controller.getCartContents());
        adapter.setOnDataChangeListener(this);  //For Listener

        updateListView();

        btnCartClear = view.findViewById(R.id.btnClearMainCart);
        btnCartPlaceOrder = view.findViewById(R.id.btnCartPlaceOrder);

        btnCartClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.clearCart(); //Clear the Cart
                myInterface.OnCartUpdate(controller.getCartSize()); //Update the Cart View
                updateListView(); //Update The ListView
            }
        });

        btnCartPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder();
            }
        });

        return  view;
    }

    public void placeOrder()
    {
        SharePref g = SharePref.getInstance(getContext());
        String token = g.getString(Constants.APP_TOKEN);

        if (token.isEmpty())
        {
            CustomToast.showToastMessage("Please Login to RibsnCuts server first, then place order",getContext(),Toast.LENGTH_SHORT);
            return;
        }
        //Enable Progress Dialog
        enableProgressDialog("Posting Order...Please wait");

        try {

            //JSonArray to hold all the items of Cart
            JSONArray jArrayOrderDetails = new JSONArray();
            //Add all of these items to jArrayOrderDetails
            for (Item item : controller.getCartContents())
            {
                JSONObject order_detail = new JSONObject();
                order_detail.put("id",item.getId());
                order_detail.put("name",item.getName());
                order_detail.put("qty", item.getQty());
                order_detail.put("price",item.getPrice());
                jArrayOrderDetails.put(order_detail);
            }
            //Create new Object to hold Order Data & jArrayOrderDetails
            JSONObject orders = new JSONObject();
            orders.put("subtotal",controller.getTotalPrice());
            orders.put("grandtotal",controller.getTotalPrice());
            orders.put("status","Pending");
            orders.put("city","Multan");
            orders.put("order_details",jArrayOrderDetails);

            Log.d(TAG, "placeOrder: " + orders);

            /** Call the AsyncHttpConnectionTask to Login User */
            Map<String,String> postData = new HashMap<>();
            /** Put the JsonObject with name to postData variable
             * Ensure, theat jsonObject is converted to string
             * since we will convert it back in to Json in Server App.
             */
            postData.put( "orders" ,orders.toString());
            AsyncHttpConnectionTask task = new AsyncHttpConnectionTask(
                    RequestMode.POST,
                    RequestType.POST_ORDER,
                    CartActivity.this,
                    postData,
                    token);
            task.execute(Constants.PLACE_ORDER_URL);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void updateListView()
    {
        if ( lv_cart_items != null)
        {
            lv_cart_items.setAdapter(adapter);
        }
        else
        {
            if(BuildConfig.DEBUG)
                Log.d(TAG, "updateListView: ListView is Null!");

        }

        updatePriceAndTotalItems();
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if ( getActivity() instanceof ICartOperations)
        {
            myInterface = (ICartOperations) getActivity();
        }
    }

    @Override
    public void onResume() {
        updateListView();
        super.onResume();
    }

    @Override
    public void OnDataChanged() {

        if(BuildConfig.DEBUG)
            Log.d(TAG, "OnDataChanged: Updating view...");

        adapter.notifyDataSetChanged();
        updatePriceAndTotalItems();
    }

    void updatePriceAndTotalItems()
    {
        tv_CartTotalInPkr.setText(String.valueOf(controller.getTotalPrice()));
        tv_CartTotalItems.setText(String.valueOf(controller.getTotalItems()));
    }

    @Override
    public void OnAsyncTaskCompleted(Boolean success, RequestMode requestMode, RequestType requestType, final String result) {

        if(BuildConfig.DEBUG)
            Log.d(Constants.TAG, "OnAsyncTaskCompleted: CartActivity " + result);

        disableProgressDialog();

        switch (requestType)
        {
            case POST_ORDER:
                if(success)
                {
                    clearCartView();
                    if(BuildConfig.DEBUG)
                        Log.d(TAG, "OnAsyncTaskCompleted: Completed");

                }
                else
                {
                    operationFailed();
                    if(BuildConfig.DEBUG)
                        Log.d(TAG, "OnAsyncTaskCompleted: Not Completed");

                }
                break;
        }
    }

    public void enableProgressDialog(String message)
    {
        /** Show ProgressDialog */
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void disableProgressDialog()
    {
        /** Dismiss the Progress Dialog **/
        if (progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }

    void clearCartView(){
        controller.clearCart();
        /** Display Message of Failure */
        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                myInterface.OnOrderPostSuccessful();
                adapter.notifyDataSetChanged();
                updatePriceAndTotalItems();
                CustomToast.showToastMessage("Order Placed. Please check your email for details.",getActivity(),Toast.LENGTH_SHORT);
                //TODO::Add the profile,order & details to a txt file and keep it for reference
            }
        });

    }

    void operationFailed(){
        /** Display Message of Failure */
        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                CustomToast.showToastMessage("Unable to place your order. Please try later.",getActivity(),Toast.LENGTH_SHORT);
            }
        });

    }
}
