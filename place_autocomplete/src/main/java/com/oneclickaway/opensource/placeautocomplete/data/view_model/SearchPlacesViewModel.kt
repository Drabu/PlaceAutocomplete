package com.oneclickaway.opensource.placeautocomplete.data.view_model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.oneclickaway.opensource.placeautocomplete.data.repositories.SearchPlacesRepo
import com.oneclickaway.opensource.placeautocomplete.utils.LoadingManager

/** @author @buren ---> {view model used to hold data while device configuration is changed}*/
class SearchPlacesViewModel(application: Application) : AndroidViewModel(application) {

    val searchPlacesRepo = SearchPlacesRepo()


    /** @author @buren ---> {micro-service used to request data stream of search results}*/
    fun requestListOfSearchResults(placeHint: String, apiKey : String, location : String, radius : String) {
        searchPlacesRepo.requestListOfSearchResults(placeHint = placeHint, apiKey = apiKey, location = location, radius = radius )
    }

    /** @author @buren ---> {micro-service used to get live data stream of search results}*/
    fun getLiveListOfSearchResultsStream() = searchPlacesRepo.getLiveListOfSearchResultsStream()


    /** @author @buren ---> {micro-service used to request data stream of place details}*/
    fun requestPlaceDetails(placeId : String, apiKey : String){
        searchPlacesRepo.requestPlaceDetails(placeId = placeId, apiKey = apiKey)
    }

    /** @author @buren ---> {micro-service used to get live data stream of place details}*/
    fun getPlaceDetailsLiveDataStream() = searchPlacesRepo.getPlaceDetailsLiveDataStream()

    fun getLoadingPredictionManager(): MutableLiveData<LoadingManager> = searchPlacesRepo.getLoadingPredictionManager()

    fun getLoadingPlaceManager(): LiveData<LoadingManager> = searchPlacesRepo.getLoadingPlaceManager()




}