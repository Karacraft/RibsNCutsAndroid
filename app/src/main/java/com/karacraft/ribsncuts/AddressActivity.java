package com.karacraft.ribsncuts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddressActivity extends Fragment
{


    public AddressActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Setup Title
        getActivity().setTitle(R.string.title_activity_address);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address, container, false);
    }


}
