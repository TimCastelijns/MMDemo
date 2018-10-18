package com.castelijns.mmdemo.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {

    private const val URL_BASE = "https://jsonplaceholder.typicode.com/"

    private val RETROFIT = Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    val service: ApiService = RETROFIT.create(ApiService::class.java)

}
