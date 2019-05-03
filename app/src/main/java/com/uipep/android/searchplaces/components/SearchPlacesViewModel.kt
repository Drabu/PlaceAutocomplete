package com.uipep.android.searchplaces.components

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

class SearchPlacesViewModel(application: Application) : AndroidViewModel(application) {

    var searchPlacesRepo = SearchPlacesRepo()

    fun requestListOfSearchResults(placeHint: String) {
        searchPlacesRepo.requestListOfSearchResults(placeHint)
    }

    fun getLiveListOfSearchResultsStream() = searchPlacesRepo.getLiveListOfSearchResultsStream()



    fun getPlaceDetailsLiveDataStream() = searchPlacesRepo.getPlaceDetailsLiveDataStream()

    fun clear() {
        searchPlacesRepo.clear()
    }

}