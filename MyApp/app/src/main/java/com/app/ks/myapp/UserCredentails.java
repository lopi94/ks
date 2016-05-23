package com.app.ks.myapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by KS on 2016-04-23.
 */
public class UserCredentails  {

    public static  final String USERNAME = "username";
    public static  final String PASSWORD = "password";

    @SerializedName("username")
    private String mUsername;
    @SerializedName("password")
    private String mPassword;

    public UserCredentails() {}

    public UserCredentails(String mUsername, String mPassword) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
