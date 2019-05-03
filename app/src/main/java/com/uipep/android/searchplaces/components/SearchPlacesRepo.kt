package com.uipep.android.searchplaces.components

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.uipep.android.searchplaces.api.base.RESTAPIManager
import com.uipep.android.searchplaces.api.bean.place_details.PlaceDetails
import com.uipep.android.searchplaces.api.bean.places_response.PredictionsItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchPlacesRepo {

    var compositeDisposable = CompositeDisposable()


    private var listOfSearchResults: MutableLiveData<List<PredictionsItem?>> = MutableLiveData()

    /** @author @buren ---> {this microservice gets autocomplete results}*/
    fun requestListOfSearchResults(placeHint: String) {
        compositeDisposable.add(

            RESTAPIManager.getInstance().getPlaceResults(placeHint)
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

    fun getLiveListOfSearchResultsStream(): LiveData<List<PredictionsItem?>> = listOfSearchResults





    private var placeDetails: MutableLiveData<PlaceDetails?> = MutableLiveData()

    fun requestPlaceDetails(placeId: String) {


        compositeDisposable.add(

            RESTAPIManager.getInstance().getPlaceDetailsFromPlaceId(placeId = placeId)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .filter {
                    it.status.equals(StatusCodes.GOOGLE_SEARCH_RESULT_OK)
                }.map {
                    it.result
                }.subscribe {
                    placeDetails.postValue(it)
                }
        )

    }

    fun getPlaceDetailsLiveDataStream() : LiveData<PlaceDetails?> = placeDetails

    fun clear() {
        compositeDisposable.clear()
    }


}
