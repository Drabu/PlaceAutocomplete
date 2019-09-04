package com.oneclickaway.opensource.placeautocomplete.data.repositories

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.oneclickaway.opensource.placeautocomplete.data.api.base.RESTAPIManager
import com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details.PlaceDetails
import com.oneclickaway.opensource.placeautocomplete.data.api.bean.places_response.PredictionsItem
import com.oneclickaway.opensource.placeautocomplete.data.model.room.SearchSelectedItem
import com.oneclickaway.opensource.placeautocomplete.data.room.RecentSearchesDB
import com.oneclickaway.opensource.placeautocomplete.utils.Commons.isNetworkConnected
import com.oneclickaway.opensource.placeautocomplete.utils.LoadingManager
import com.oneclickaway.opensource.placeautocomplete.utils.SearchPlacesStatusCodes
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/** @author @buren ---> {REST Service repository}*/
@SuppressLint("CheckResult")
class SearchPlacesRepo(var application: Application) {


    /*Data from lists here*/

    private val listOfSearchResults: MutableLiveData<List<PredictionsItem?>> = MutableLiveData()
    /** @author @buren ---> {micro-service used to get live data stream of search results}*/
    fun getLiveListOfSearchResultsStream(): LiveData<List<PredictionsItem?>> = listOfSearchResults

    private val placeDetails: MutableLiveData<PlaceDetails?> = MutableLiveData()
    /** @author @buren ---> {micro-service used to get live data stream of place details}*/
    fun getPlaceDetailsLiveDataStream(): LiveData<PlaceDetails?> = placeDetails

    private val recentSearches: MutableLiveData<List<SearchSelectedItem>> = MutableLiveData()
    /** @author @buren ---> {micro-service used to get live data stream of recent search data}*/
    fun getRecentSearches(): LiveData<List<SearchSelectedItem>> = recentSearches

    /*Data lists ends here*/


    /*Loading managers are mentioned here*/

    private val loadingPredictionManager = MutableLiveData<LoadingManager>()
    fun getLoadingPredictionManager(): LiveData<LoadingManager> = loadingPredictionManager

    private val loadingPlaceManager = MutableLiveData<LoadingManager>()
    fun getLoadingPlaceManager(): LiveData<LoadingManager> = loadingPlaceManager

    private val recentSearchesManager = MutableLiveData<LoadingManager>()
    /**
     *@author Burhan ud din ---> gets the search manager to alter visibility
     */
    fun getRecentSearchesManager(): LiveData<LoadingManager> = recentSearchesManager

    /*loading managers ends here*/


    /*request from view for data */

    /** @author @buren ---> {this micro-service gets autocomplete results}*/
    fun requestListOfSearchResults(
        placeHint: String,
        apiKey: String,
        location: String,
        radius: String
    ) {
        loadingPredictionManager.postValue(LoadingManager.STATE_REFRESHING)

        if (placeHint.isBlank()) {
            /*search box contains location query checking internet connection*/
            loadingPredictionManager.postValue(LoadingManager.STATE_IDLE)
            return
        }


        if (!isNetworkConnected(application)) {
            loadingPredictionManager.postValue(LoadingManager.STATE_NO_INTERNET)
            return
        }

        RESTAPIManager.getInstance().getPlaceResults(
            placeHint = placeHint,
            apiKey = apiKey,
            location = location,
            radius = radius
        )
            .filter {
                if (it.predictions?.isEmpty()!!) {
                    loadingPredictionManager.postValue(LoadingManager.STATE_NO_RESULT)
                    return@filter false
                } else {
                    return@filter true
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                it.predictions
            }
            .subscribe(object : DisposableObserver<List<PredictionsItem?>>() {
                override fun onComplete() {
                    loadingPredictionManager.postValue(LoadingManager.STATE_COMPLETED)
                }

                override fun onNext(t: List<PredictionsItem?>) {
                    listOfSearchResults.postValue(t)
                }

                override fun onError(e: Throwable) {
                    loadingPredictionManager.postValue(LoadingManager.STATE_ERROR)
                }

            })


    }

    /** @author @buren ---> {micro-service used to get place details}*/
    fun requestPlaceDetails(placeId: String, apiKey: String) {
        loadingPlaceManager.postValue(LoadingManager.STATE_REFRESHING)

        if (!isNetworkConnected(application)) {
            loadingPlaceManager.postValue(LoadingManager.STATE_NO_INTERNET)
            return
        }

        RESTAPIManager.getInstance().getPlaceDetailsFromPlaceId(placeId = placeId, apiKey = apiKey)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .filter {
                it.status.equals(SearchPlacesStatusCodes.GOOGLE_SEARCH_RESULT_OK)
            }.map {
                it.result
            }.subscribe(object : DisposableObserver<PlaceDetails>() {

                override fun onComplete() {
                    loadingPlaceManager.postValue(LoadingManager.STATE_COMPLETED)
                }

                override fun onNext(it: PlaceDetails) {
                    placeDetails.postValue(it)
                    addSearchedItemToRecents(it)


                }

                override fun onError(e: Throwable) {
                    loadingPlaceManager.postValue(LoadingManager.STATE_ERROR)
                }

            })

    }

    /** @author @buren ---> {micro-service used to get recent searches}*/
    fun requestListOfRecentSearches(mContext: Context) {
        recentSearchesManager.postValue(LoadingManager.STATE_REFRESHING)

        if (!isNetworkConnected(application)) {
            recentSearchesManager.postValue(LoadingManager.STATE_NO_INTERNET)
            return
        }

        Observable.fromCallable {
            RecentSearchesDB.getInstance(mContext)?.repDao()?.getRecentSearches()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                if (it?.isEmpty()!!) {
                    recentSearchesManager.postValue(LoadingManager.STATE_NO_RESULT)
                } else {
                    recentSearchesManager.postValue(LoadingManager.STATE_COMPLETED)
                    Log.d("arrayItems", "" + it.toString())
                    recentSearches.postValue(it)


                }
            }

    }

    fun addSearchedItemToRecents(it: PlaceDetails) {

//        Toast.makeText(application, "adddng", Toast.LENGTH_LONG).show()
        Observable.fromCallable {
            initDb()?.repDao()?.addSearchItem(
                SearchSelectedItem(
                    placeId = it.placeId!!,
                    mainText = it.name!!,
                    secondaryText = it.formattedAddress!!,
                    searchCurrentMilliseconds = System.currentTimeMillis()
                )
            )
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Unit>() {
                override fun onNext(t: Unit) {
                    Log.e(javaClass.simpleName, "addSearchedItemToRecents: onNext")
                }

                override fun onComplete() {
                    Log.e(javaClass.simpleName, "addSearchedItemToRecents: Completed")
                }


                override fun onError(e: Throwable) {
                    Log.e(javaClass.simpleName, "addSearchedItemToRecents: Error")
                }

            })


    }


    private fun initDb(): RecentSearchesDB? {
        return RecentSearchesDB.getInstance(application)

    }
}

/*request from view ends here*/


