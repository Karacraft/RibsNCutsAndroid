package com.karacraft.ribsncuts;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.karacraft.ribsncuts.DB.ProductsDB;
import com.karacraft.ribsncuts.adapter.ProductListAdapter;
import com.karacraft.ribsncuts.cart.Controller;
import com.karacraft.ribsncuts.helper.Constants;
import com.karacraft.ribsncuts.model.Item;
import com.karacraft.ribsncuts.model.Product;

import java.util.ArrayList;

import static com.karacraft.ribsncuts.helper.Constants.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllActivity extends Fragment
{

    View empty;
    ListView lv_all_products;
    ArrayList<Product> values = new ArrayList<Product>();
    ProductListAdapter adapter;
    Controller controller;

    public AllActivity() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //Setup Title
        getActivity().setTitle(R.string.title_activity_all);
        // Inflate the View
        View v =  inflater.inflate(R.layout.fragment_all, container, false);

        /** Get Global Controller Class object (See applicaiton tag in anrdroidmanifest.xml )*/
        controller = (Controller) getActivity().getApplicationContext();

        empty = v.findViewById(R.id.empty_all);
        lv_all_products = v.findViewById(R.id.lv_all_products);

        empty.setVisibility(View.GONE);
        lv_all_products.setVisibility(View.VISIBLE);

        updateListView();


        lv_all_products.setOnItemClickListener(new AdapterView.OnItemClickListener()
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

        return v;
    }

    public void updateListView()
    {
        //Initialize DB
        ProductsDB db = new ProductsDB(getContext());
        //Open Database
        db.open();
        values = db.readDataInArrayList("All");
        db.close();

        adapter = new ProductListAdapter(getContext(),values,Constants.LIST_PARTIAL);
        if (lv_all_products != null)
        {
            lv_all_products.setAdapter(adapter);
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
