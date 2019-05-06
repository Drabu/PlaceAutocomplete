package com.oneclickaway.opensource.placeautocomplete.api.base

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/** @author @buren ---> {Api manager for REST Api}*/
object RESTAPIManager {

    private var BASE_URL = "https://maps.googleapis.com/maps/api/place/"

    private fun getOkhttpClient(): OkHttpClient {

        var okhttpBuilder = OkHttpClient.Builder()
        okhttpBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        okhttpBuilder.callTimeout(100, TimeUnit.SECONDS)
        okhttpBuilder.retryOnConnectionFailure(true)

        return okhttpBuilder.build()
    }

    /** @author @buren ---> {returns the instance of retrofit for making api calls}*/
    fun getInstance(): SearchPlaceApi {
        val retroFit = Retrofit.Builder().client(getOkhttpClient()).baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retroFit.create(SearchPlaceApi::class.java)
    }

}