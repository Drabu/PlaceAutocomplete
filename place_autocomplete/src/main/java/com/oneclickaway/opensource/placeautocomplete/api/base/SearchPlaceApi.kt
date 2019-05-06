package com.oneclickaway.opensource.placeautocomplete.api.base

import com.oneclickaway.opensource.placeautocomplete.api.bean.place_details.PlacesDetailsResponse
import com.oneclickaway.opensource.placeautocomplete.api.bean.places_response.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchPlaceApi {


    @GET("details/json")
    fun getPlaceDetailsFromPlaceId(
        @Query("placeid") placeId: String, @Query("key") apiKey: String
    ): Observable<PlacesDetailsResponse>

    @GET("autocomplete/json")
    fun getPlaceResults(
        @Query("input") placeHint: String, @Query("key") apiKey: String, @Query("location") location: String, @Query(
            "radius"
        ) radius: String
    ): Observable<SearchResponse>

}
