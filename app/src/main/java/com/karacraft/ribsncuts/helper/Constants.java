package com.karacraft.ribsncuts.helper;

public class Constants
{
    public static final String IP_LOCAL = "http://192.168.0.100/";
//    public static final String PRODUCT_URL = "http://ribsncuts.com/api/products";
    public static final String PRODUCT_URL = IP_LOCAL +  "ribsncuts/public/api/products";
    public static final String LATEST_URL = IP_LOCAL +  "api/latest";
//    public static final String LOGIN_URL = "http://ribsncuts.com/api/login";
    public static final String LOGIN_URL = IP_LOCAL +  "ribsncuts/public/api/login";
//    public static final String DETAILS_URL = "http://ribsncuts.com/api/details";
    public static final String DETAILS_URL = IP_LOCAL +  "ribsncuts/public/api/details";
//    public static final String REGISTER_URL = "http://ribsncuts.com/api/register";
    public static final String REGISTER_URL = IP_LOCAL +  "ribsncuts/public/api/register";
    public static final String IMAGE_URL = "https://ribsncuts.com/images/products/";

    /** Application Preferences*/
    public static final String APP_PREF_NAME = "com.karacraft.ribsncuts.prefs";
    public static final String FIRST_ATTEMPT = "first_attempt";
    public static final String LAST_UPDATE = "last_update";
    public static final String APP_TOKEN = "token";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_NAME ="user_name";

    public static final String TAG = "kcraftpk@gmail.com";

    /** For ProductAdapter */
    public static final String LIST_FULL = "list_full";
    public static final String LIST_PARTIAL = "list_partial";


    /** Table Keys*/
    public static final String KEY_ROWID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CUTSOURCE = "cut_source";
    public static final String KEY_BESTFOR = "best_for";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_PRICEPERKG = "price_per_kg";
    public static final String KEY_SLUG = "slug";
    public static final String KEY_CATEGORY = "category";


}
