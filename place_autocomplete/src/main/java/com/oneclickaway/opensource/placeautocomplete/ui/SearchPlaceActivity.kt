package com.oneclickaway.opensource.placeautocomplete.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import com.jakewharton.rxbinding2.widget.RxTextView
import com.oneclickaway.opensource.placeautocomplete.R
import com.oneclickaway.opensource.placeautocomplete.api.bean.places_response.PredictionsItem
import com.oneclickaway.opensource.placeautocomplete.components.SearchPlacesViewModel
import com.oneclickaway.opensource.placeautocomplete.components.StatusCodes
import com.oneclickaway.opensource.placeautocomplete.interfaces.PlaceClickListerner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchPlaceActivity : AppCompatActivity(), PlaceClickListerner {
    lateinit var viewModel: SearchPlacesViewModel


    lateinit var placeNamET: EditText

    lateinit var searchResultsRV: RecyclerView

    lateinit var searchProgressbar : ProgressBar

    var compositeDisposable = CompositeDisposable()

    lateinit var searchListAdapter: SearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_place)

        inflateViews()

        setOnQueryChangeListener()

        attachLiveObservers()

    }

    private fun attachLiveObservers() {

        viewModel.getLiveListOfSearchResultsStream().observe(this, Observer {
            /*refresh the adapter here*/
            searchProgressbar.visibility = View.GONE
            searchListAdapter.setSearchCandidates(it)
        })



        viewModel.getPlaceDetailsLiveDataStream().observe(this, Observer {
            searchProgressbar.visibility = View.GONE
            Log.d(javaClass.simpleName, "attachLiveObservers:  ${it?.geometry?.location?.lat} $it " )
            val resultData =  Intent()
            resultData.putExtra(StatusCodes.PLACE_DATA, it)
            setResult(Activity.RESULT_OK, resultData)
            finish()

        })

    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this).get(SearchPlacesViewModel::class.java)
    }

    private fun setOnQueryChangeListener() {

        compositeDisposable.add(

            RxTextView.textChanges(placeNamET)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter {
                    it.toString().isNotBlank()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    Log.d(javaClass.simpleName, "setOnQueryChangeListener: ${it}")
                    searchProgressbar.visibility = View.VISIBLE
                    viewModel.requestListOfSearchResults(it.toString())
                }
        )

    }

    private fun inflateViews() {
        placeNamET = findViewById(R.id.placeNamET)
        searchResultsRV = findViewById(R.id.searchResultsRV)
        searchProgressbar = findViewById(R.id.searchProgressBar)

        setViewModel()

        setRecyclerView()

    }

    private fun setRecyclerView() {

        searchResultsRV.layoutManager = LinearLayoutManager(this)
        searchListAdapter = SearchResultAdapter(placeClickListerner = this)
        searchResultsRV.adapter = searchListAdapter

    }

    override fun onPlaceClicked(candidateItem: PredictionsItem?) {

        searchProgressbar.visibility = View.VISIBLE
        viewModel.requestPlaceDetails(candidateItem?.placeId.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
        compositeDisposable.clear()
    }

}
