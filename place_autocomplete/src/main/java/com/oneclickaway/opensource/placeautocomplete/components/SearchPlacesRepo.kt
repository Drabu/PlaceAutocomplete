package com.oneclickaway.opensource.placeautocomplete.components

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.oneclickaway.opensource.placeautocomplete.api.base.RESTAPIManager
import com.oneclickaway.opensource.placeautocomplete.api.bean.place_details.PlaceDetails
import com.oneclickaway.opensource.placeautocomplete.api.bean.places_response.PredictionsItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/** @author @buren ---> {REST Service repository}*/
class SearchPlacesRepo {

    private var compositeDisposable = CompositeDisposable()

    private var listOfSearchResults: MutableLiveData<List<PredictionsItem?>> = MutableLiveData()

    /** @author @buren ---> {this micro-service gets autocomplete results}*/
    fun requestListOfSearchResults(placeHint: String, apiKey: String, location: String, radius: String) {
        compositeDisposable.add(

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
                    }

                    override fun onError(e: Throwable) {

                    }

                })

        )


    }

    /** @author @buren ---> {micro-service used to get live data stream of search results}*/
    fun getLiveListOfSearchResultsStream(): LiveData<List<PredictionsItem?>> = listOfSearchResults

    private var placeDetails: MutableLiveData<PlaceDetails?> = MutableLiveData()

    /** @author @buren ---> {micro-service used to get place details}*/
    fun requestPlaceDetails(placeId: String, apiKey: String) {


        compositeDisposable.add(

            RESTAPIManager.getInstance().getPlaceDetailsFromPlaceId(placeId = placeId, apiKey = apiKey)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .filter {
                    it.status.equals(SearchPlacesStatusCodes.GOOGLE_SEARCH_RESULT_OK)
                }.map {
                    it.result
                }.subscribe {
                    placeDetails.postValue(it)
                }
        )

    }

    /** @author @buren ---> {micro-service used to get live data stream of place details}*/
    fun getPlaceDetailsLiveDataStream(): LiveData<PlaceDetails?> = placeDetails

    /** @author @buren ---> {used to clear subscribers}*/
    fun clear() {
        compositeDisposable.clear()
    }


}
