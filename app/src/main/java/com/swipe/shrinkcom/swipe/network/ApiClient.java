package com.swipe.shrinkcom.swipe.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.swipe.shrinkcom.swipe.api.UtilApi.BASE_URL;
import static com.swipe.shrinkcom.swipe.api.UtilApi.BASE_URL1;


public class ApiClient {
 
//    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}