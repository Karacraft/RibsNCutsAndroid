package com.karacraft.ribsncuts;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
    ICartUpdated myInterface;

    ListView lv_cart_items;
    Button btn_cart_clear;
    Button btn_cart_checkout;
    TextView tv_cart_subtotal;
    TextView tv_cart_grandtotal;
    Controller controller;

    LinearLayout layout_cart;
    TextView tv_cart;

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

        layout_cart = view.findViewById(R.id.cart_main_layout);
        layout_cart.setVisibility(View.VISIBLE);

        tv_cart = view.findViewById(R.id.empty_cart);
        tv_cart.setVisibility(View.GONE);

        lv_cart_items = view.findViewById(R.id.lv_cart_items);
        lv_cart_items.setVisibility(View.VISIBLE);

        btn_cart_clear = view.findViewById(R.id.btn_cart_clear);
        btn_cart_checkout = view.findViewById(R.id.btn_cart_checkout);
        tv_cart_subtotal = view.findViewById(R.id.tv_cart_subtotal);
        tv_cart_grandtotal = view.findViewById(R.id.tv_cart_grandtotal);

        /** Get Global Controller Class object (See application tag in AndroidManifest.xml )*/
        controller = (Controller) getActivity().getApplicationContext();

        tv_cart_subtotal.setText(String.valueOf(controller.getTotalPrice()));
        tv_cart_grandtotal.setText(String.valueOf(controller.getTotalPrice()));

        btn_cart_clear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                controller.clearCart();
                myInterface.OnCartUpdate(controller.getCartSize());
                layout_cart.setVisibility(View.GONE);
                tv_cart.setVisibility(View.VISIBLE);
            }
        });

        btn_cart_checkout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TODO:: Add asynctask to call server and placde the order
            }
        });

        updateListView();

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
        if ( getActivity() instanceof ICartUpdated)
        {
            myInterface = (ICartUpdated) getActivity();
        }
    }
}
