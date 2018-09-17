package com.karacraft.ribsncuts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersActivity extends Fragment {

    public OrdersActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Setup Title
        getActivity().setTitle(R.string.title_activity_orders);
        // Inflate the View
        View view =  inflater.inflate(R.layout.fragment_all, container, false);
        
        return view;
    }

}
