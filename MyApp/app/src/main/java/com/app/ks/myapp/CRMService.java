package com.app.ks.myapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by KS on 2016-04-23.
 */
public interface CRMService {

    //Get/client/{id}
    @GET(CrmServiceEndpoint.GET_CLIENT)
    Call<Client> getClient(@Header("username") String username,
                           @Header("password") String password,
                           @Path("id") long id);

    //POST /login
    @POST(CrmServiceEndpoint.LOGIN)
    Call<Void> login(@Body UserCredentails userCredentails);

    //POST /client
    @POST(CrmServiceEndpoint.CLIENT)
    Call<Void> addClient(@Header("username") String username,
                         @Header("password") String password,
                         @Body Client client);

    //GET /clients
    @GET(CrmServiceEndpoint.GET_CLIENTS_LIST)
    Call<List<Client>> getClientList(@Header("username") String username,
                                     @Header("password") String password);
}
