package com.karacraft.ribsncuts.helper;

public enum RequestType {
    GET_UPDATES,    //Get updates from server
    GET_PRODUCTS,   //Get products from server
    POST_LOGIN,     //Get Token From Server passing Email & Password
    POST_REGISTER,  //Register user to get Token , pass Full name, Email, Password & Confirm Password
    POST_DETAILS,   //Send email & password to get it
    POST_ORDER,     //Send token , email & password to place order
}
