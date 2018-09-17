package com.karacraft.ribsncuts.helper;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static com.karacraft.ribsncuts.helper.Constants.TAG;

public class SharePref
{

    private static SharePref sharePref = new SharePref();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    /** Constructor */
    private  SharePref() {};

    //The context passed into the getInstance should be application level context.
    public static SharePref getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(Constants.APP_PREF_NAME, Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharePref;
    }

    public void putString(String key,String value)
    {
        editor.putString(key,value);
        editor.commit();
    }

    public String getString(String key)
    {
        return sharedPreferences.getString(key,"");
    }

    public void putBoolean(String key,Boolean value)
    {
        editor.putBoolean(key,value);
        editor.commit();
    }

    public boolean getBoolean(String key)
    {
        return  sharedPreferences.getBoolean(key,false);
    }

    public void removeString(String key)
    {
        editor.remove(key);
        editor.commit();
        Log.d(TAG, "removeString: " + key);
    }

}
