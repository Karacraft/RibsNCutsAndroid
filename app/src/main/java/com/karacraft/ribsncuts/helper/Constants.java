package com.karacraft.ribsncuts.helper;

public class Constants
{
    public static final String IP_LOCAL = "http://192.168.0.103/";
    public static final String IP_PRODUCTION = "https://ribsncuts.com/";

    public static final String PRODUCT_URL = IP_PRODUCTION + "api/products";
//    public static final String PRODUCT_URL = IP_LOCAL +  "ribsncuts/public/api/products";

    public static final String LOGIN_URL = IP_PRODUCTION + "api/login";
//    public static final String LOGIN_URL = IP_LOCAL +  "ribsncuts/public/api/login";

    public static final String DETAILS_URL = IP_PRODUCTION + "api/details";
//    public static final String DETAILS_URL = IP_LOCAL +  "ribsncuts/public/api/details";

    public static final String REGISTER_URL = IP_PRODUCTION + "api/register";
//    public static final String REGISTER_URL = IP_LOCAL +  "ribsncuts/public/api/register";

    public static final String IMAGE_URL = IP_PRODUCTION + "images/products/";
//    public static final String IMAGE_URL = IP_LOCAL + "ribsncuts/public/images/products/";

    public static final String PLACE_ORDER_URL = IP_PRODUCTION +  "api/order";
    //    public static final String PLACE_ORDER_URL = IP_LOCAL +  "ribsncuts/public/api/order";

    public static final String UPDATE_PROFILE_URL = IP_PRODUCTION +  "api/profile/update";
//    public static final String UPDATE_PROFILE_URL = IP_LOCAL +  "ribsncuts/public/api/profile/update";

    /** Application Preferences*/
    public static final String APP_PREF_NAME = "com.karacraft.ribsncuts.prefs";
    public static final String FIRST_ATTEMPT = "first_attempt";
    public static final String LAST_UPDATE = "last_update";
    public static final String APP_TOKEN = "token";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME ="name";
    public static final String USER_LOGGED_IN = "userloggedin";
    public static final String PROFILE_ID = "id";
    public static final String PROFILE_CONTACT = "contact";
    public static final String PROFILE_ADDRESS = "address";
    public static final String PROFILE_CITY = "city";
    public static final String PROFILE_CELLNUMBER = "cellnumber";
    public static final String PROFILE_LANDLINE = "landline";

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
