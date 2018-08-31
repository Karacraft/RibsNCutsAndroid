package com.karacraft.ribsncuts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.karacraft.ribsncuts.DB.ProductsDB;
import com.karacraft.ribsncuts.adapter.ProductListAdapter;
import com.karacraft.ribsncuts.helper.Constants;
import com.karacraft.ribsncuts.model.Product;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChickenActivity extends Fragment
{

    View empty;
    ListView lv_chicken_products;
    ArrayList<Product> values = new ArrayList<Product>();
    ProductListAdapter adapter;

    public ChickenActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Setup Title
        getActivity().setTitle(R.string.title_activity_chicken);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chicken, container, false);

        empty = view.findViewById(R.id.empty_chicken);
        lv_chicken_products = view.findViewById(R.id.lv_chicken_products);

        empty.setVisibility(View.GONE);
        lv_chicken_products.setVisibility(View.VISIBLE);

        updateListView();

        return  view;
    }



    public void updateListView()
    {
        //We have Data, inflate the list view
        if (BuildConfig.DEBUG)
        {
            Log.d(Constants.TAG, "updateListViw: Loading from Sqlite...");
        }
        //Initialize DB
        ProductsDB db = new ProductsDB(getContext());
        //Open Database
        db.open();
        values = db.readDataInArrayList("Chicken");
        db.close();

        adapter = new ProductListAdapter(getContext(),values,Constants.LIST_PARTIAL);

        if (lv_chicken_products != null)
        {
            lv_chicken_products.setAdapter(adapter);
        }
        else
        {
            if(BuildConfig.DEBUG)
            {
                Log.d(Constants.TAG, "updateListView: ListView is Null!");
            }
        }

        adapter.notifyDataSetChanged();
    }

}
