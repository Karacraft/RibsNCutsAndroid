package com.karacraft.ribsncuts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.karacraft.ribsncuts.cart.Controller;
import com.karacraft.ribsncuts.helper.AsyncHttpConnectionTask;
import com.karacraft.ribsncuts.helper.Constants;
import com.karacraft.ribsncuts.helper.CustomToast;
import com.karacraft.ribsncuts.helper.RequestMode;
import com.karacraft.ribsncuts.helper.RequestType;
import com.karacraft.ribsncuts.helper.SharePref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,ICartUpdated,AsyncHttpConnectionTask.AsyncHCTCallback
{

    FragmentManager fragmentManager = getSupportFragmentManager();
    ProgressDialog progressDialog;
    Controller controller;

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    TextView all, beef, mutton , tvCartBadge;

    int countNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base);

        /** Get Global Controller Class object (See applicaiton tag in anrdroidmanifest.xml )*/
        controller = (Controller) getApplicationContext();

        /** Setup Toolbar */
        toolbar = findViewById(R.id.main_toolbar); //In toolbar_main.xml
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_rnc_logo_24_w);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        /** Setup Drawer */
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /** Create Navigation View*/
        navigationView = findViewById(R.id.nav_drawer_view);
        navigationView.setNavigationItemSelectedListener(this);
//        Setup NavDrawer Texts
//        all = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_all));
//        beef = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_beef));
//        mutton = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_mutton));
//        //This method will initialize the count value
//        initializeCountDrawer(17,12,8);


        /** Setup First Fragment Here **/
        AllActivity allActivity = new AllActivity();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right)
//                .addToBackStack(null)
                .replace(R.id.main_fragment, allActivity)
                .commit();
    }

    /**
     * This function Updates the counter in NavigationDrawer
     * @param iAll
     * @param iBeef
     * @param iMutton
     */
    public void initializeCountDrawer(int iAll, int iBeef, int iMutton)
    {
        //Gravity property aligns the text
        all.setGravity(Gravity.CENTER_VERTICAL);
        all.setTypeface(null, Typeface.BOLD);
        all.setTextColor(getResources().getColor(R.color.colorAccent));
        all.setText(String.valueOf(iAll));
        beef.setGravity(Gravity.CENTER_VERTICAL);
        beef.setTypeface(null,Typeface.BOLD);
        beef.setTextColor(getResources().getColor(R.color.colorAccent));
        beef.setText(String.valueOf(iBeef));
        mutton.setGravity(Gravity.CENTER_VERTICAL);
        mutton.setTypeface(null,Typeface.BOLD);
        mutton.setTextColor(getResources().getColor(R.color.colorAccent));
        mutton.setText(String.valueOf(iMutton));
    }

    /** Load the selected Fragment in View */
    private void loadSelectedFragment(int fragmentId)
    {

        switch (fragmentId){
            case R.id.nav_all:
                AllActivity allActivity = new AllActivity();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right)
                        .addToBackStack(null)
                        .replace(R.id.main_fragment, allActivity)
                        .commit();
                break;
            case R.id.nav_beef:
                BeefActivity beefActivity = new BeefActivity();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right)
                        .addToBackStack(null)
                        .replace(R.id.main_fragment,beefActivity)
                        .commit();
                break;
            case R.id.nav_mutton:
                MuttonActivity muttonActivity = new MuttonActivity();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right)
                        .addToBackStack(null)
                        .replace(R.id.main_fragment,muttonActivity)
                        .commit();
                break;
            case R.id.nav_address:
                AddressActivity addressActivity = new AddressActivity();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right)
                        .addToBackStack(null)
                        .replace(R.id.main_fragment, addressActivity)
                        .commit();
                break;
            case R.id.nav_visit_website:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ribsncuts.com"));
                startActivity(intent);
                break;
            case R.id.nav_location:
                Intent intent1 = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:30.228450,71.479049"));
                startActivity(intent1);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @Override
    public void onBackPressed()
    {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);


        final MenuItem menuItem = menu.findItem(R.id.nav_cart);
        final View badge = menuItem.getActionView();

        tvCartBadge  = badge.findViewById(R.id.txtCount);

        setupBadge();

        badge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    public void setupBadge()
    {
        if(tvCartBadge != null)
        {
            if (countNumber == 0)
            {
                if (tvCartBadge.getVisibility() != View.GONE)
                {
                    tvCartBadge.setVisibility(View.GONE);
                }
            }
            else
            {
                tvCartBadge.setText(String.valueOf(countNumber));
                if (tvCartBadge.getVisibility() != View.VISIBLE)
                {
                    tvCartBadge.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_login) {
            showLoginAlertDialog();
            return true;
        }

        if (id == R.id.nav_cart){

            if (controller.isCartEmpty())
            {
                CustomToast.showToastMessage("Cart is Empty",MainActivity.this,Toast.LENGTH_SHORT);
            }
            else
            {

                CartActivity cartActivity = (CartActivity) fragmentManager.findFragmentByTag("CartFragment");
                //if we are on cartActivity , then gracefully don't reply to click
                if (cartActivity == null)
                {
                    cartActivity = new CartActivity();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right)
                            .addToBackStack(null)
                            .replace(R.id.main_fragment, cartActivity,"CartFragment")
                            .commit();
                }

            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showLoginAlertDialog()
    {
        /** show custom dialog for testing*/
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view1 = getLayoutInflater().inflate(R.layout.custom_login_dialog,null);

        final EditText etEmail = view1.findViewById(R.id.etCustomLoginEmail);
        final EditText etPassword = view1.findViewById(R.id.etCustomLoginPassword);
        Button btnLogin = view1.findViewById(R.id.btnCustomLoginSubmit);
        Button btnRegister = view1.findViewById(R.id.btnCustomLoginNewUserRegister);

        builder.setView(view1);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (TextUtils.isEmpty(etPassword.getText()))
                {
                    etPassword.setError("Field is empty");
                    return;
                }
                String email = etEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email) || !isEmailValid(email))
                {
                    etEmail.setError("Email address required");
                    return;
                }

                loginUser(etEmail.getText().toString().trim(),etPassword.getText().toString().trim());
                dialog.dismiss();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showRegisterAlertDialog();
                if ( dialog.isShowing())
                {
                    dialog.dismiss();
                }
            }
        });
    }

    public boolean isEmailValid(CharSequence email)
    {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void showRegisterAlertDialog()
    {
        /** show custom dialog for testing*/
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view1 = getLayoutInflater().inflate(R.layout.custom_register_dialog,null);
        Button btnRegister = view1.findViewById(R.id.btnCustomRegisterSubmit);
        btnRegister.setText("Register");
        final EditText etUsername = view1.findViewById(R.id.etCustomRegisterUsername);
        final EditText etEmail = view1.findViewById(R.id.etCustomRegisterEmail);
        final EditText etPassword1 = view1.findViewById(R.id.etCustomRegisterPassword);
        final EditText etPassword2 = view1.findViewById(R.id.etCustomRegisterPasswordConfirm);

        builder.setView(view1);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (TextUtils.isEmpty(etUsername.getText().toString()))
                {
                    etUsername.setError("Required Filed");
                    return;
                }

                String email = etEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email) || !isEmailValid(email))
                {
                    etEmail.setError("Email address required");
                    return;
                }

                if (TextUtils.isEmpty(etPassword1.getText()))
                {
                    etPassword1.setError("Field is required");
                    return;
                }

                if (TextUtils.isEmpty(etPassword2.getText()))
                {
                    etPassword2.setError("Field is required");
                    return;
                }

                if ( !etPassword1.getText().toString().equals(etPassword2.getText().toString()))
                {
                    etPassword2.setError("Field doesn't match");
                    return;
                }

                registerUser(etUsername.getText().toString().trim(),etEmail.getText().toString().trim(),etPassword1.getText().toString().trim());
                dialog.dismiss();
            }
        });
    }

    /**
     * Logs in the User with Rest Api
     * @param email email of the user
     * @param password password of the user
     */
    public void loginUser(String email,String password)
    {
        enableProgressDialog("Trying to login...Please wait");

        /** Add data to prefs **/
        SharePref p = SharePref.getInstance(MainActivity.this);
        p.putString(Constants.USER_EMAIL,email);
        p.putString(Constants.USER_PASSWORD,password);

        /** Call the AsyncHttpConnectionTask to Login User */
        Map<String,String> postData = new HashMap<>();
        postData.put("email",email);
        postData.put("password",password);
        AsyncHttpConnectionTask task = new AsyncHttpConnectionTask(RequestMode.POST,RequestType.POST_LOGIN,MainActivity.this,postData);
        task.execute(Constants.LOGIN_URL);
    }

    public void registerUser(String username , String email,String password)
    {
        enableProgressDialog("Trying to register user...Please wait");

        /** Add data to prefs **/
        SharePref p = SharePref.getInstance(MainActivity.this);
        p.putString(Constants.USER_EMAIL,email);
        p.putString(Constants.USER_PASSWORD,password);
        p.putString(Constants.USER_NAME,username);

        /** Call the AsyncHttpConnectionTask to Login User */
        Map<String,String> postData = new HashMap<>();
        postData.put("name",username);
        postData.put("email",email);
        postData.put("password",password);
        postData.put("confirm_password",password);
        AsyncHttpConnectionTask task = new AsyncHttpConnectionTask(RequestMode.POST,RequestType.POST_REGISTER,MainActivity.this,postData);
        task.execute(Constants.REGISTER_URL);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        loadSelectedFragment(id);

        return true;
    }

    /**
     * interface implemented method
     * @param cartSize
     */
    @Override
    public void OnCartUpdate(int cartSize)
    {
        countNumber = cartSize;
        setupBadge();
    }

    /**
     * AsyncTask Implemented Interface callBack
     * @param success
     * @param requestMode
     * @param requestType
     * @param result
     */
    @Override
    public void OnAsyncTaskCompleted(Boolean success, RequestMode requestMode, RequestType requestType, String result)
    {

        if(BuildConfig.DEBUG)
            Log.d(Constants.TAG, "OnAsyncTaskCompleted MainActivity: Result: " + result);

        switch (requestType)
        {
           case POST_LOGIN:
               if(success)
               {
                   if(BuildConfig.DEBUG)
                       Log.d(Constants.TAG, "OnAsyncTaskCompleted MainActivity: POST_LOGIN SUCCESS");
                   saveTokenFromAttempt(result);
               }
               else
               {
                   if(BuildConfig.DEBUG)
                       Log.d(Constants.TAG, "OnAsyncTaskCompleted MainActivity: POST_LOGIN FAILED");
                   tokenAttemptFailed(result);
               }
               break;
           case POST_REGISTER:
               if(success)
               {
                   if(BuildConfig.DEBUG)
                       Log.d(Constants.TAG, "OnAsyncTaskCompleted MainActivity: POST_REGISTER SUCCESS");
                   saveTokenFromAttempt(result);
               }
               else
               {
                   if(BuildConfig.DEBUG)
                       Log.d(Constants.TAG, "OnAsyncTaskCompleted MainActivity: POST_REGISTER FAILED");
                    tokenAttemptFailed(result);
               }
               break;
        }
    }

    /** This is invoked after AsyncTask is finished */
    public void saveTokenFromAttempt(String result)
    {

        String token ="";
        try
        {
            JSONObject jsonObject = new JSONObject(result);
            token = jsonObject.getString(Constants.APP_TOKEN);

            if(BuildConfig.DEBUG)
                Log.d(Constants.TAG, "saveTokenFromAttempt:  Token: " + token);

        } catch (JSONException e)
        {
            disableProgressDialog();
            e.printStackTrace();
        }

        //TODO::ensure Login Form is no more showable

        /** Setting Shared Preferences */
        SharePref g = SharePref.getInstance(MainActivity.this);
        g.putString(Constants.APP_TOKEN,token);

        disableProgressDialog();

    }

    public void tokenAttemptFailed(final String result)
    {

        disableProgressDialog();

        /** Display Message of Failure */
        MainActivity.this.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                CustomToast.showToastMessage(result,MainActivity.this,Toast.LENGTH_LONG);

                if(BuildConfig.DEBUG)
                    Log.d(Constants.TAG, "tokenAttemptFailed:  " + result);

                //TODO:: ensure Login is visible
            }
        });

    }

    public void enableProgressDialog(String message)
    {
        /** Show ProgressDialog */
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void disableProgressDialog()
    {
        /** Dismiss the Progress Dialog **/
        if (progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }

}
