package com.karacraft.ribsncuts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karacraft.ribsncuts.DB.ProductsDB;
import com.karacraft.ribsncuts.helper.Constants;
import com.karacraft.ribsncuts.model.Product;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChickenActivity extends Fragment
{

    View empty;
    ArrayList<Product> values = new ArrayList<Product>();

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



        return  view;
    }



    public void updateListView()
    {
        //We have Data, inflate the list view
        if (BuildConfig.DEBUG)
            Log.d(Constants.TAG, "updateListViw: Loading from Sqlite...");

        //Initialize DB
        ProductsDB db = new ProductsDB(getContext());
        //Open Database
        db.open();
        values = db.readDataInArrayList("Chicken");
        db.close();


    }

}
