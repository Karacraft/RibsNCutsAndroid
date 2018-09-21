package com.karacraft.ribsncuts;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karacraft.ribsncuts.DB.ProductsDB;
import com.karacraft.ribsncuts.adapter.ProductAdapter;
import com.karacraft.ribsncuts.cart.Controller;
import com.karacraft.ribsncuts.helper.Constants;
import com.karacraft.ribsncuts.model.Item;
import com.karacraft.ribsncuts.model.Product;

import java.util.ArrayList;

import static com.karacraft.ribsncuts.helper.Constants.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class BeefActivity extends Fragment
{

    View empty;
    View view;
    ArrayList<Product> values = new ArrayList<Product>();
    Controller controller;

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    ICartOperations myInterface;    //to communicate with Parent Acitivity.

    public BeefActivity() { /**Required empty public constructor*/ }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Inflated beef");
        //Setup Title
        getActivity().setTitle(R.string.title_activity_beef);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_beef, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /** The MainActivity implements this interface
         * we need it to add items to cart
         */
        if(getActivity() instanceof ICartOperations)
        {
            myInterface = (ICartOperations) getActivity();
        }

//        /** Get Global Controller Class object (See application tag in anrdroidmanifest.xml )*/
//        controller = (Controller) getActivity().getApplicationContext();

        empty = view.findViewById(R.id.empty_beef);
        recyclerView = view.findViewById(R.id.list_products);
        recyclerView.setHasFixedSize(true);

        empty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        updateListView();
    }

    public void updateListView()
    {
        //Initialize DB
        ProductsDB db = new ProductsDB(getContext());
        //Open Database
        db.open();
        values = db.readDataInArrayList("Beef");
        db.close();

        myAdapter = new ProductAdapter(getContext(), values, new ProductAdapter.ItemClicked() {
            @Override
            public void onItemClicked(int position) {
                Bundle bundle = new Bundle();

                bundle.putInt(Constants.KEY_ROWID,values.get(position).getId());
                bundle.putString(Constants.KEY_TITLE,values.get(position).getTitle());
                bundle.putString(Constants.KEY_CUTSOURCE,values.get(position).getCutSource());
                bundle.putString(Constants.KEY_BESTFOR,values.get(position).getBestFor());
                bundle.putString(Constants.KEY_DESCRIPTION,values.get(position).getDescription());
                bundle.putString(Constants.KEY_SLUG,values.get(position).getSlug());
                bundle.putString(Constants.KEY_CATEGORY,values.get(position).getCategory());
                bundle.putString(Constants.KEY_IMAGE,values.get(position).getImage());
                bundle.putInt(Constants.KEY_PRICEPERKG,values.get(position).getPrice());

                ShowProductActivity showProductActivity = new ShowProductActivity();
                FragmentManager fragmentManager = getFragmentManager();
                showProductActivity.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right)
                        .addToBackStack(null)
                        .replace(R.id.main_fragment, showProductActivity)
                        .commit();
            }

            @Override
            public void onAddButtonClicked(int position)
            {
                Item item = new Item(values.get(position).getId(),values.get(position).getTitle(),values.get(position).getImage(),values.get(position).getPrice(),1);
                myInterface.OnItemAddedToCart(item);
            }
        });
        //Set Adapter for RecyclerView
        if (recyclerView != null)
        {
            recyclerView.setAdapter(myAdapter);
        }
        else {
            if(BuildConfig.DEBUG)
                Log.d(TAG, "updateListView: ListView is Null!");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        myAdapter.notifyDataSetChanged();
    }
}
