package com.oneclickaway.opensource.placeautocomplete.components

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

class SearchPlacesViewModel(application: Application) : AndroidViewModel(application) {

    var searchPlacesRepo = SearchPlacesRepo()

    fun requestListOfSearchResults(placeHint: String, apiKey : String, location : String, radius : String) {
        searchPlacesRepo.requestListOfSearchResults(placeHint = placeHint, apiKey = apiKey, location = location, radius = radius )
    }

    fun getLiveListOfSearchResultsStream() = searchPlacesRepo.getLiveListOfSearchResultsStream()

    fun requestPlaceDetails(placeId : String, apiKey : String){
        searchPlacesRepo.requestPlaceDetails(placeId = placeId, apiKey = apiKey)
    }

    fun getPlaceDetailsLiveDataStream() = searchPlacesRepo.getPlaceDetailsLiveDataStream()

    fun clear() {
        searchPlacesRepo.clear()
    }

}