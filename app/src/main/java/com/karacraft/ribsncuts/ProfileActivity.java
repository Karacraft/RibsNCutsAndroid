package com.karacraft.ribsncuts;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.karacraft.ribsncuts.helper.AsyncHttpConnectionTask;
import com.karacraft.ribsncuts.helper.Constants;
import com.karacraft.ribsncuts.helper.CustomToast;
import com.karacraft.ribsncuts.helper.RequestMode;
import com.karacraft.ribsncuts.helper.RequestType;
import com.karacraft.ribsncuts.helper.SharePref;

import java.util.HashMap;
import java.util.Map;

import static com.karacraft.ribsncuts.helper.Constants.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileActivity extends Fragment implements AsyncHttpConnectionTask.AsyncHCTCallback{

    ConstraintLayout lvShowProfile;
    TextView tvEmptyProfile;
    TextView tv_userEmail,tv_userName;
    EditText et_userAddress,et_userContact;
    EditText et_userCellNumber,et_userLandLine,et_userCity;
    Button btnUpdateUserProfile;
    String token;
    ProgressDialog progressDialog;


    public ProfileActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Setup Title
        getActivity().setTitle(R.string.title_activity_profile);

        lvShowProfile = view.findViewById(R.id.lv_showProfile);
        tvEmptyProfile = view.findViewById(R.id.tv_emptyProfile);

        setUpViews(view);

        if(isUserLoggedIn())
        {
            lvShowProfile.setVisibility(View.VISIBLE);
            tvEmptyProfile.setVisibility(View.GONE);
        }
        else
        {
            lvShowProfile.setVisibility(View.GONE);
            tvEmptyProfile.setVisibility(View.VISIBLE);
        }

        btnUpdateUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO::Validate the Inputs
                if (TextUtils.isEmpty(et_userContact.getText()))
                {
                    et_userContact.setError("Contact Name Required");
                    et_userContact.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(et_userAddress.getText()))
                {
                    et_userAddress.setError("Full Address Required");
                    et_userAddress.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(et_userCellNumber.getText()))
                {
                    et_userCellNumber.setError("Cell Number is required");
                    et_userCellNumber.requestFocus();
                    return;
                }

                ((BaseActivity)getActivity()).enableProgressDialog("Updating profile. Please wait...");
                setUpProfileData();
                //Update on Server
                Map<String, String> postData = new HashMap<>();
                postData.put("contact", et_userContact.getText().toString());
                postData.put("address", et_userAddress.getText().toString());
                postData.put("cellnumber", et_userCellNumber.getText().toString());
                postData.put("landline", et_userLandLine.getText().toString());
                postData.put("city", et_userCity.getText().toString());
                AsyncHttpConnectionTask task = new AsyncHttpConnectionTask(
                        RequestMode.POST,
                        RequestType.POST_UPDATE_PROFILE,
                        ProfileActivity.this,
                        postData,
                        token);
                task.execute(Constants.UPDATE_PROFILE_URL);

            }
        });

        return view;
    }

    void setUpViews(View view)
    {
        tv_userName = view.findViewById(R.id.tv_userName);
        tv_userEmail = view.findViewById(R.id.tv_userEmail);
        et_userContact = view.findViewById(R.id.tv_userContact);
        et_userAddress = view.findViewById(R.id.tv_userAddress);
        et_userCellNumber = view.findViewById(R.id.tv_userCellNumber);
        et_userLandLine = view.findViewById(R.id.tv_userLandLine);
        et_userCity = view.findViewById(R.id.tv_userCity);
        btnUpdateUserProfile = view.findViewById(R.id.btnUpdateUserProfile);

        SharePref s = SharePref.getInstance(getContext());
        tv_userName.setText(s.getString(Constants.USER_NAME));
        tv_userEmail.setText(s.getString(Constants.USER_EMAIL));
        et_userContact.setText(s.getString(Constants.PROFILE_CONTACT));
        et_userAddress.setText(s.getString(Constants.PROFILE_ADDRESS));
        et_userCellNumber.setText(s.getString(Constants.PROFILE_CELLNUMBER));
        et_userLandLine.setText(s.getString(Constants.PROFILE_LANDLINE));
        et_userCity.setText(s.getString(Constants.PROFILE_CITY));
        token = s.getString(Constants.APP_TOKEN);
    }

    void setUpProfileData()
    {

        SharePref s = SharePref.getInstance(getContext());
        s.putString(Constants.PROFILE_CONTACT,et_userContact.getText().toString());
        s.putString(Constants.PROFILE_ADDRESS,et_userAddress.getText().toString());
        s.putString(Constants.PROFILE_CELLNUMBER,et_userCellNumber.getText().toString());
        s.putString(Constants.PROFILE_LANDLINE,et_userLandLine.getText().toString());
        s.putString(Constants.PROFILE_CITY,et_userCity.getText().toString());
    }

    boolean isUserLoggedIn()
    {
        //Save Shared Preferences
        SharePref g = SharePref.getInstance(getContext());
        boolean isUserLoggedIn = g.getBoolean(Constants.USER_LOGGED_IN);
        if (isUserLoggedIn)
        {
            return true;
        }
        return false;
    }



    @Override
    public void OnAsyncTaskCompleted(Boolean success, RequestMode requestMode, RequestType requestType, String result) {

        if(BuildConfig.DEBUG)
            Log.d(Constants.TAG, "OnAsyncTaskCompleted: ProfileActivity " + result);

        ((BaseActivity)getActivity()).disableProgressDialog();

        switch (requestType)
        {
            case POST_UPDATE_PROFILE:
                if(success)
                {
//                    clearCartView();
                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((BaseActivity)getActivity()).showToastMessage("Profile Updated.",Toast.LENGTH_SHORT);
                        }
                    });
                    Log.d(TAG, "OnAsyncTaskCompleted: Completed");
                }
                else
                {
                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((BaseActivity)getActivity()).showToastMessage("Unable to fullfill request. Try again later.",Toast.LENGTH_SHORT);
                        }
                    });
//                    operationFailed();
                    Log.d(TAG, "OnAsyncTaskCompleted: Not Completed");
                }
                break;
        }
    }
}
