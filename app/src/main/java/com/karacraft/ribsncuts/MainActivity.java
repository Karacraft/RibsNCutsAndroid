package com.karacraft.ribsncuts;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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
import com.karacraft.ribsncuts.model.Item;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        AsyncHttpConnectionTask.AsyncHCTCallback ,
        ICartOperations
{

    FragmentManager fragmentManager = getSupportFragmentManager();

    Controller controller;              //Cart Data Operations

    Toolbar toolbar;                    //Shows our Navigation Drawer Icon
    DrawerLayout drawer;                //Navigation Drawer
    ActionBarDrawerToggle toggle;       //Action Bar with Menu & Icons
    NavigationView navigationView;      //Nav View

    TextView all, beef, mutton , tvCartBadge ;
    Menu mainMenu;

    int countNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base);

        /** Get Global Controller Class object (See applicaiton tag in anrdroidmanifest.xml )*/
        controller = (Controller) getApplicationContext();
        controller.clearCart();

        /** Setup Toolbar */
        toolbar = findViewById(R.id.main_toolbar); //In toolbar_main.xml
        setSupportActionBar(toolbar);
        //This allows us to show custom action bar
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
//        ((TextView)getSupportActionBar().getCustomView().findViewById(R.id.tvActionBarTitle)).setText("new title");
        //Other Details
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.ic_rnc_logo_24_w);
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
                replaceFragment(allActivity);
                break;
            case R.id.nav_beef:
                BeefActivity beefActivity = new BeefActivity();
                replaceFragment(beefActivity);
//                fragmentManager.beginTransaction()
//                        .setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right)
//                        .addToBackStack(null)
//                        .replace(R.id.main_fragment,beefActivity)
//                        .commit();
                break;
            case R.id.nav_mutton:
                MuttonActivity muttonActivity = new MuttonActivity();
                replaceFragment(muttonActivity);
                break;
            case R.id.nav_profile:
                ProfileActivity profileActivity = new ProfileActivity();
                replaceFragment(profileActivity);
                break;
//            case R.id.nav_ordres:
//                OrdersActivity ordersActivity = new OrdersActivity();
//                replaceFragment(ordersActivity);
//                break;
            case R.id.nav_address:
                AddressActivity addressActivity = new AddressActivity();
                replaceFragment(addressActivity);
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

    private void replaceFragment(Fragment fragment)
    {
        String backStateName = fragment.getClass().getName();

        boolean fragmentPopped = fragmentManager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.main_fragment, fragment);
            ft.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_left,R.anim.slide_from_left,R.anim.slide_to_right);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
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
        this.mainMenu = menu;

        //setup User Menu
        if(isUserLoggedIn())
        {
            MenuItem menuItem = menu.findItem(R.id.nav_login);
            menuItem.setIcon(R.drawable.ic_signout_24);
            menuItem.setTitle("Logout");
        }
        else
        {
            MenuItem menuItem = menu.findItem(R.id.nav_login);
            menuItem.setIcon(R.drawable.ic_signin_24);
            menuItem.setTitle("Login");
        }

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
            if(isUserLoggedIn())
            {
                logOutUser();
                showToastMessage("User logged out...",Toast.LENGTH_SHORT);
//                CustomToast.showToastMessage("User logged out..",MainActivity.this,Toast.LENGTH_SHORT);
                return true;
            }
            showLoginAlertDialog();
            return true;
        }

        if (id == R.id.nav_cart){

            if (controller.isCartEmpty())
            {
                showToastMessage("Cart is Empty",Toast.LENGTH_SHORT);
//                CustomToast.showToastMessage("Cart is Empty",MainActivity.this,Toast.LENGTH_SHORT);
            }
            else
            {
                CartActivity cartActivity = (CartActivity) fragmentManager.findFragmentByTag("CartFragment");
                //if we are on cartActivity , then gracefully don't reply to click
                if (cartActivity == null)
                {
                    cartActivity = new CartActivity();
                    replaceFragment(cartActivity);
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
                    etPassword.requestFocus();
                    return;
                }
                String email = etEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email) || !isEmailValid(email))
                {
                    etEmail.setError("Email address required");
                    etEmail.requestFocus();
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
                    etUsername.setError("Username is Required");
                    etUsername.requestFocus();
                    return;
                }

                String email = etEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email) || !isEmailValid(email))
                {
                    etEmail.setError("Email Address is Required");
                    etEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(etPassword1.getText()))
                {
                    etPassword1.setError("Password is Required");
                    etPassword1.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(etPassword2.getText()))
                {
                    etPassword2.setError("Password is Required");
                    etPassword2.requestFocus();
                    return;
                }

                if ( !etPassword1.getText().toString().equals(etPassword2.getText().toString()))
                {
                    etPassword2.setError("Field doesn't match");
                    etPassword2.requestFocus();
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

    @Override
    public void OnItemAddedToCart(Item item)
    {
        showToastMessage(item.getName() + " added to Cart", 750);
        controller.addItem(item);
        OnCartUpdate(controller.getCartSize());
    }

    @Override
    public void OnDecreaseItemInCart(Item item)
    {
        controller.decreaseItem(item);
        OnCartUpdate(controller.getCartSize());
    }

    @Override
    public void OnOrderPostSuccessful() {
        countNumber = 0;
        setupBadge();
    }

    @Override
    public void OnIncreaseItemInCart(Item item)
    {
        controller.increaseItem(item);
        OnCartUpdate(controller.getCartSize());
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
    void saveTokenFromAttempt(String result)
    {

        /** Display Message of Failure */
        MainActivity.this.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
            //TODO::Show Dialog
            showToastMessage("Please update your profile before posting an order",Toast.LENGTH_LONG);
            }
        });


        if(BuildConfig.DEBUG)
            Log.d(Constants.TAG, "saveTokenFromAttempt: Result " + result);

        String token ="";
        try
        {
            JSONObject joResult = new JSONObject(result);
            JSONObject joProfile = joResult.getJSONObject("profile");
            token = joResult.getString(Constants.APP_TOKEN);
            String name = joResult.getString(Constants.USER_NAME);

            //Save Shared Preferences
            SharePref g = SharePref.getInstance(MainActivity.this);
            g.putBoolean(Constants.USER_LOGGED_IN,true);
            g.putString(Constants.APP_TOKEN,token);
            g.putString(Constants.USER_NAME,name);
            g.putString(Constants.PROFILE_ID,joProfile.getString(Constants.PROFILE_ID));
            g.putString(Constants.PROFILE_CONTACT,joProfile.getString(Constants.PROFILE_CONTACT));
            g.putString(Constants.PROFILE_ADDRESS,joProfile.getString(Constants.PROFILE_ADDRESS));
            g.putString(Constants.PROFILE_CITY,joProfile.getString(Constants.PROFILE_CITY));
            g.putString(Constants.PROFILE_CELLNUMBER,joProfile.getString(Constants.PROFILE_CELLNUMBER));
            g.putString(Constants.PROFILE_LANDLINE,joProfile.getString(Constants.PROFILE_LANDLINE));
            //Invalidate Menus to Show the Proper icon
            invalidateOptionsMenu(); //I Guess this will work
            //Disable Progress bar
            disableProgressDialog();

        }
        catch (JSONException e)
        {
            disableProgressDialog();
            e.printStackTrace();
        }
    }

    void tokenAttemptFailed(final String result)
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

    boolean isUserLoggedIn()
    {
        //Save Shared Preferences
        SharePref g = SharePref.getInstance(MainActivity.this);
        boolean isUserLoggedIn = g.getBoolean(Constants.USER_LOGGED_IN);
        if (isUserLoggedIn)
        {
            return true;
        }
        return false;
    }

    void logOutUser()
    {
        SharePref s = SharePref.getInstance(MainActivity.this);

        s.putBoolean(Constants.USER_LOGGED_IN,false);
        s.removeString(Constants.APP_TOKEN);
        s.removeString(Constants.USER_EMAIL);
        s.removeString(Constants.USER_PASSWORD);
        s.removeString(Constants.USER_NAME);
        s.removeString(Constants.PROFILE_ID);
        s.removeString(Constants.PROFILE_CONTACT);
        s.removeString(Constants.PROFILE_ADDRESS);
        s.removeString(Constants.PROFILE_CELLNUMBER);
        s.removeString(Constants.PROFILE_LANDLINE);
        s.removeString(Constants.PROFILE_CITY);
        //Invalidate Menu
        invalidateOptionsMenu();
    }

}
