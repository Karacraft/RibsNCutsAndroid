package com.karacraft.ribsncuts;


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

import com.karacraft.ribsncuts.adapter.CartItemListAdapter;
import com.karacraft.ribsncuts.cart.Controller;

import static com.karacraft.ribsncuts.helper.Constants.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartActivity extends Fragment
{

    CartItemListAdapter adapter;
    ICartOperations myInterface;

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

            }
        });

        return  view;
    }


    public void updateListView()
    {

        adapter = new CartItemListAdapter(getContext(),controller.getCartContents());
        if ( lv_cart_items != null)
        {
            lv_cart_items.setAdapter(adapter);
        }
        else {
            if(BuildConfig.DEBUG)
            {
                Log.d(TAG, "updateListView: ListView is Null!");
            }
        }

        tv_CartTotalInPkr.setText(String.valueOf(controller.getTotalPrice()));
        tv_CartTotalItems.setText(String.valueOf(controller.getUniqueItems()));
        adapter.notifyDataSetChanged();
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
}
