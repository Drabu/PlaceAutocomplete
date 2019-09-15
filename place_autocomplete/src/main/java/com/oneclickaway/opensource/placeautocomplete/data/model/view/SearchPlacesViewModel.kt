package com.oneclickaway.opensource.placeautocomplete.data.model.view

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.oneclickaway.opensource.placeautocomplete.data.model.room.SearchSelectedItem
import com.oneclickaway.opensource.placeautocomplete.data.repositories.SearchPlacesRepo
import com.oneclickaway.opensource.placeautocomplete.utils.LoadingManager

/** @author @buren ---> {view model used to hold data while device configuration is changed}*/
class SearchPlacesViewModel(private val applicationContext: Application) :
    AndroidViewModel(applicationContext) {


    private val searchPlacesRepo = SearchPlacesRepo(applicationContext)


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

    /** @author @buren ---> {prediction manager}*/
    fun getLoadingPredictionManager(): LiveData<LoadingManager> =
        searchPlacesRepo.getLoadingPredictionManager()

    /** @author @buren ---> {places Manager}*/
    fun getLoadingPlaceManager(): LiveData<LoadingManager> = searchPlacesRepo.getLoadingPlaceManager()

    /** @author @buren ---> {Searches manager}*/
    fun getRecentSearchesManager(): LiveData<LoadingManager> =
        searchPlacesRepo.getRecentSearchesManager()

    /** @author @buren ---> {Search data}*/
    fun getRecentSearchesData(): LiveData<List<SearchSelectedItem>> =
        searchPlacesRepo.getRecentSearches()

    /** @author @buren ---> {recent searches}*/
    fun requestListOfRecentSearches() =
        searchPlacesRepo.requestListOfRecentSearches(applicationContext)


}