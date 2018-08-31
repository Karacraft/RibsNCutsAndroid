package com.karacraft.ribsncuts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.karacraft.ribsncuts.DB.ProductsDB;
import com.karacraft.ribsncuts.adapter.ProductListAdapter;
import com.karacraft.ribsncuts.cart.Controller;
import com.karacraft.ribsncuts.helper.Constants;
import com.karacraft.ribsncuts.model.Product;

import java.util.ArrayList;

import static com.karacraft.ribsncuts.helper.Constants.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class BeefActivity extends Fragment
{

    /** Global Class*/
    Controller controller;

    View empty;
    ListView lv_beef_products;
    ArrayList<Product> values = new ArrayList<Product>();
    ProductListAdapter adapter;

    public BeefActivity() { /**Required empty public constructor*/ }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Inflated beef");
        //Setup Title
        getActivity().setTitle(R.string.title_activity_beef);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beef, container, false);
        empty = view.findViewById(R.id.empty_beef);
        lv_beef_products = view.findViewById(R.id.lv_beef_products);


        empty.setVisibility(View.GONE);
        lv_beef_products.setVisibility(View.VISIBLE);

        updateListView();


        /** Init Global Controller Class object (See applicaiton tag in anrdroidmanifest.xml )*/
        controller = (Controller) getActivity().getApplicationContext();


        /** Set up Clicks for List Items*/
        lv_beef_products.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {

                if(BuildConfig.DEBUG)
                {
                    Log.d(TAG, "onItemClick: " + values.get(i).getTitle());
                }

                Bundle bundle = new Bundle();

                bundle.putInt(Constants.KEY_ROWID,values.get(i).getId());
                bundle.putString(Constants.KEY_TITLE,values.get(i).getTitle());
                bundle.putString(Constants.KEY_CUTSOURCE,values.get(i).getCutSource());
                bundle.putString(Constants.KEY_BESTFOR,values.get(i).getBestFor());
                bundle.putString(Constants.KEY_DESCRIPTION,values.get(i).getDescription());
                bundle.putString(Constants.KEY_SLUG,values.get(i).getSlug());
                bundle.putString(Constants.KEY_CATEGORY,values.get(i).getCategory());
                bundle.putString(Constants.KEY_IMAGE,values.get(i).getImage());
                bundle.putInt(Constants.KEY_PRICEPERKG,values.get(i).getPrice());

                ShowProductActivity showProductActivity = new ShowProductActivity();
                FragmentManager fragmentManager = getFragmentManager();
                showProductActivity.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right)
                        .addToBackStack(null)
                        .replace(R.id.main_fragment, showProductActivity)
                        .commit();
            }
        });

        return  view;
    }



    public void updateListView()
    {

        //We have Data, inflate the list view
        if (BuildConfig.DEBUG)
        {
            Log.d(TAG, "updateListViw: Loading from Sqlite...");
        }
        //Initialize DB
        ProductsDB db = new ProductsDB(getContext());
        //Open Database
        db.open();
        values = db.readDataInArrayList("Beef");
        db.close();

        adapter = new ProductListAdapter(getContext(),values,Constants.LIST_PARTIAL);

        if (lv_beef_products != null)
        {
            lv_beef_products.setAdapter(adapter);
        }
        else
        {
            if(BuildConfig.DEBUG)
            {
                Log.d(TAG, "updateListView: ListView is Null!");
            }
        }

        adapter.notifyDataSetChanged();
    }



}
