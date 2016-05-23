package com.app.ks.myapp;

import android.content.Intent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by KS on 2016-04-23.
 */
public class ServiceManager {

    private static final String CRM_SERVICE = "http://mobile1.osoz.pl:8080/crm/rest/";

    public static CRMService getCrmService() {
        Retrofit retrofit = getRetrofitInstance(CRM_SERVICE);
        return retrofit.create(CRMService.class);
    }

    private static Retrofit getRetrofitInstance(String url) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

}
