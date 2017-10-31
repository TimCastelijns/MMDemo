package com.castelijns.mmdemo.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiManager {

    private static final String URL_BASE = "https://jsonplaceholder.typicode.com/";

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    private static final ApiService API_SERVICE = RETROFIT.create(ApiService.class);

    public static ApiService getService() {
        return API_SERVICE;
    }
}
