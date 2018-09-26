package com.karacraft.ribsncuts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.karacraft.ribsncuts.DB.ProductsDB;
import com.karacraft.ribsncuts.cart.Controller;
import com.karacraft.ribsncuts.helper.AsyncHttpConnectionTask;
import com.karacraft.ribsncuts.helper.Constants;
import com.karacraft.ribsncuts.helper.CustomToast;
import com.karacraft.ribsncuts.helper.RequestMode;
import com.karacraft.ribsncuts.helper.RequestType;
import com.karacraft.ribsncuts.helper.SharePref;

import org.json.JSONArray;
import org.json.JSONObject;

public class SplashActivity extends BaseActivity implements AsyncHttpConnectionTask.AsyncHCTCallback
{

    boolean firstAttempt = false;  //If there is data in Sqlite

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);


        /** Get Global Controller Class object (See application tag in AndroidManifest.xml )*/
//        final Controller controller = (Controller) getApplicationContext();
//        controller.clearCart();

        /** All Set up , now download the data form Internet.
         *
         * 1- Check if This is first Attempt
         * 2- If first Attempt, Download data from net
         * 3- If no Internet , then quit Application
         * 4- Download Data and update the first Fragment with All Products
         * 5- Make first Attempt as Done
         * */
        //Save Shared Preferences
        SharePref g = SharePref.getInstance(SplashActivity.this);
        firstAttempt = g.getBoolean(Constants.FIRST_ATTEMPT);

        if (!firstAttempt)
        {
            enableProgressDialog("Downloading data from server...Please wait");

            /** This is first Attempt- Fetch data from Internet */
            if(BuildConfig.DEBUG)
            {
                Log.d(Constants.TAG, "onCreate: Product Data doesn't Exist. Fetching from Internet");
            }
            /** User Custom AsyncTask Class */
            AsyncHttpConnectionTask task =  new AsyncHttpConnectionTask(
                    RequestMode.GET,
                    RequestType.GET_PRODUCTS,
                    SplashActivity.this);
            task.execute(Constants.PRODUCT_URL);
        }
        else
        {
            /** This is not First Attempt. Go directly to Main Activity */
            if (BuildConfig.DEBUG)
            {
                Log.d(Constants.TAG, "onCreate: Shared Preferences exist. Product Data Loaded from Sqlite Database");
            }
            //Start the activity after delay - to show the splash
            delayLoading.start();
        }

    }

    /** Load MainActivity **/
    public void loadMainActivity()
    {
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    /** Converts the String data to Json for Product Table */
    public void populateSqliteWithProducts(String resultData)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(resultData);
            String result = jsonObject.getString("success");
            String lastUpdate = jsonObject.getString("lastUpdate");
            JSONArray products = jsonObject.getJSONArray("products");

            if (BuildConfig.DEBUG)
            {
                Log.d(Constants.TAG, "getJsonData: Result is: " + result);
                Log.d(Constants.TAG, "getJsonData: Last update is: " + lastUpdate);
                Log.d(Constants.TAG, "getJsonData: Products Size is: " + products.length());
            }

            //Initialize DB
            ProductsDB db = new ProductsDB(SplashActivity.this);
            //Open Database
            db.open();

            //Data is Products
            for (int i = 0 ; i < products.length() ; i++)
            {
                JSONObject k = products.getJSONObject(i);
                db.createEntry(
                        k.getInt("id"),
                        k.getString("title"),
                        k.getString("cut_source"),
                        k.getString("best_for"),
                        k.getString("description"),
                        k.getString("image"),
                        k.getString("slug"),
                        k.getString("category"),
                        k.getInt("price_per_kg")
                );
            }

            //Close Database
            db.close();
            //Save Shared Preferences
            SharePref g = SharePref.getInstance(SplashActivity.this);
            g.putString(Constants.LAST_UPDATE,lastUpdate);
            g.putBoolean(Constants.FIRST_ATTEMPT,true);

            disableProgressDialog();

            //Start main Activity
            loadMainActivity();

        }
        catch (Exception e)
        {
            disableProgressDialog();
            e.printStackTrace();
        }

    }


    @Override
    public void OnAsyncTaskCompleted(Boolean success, RequestMode requestMode, RequestType requestType, final String result)
    {
        if(BuildConfig.DEBUG)
            Log.d(Constants.TAG, "OnAsyncTaskCompleted: SplashActivity " + result);

        switch (requestType)
        {
            case GET_PRODUCTS:
                if(success)
                {
                    populateSqliteWithProducts(result);
                }
                else
                {
                    disableProgressDialog();
                    /** Display Message of Failure */
                    SplashActivity.this.runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            showToastMessage("Unable to fetch data. Quitting application",Toast.LENGTH_LONG);

                            if(BuildConfig.DEBUG)
                                Log.d(Constants.TAG, "Downloading Failed:  " + result);

                            //TODO:: ensure Login is visible
                        }
                    });
                    quitApplicationThread.start();
                }
                break;
        }
    }

    /** Threads For Stopping or Delaying Activity activity */
    Thread quitApplicationThread = new Thread()
    {
        @Override
        public void run()
        {
            try
            {
                Thread.sleep(3500); //For LONG

                SplashActivity.this.finish();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    };

    Thread delayLoading = new Thread()
    {
        @Override
        public void run()
        {
            try
            {
                Thread.sleep(1000); //For Delay

                loadMainActivity();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    };


}
