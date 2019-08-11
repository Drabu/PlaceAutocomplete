package com.oneclickaway.opensource.placeautocomplete.components

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.oneclickaway.opensource.placeautocomplete.api.base.RESTAPIManager
import com.oneclickaway.opensource.placeautocomplete.api.bean.place_details.PlaceDetails
import com.oneclickaway.opensource.placeautocomplete.api.bean.places_response.PredictionsItem
import com.oneclickaway.opensource.placeautocomplete.api.model.LoadingManager
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/** @author @buren ---> {REST Service repository}*/
@SuppressLint("CheckResult")
class SearchPlacesRepo {

    private var listOfSearchResults: MutableLiveData<List<PredictionsItem?>> = MutableLiveData()

    private var loadingPredictionManager = MutableLiveData<LoadingManager>()
    fun getLoadingPredictionManager(): LiveData<LoadingManager> = loadingPredictionManager


    private var loadingPlaceManager = MutableLiveData<LoadingManager>()
    fun getLoadingPlaceManager(): LiveData<LoadingManager> = loadingPlaceManager

    /** @author @buren ---> {this micro-service gets autocomplete results}*/
    fun requestListOfSearchResults(placeHint: String, apiKey: String, location: String, radius: String) {
        loadingPredictionManager.postValue(LoadingManager.STATE_REFRESHING)

        RESTAPIManager.getInstance().getPlaceResults(
            placeHint = placeHint,
            apiKey = apiKey,
            location = location,
            radius = radius
        )
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                it.predictions
            }
            .subscribeWith(object : DisposableObserver<List<PredictionsItem?>>() {
                override fun onComplete() {

                }

                override fun onNext(t: List<PredictionsItem?>) {
                    listOfSearchResults.postValue(t)
                    loadingPredictionManager.postValue(LoadingManager.STATE_COMPLETED)
                }

                override fun onError(e: Throwable) {
                    loadingPredictionManager.postValue(LoadingManager.STATE_ERROR)
                }

            })


    }

    /** @author @buren ---> {micro-service used to get live data stream of search results}*/
    fun getLiveListOfSearchResultsStream(): LiveData<List<PredictionsItem?>> = listOfSearchResults

    private var placeDetails: MutableLiveData<PlaceDetails?> = MutableLiveData()

    /** @author @buren ---> {micro-service used to get place details}*/
    fun requestPlaceDetails(placeId: String, apiKey: String) {
        loadingPlaceManager.postValue(LoadingManager.STATE_REFRESHING)
        RESTAPIManager.getInstance().getPlaceDetailsFromPlaceId(placeId = placeId, apiKey = apiKey)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .filter {
                it.status.equals(SearchPlacesStatusCodes.GOOGLE_SEARCH_RESULT_OK)
            }.map {
                it.result
            }.subscribe {
                loadingPlaceManager.postValue(LoadingManager.STATE_COMPLETED)
                placeDetails.postValue(it)
            }
    }

    /** @author @buren ---> {micro-service used to get live data stream of place details}*/
    fun getPlaceDetailsLiveDataStream(): LiveData<PlaceDetails?> = placeDetails

}