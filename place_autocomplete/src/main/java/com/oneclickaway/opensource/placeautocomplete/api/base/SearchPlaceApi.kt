package com.oneclickaway.opensource.placeautocomplete.api.base

import com.oneclickaway.opensource.placeautocomplete.api.bean.place_details.PlacesDetailsResponse
import com.oneclickaway.opensource.placeautocomplete.api.bean.places_response.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
/** @author @buren ---> {api documentation}*/
interface SearchPlaceApi {

    /** @author @buren ---> {return the details of a specific place}*/
    @GET("details/json")
    fun getPlaceDetailsFromPlaceId(
        @Query("placeid") placeId: String, @Query("key") apiKey: String
    ): Observable<PlacesDetailsResponse>

    /** @author @buren ---> {gets the list of predictions}*/
    @GET("autocomplete/json")
    fun getPlaceResults(
        @Query("input") placeHint: String, @Query("key") apiKey: String, @Query("location") location: String, @Query(
            "radius"
        ) radius: String
    ): Observable<SearchResponse>

}
