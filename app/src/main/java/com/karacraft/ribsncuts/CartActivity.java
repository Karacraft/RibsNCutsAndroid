package com.karacraft.ribsncuts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.karacraft.ribsncuts.DB.ProductsDB;
import com.karacraft.ribsncuts.adapter.CartItemListAdapter;
import com.karacraft.ribsncuts.adapter.ProductListAdapter;
import com.karacraft.ribsncuts.cart.Controller;
import com.karacraft.ribsncuts.helper.Constants;

import static com.karacraft.ribsncuts.helper.Constants.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartActivity extends Fragment
{

    CartItemListAdapter adapter;
    ListView lv_cart_items;
    View empty;
    Controller controller;

    public CartActivity()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //Setup Title
        getActivity().setTitle("Shopping Cart");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        empty = view.findViewById(R.id.empty_cart_items);
        lv_cart_items = view.findViewById(R.id.lv_cart_items);

        empty.setVisibility(View.GONE);
        lv_cart_items.setVisibility(View.VISIBLE);

        /** Get Global Controller Class object (See applicaiton tag in anrdroidmanifest.xml )*/
        controller = (Controller) getActivity().getApplicationContext();

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

}
