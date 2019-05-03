package com.uipep.android.searchplaces.api.base

import android.telecom.Call
import com.uipep.android.searchplaces.api.bean.place_details.PlacesDetailsResponse
import com.uipep.android.searchplaces.api.bean.places_response.SearchResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface SearchPlaceApi {



    @GET("details/json")
    fun getPlaceDetailsFromPlaceId(@Query("placeid") placeId : String, @Query("key") apiKey : String = RESTAPIManager.API_KEY) : Observable<PlacesDetailsResponse>

    @GET("autocomplete/json")
    fun getPlaceResults(@Query("input") placeHint : String, @Query("key") apiKey : String = RESTAPIManager.API_KEY) : Observable<SearchResponse>

}
