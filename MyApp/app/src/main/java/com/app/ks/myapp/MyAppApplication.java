package com.app.ks.myapp;

import android.app.Application;

/**
 * Created by KS on 2016-04-16.
 */
public class MyAppApplication extends Application {

    private static MyAppApplication sInstance;
    private DbHelper dbHelper;
    private static UserCredentails mUserCredentails;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyAppApplication getsInstance() {
        return  sInstance;
    }

    public DbHelper getDbHelper() {
        if(dbHelper == null) {
            dbHelper = new DbHelper(this);
        }
        return dbHelper;
    }

    public UserCredentails getUserCredentails() {
        return mUserCredentails;
    }

    public static void setUserCredentials(UserCredentails userCredentials) {
        if(userCredentials == null) {
            mUserCredentails = userCredentials;
        }
    }

}
