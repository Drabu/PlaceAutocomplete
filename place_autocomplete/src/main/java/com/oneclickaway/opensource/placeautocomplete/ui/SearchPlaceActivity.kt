package com.oneclickaway.opensource.placeautocomplete.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.oneclickaway.opensource.placeautocomplete.R
import com.oneclickaway.opensource.placeautocomplete.api.bean.places_response.PredictionsItem
import com.oneclickaway.opensource.placeautocomplete.components.SearchPlacesViewModel
import com.oneclickaway.opensource.placeautocomplete.components.StatusCodes
import com.oneclickaway.opensource.placeautocomplete.databinding.ActivitySearchPlaceBinding
import com.oneclickaway.opensource.placeautocomplete.interfaces.PlaceClickListerner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchPlaceActivity : AppCompatActivity(), PlaceClickListerner , View.OnClickListener{

    lateinit var viewModel: SearchPlacesViewModel


    private lateinit var binding: ActivitySearchPlaceBinding


    var compositeDisposable = CompositeDisposable()

    lateinit var searchListAdapter: SearchResultAdapter


    var API_KEY: String? = null

    var LOCATION: String? = null

    var ENCLOSING_RADIUS: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_place)

        initializeDependency()

        setViewModel()


        setOnClickListeners()

        setRecyclerView()

        setOnQueryChangeListener()

        attachLiveObservers()

    }

    private fun setOnClickListeners() {
        binding.backImageBtn.setOnClickListener(this)
    }

    private fun initializeDependency() {
        if (intent.hasExtra(StatusCodes.GOOGLE_API_KEY)) {
            API_KEY = intent.extras?.getString(StatusCodes.GOOGLE_API_KEY, "na")
            Log.d(javaClass.simpleName, "initializeDependency: api key")

            if (intent.hasExtra(StatusCodes.CURRENT_LOCATION)) {
                LOCATION = intent.extras?.getString(StatusCodes.CURRENT_LOCATION, "na")
                Log.d(
                    javaClass.simpleName,
                    "initializeDependency: init Location " + intent.extras?.getString(
                        StatusCodes.CURRENT_LOCATION,
                        "na"
                    )
                )
            }

            if (intent.hasExtra(StatusCodes.ENCLOSE_RADIUS)) {
                ENCLOSING_RADIUS = intent.extras?.getString(StatusCodes.ENCLOSE_RADIUS, "na")
                Log.d(
                    javaClass.simpleName,
                    "initializeDependency: init radius" + intent.extras?.getString(StatusCodes.ENCLOSE_RADIUS, "na")
                )
            }

            if (intent.hasExtra(StatusCodes.SEARCH_TITLE)) {
                binding.searchTitleTV.text = intent.extras?.getString(StatusCodes.SEARCH_TITLE, getString(R.string.search_title))

            }


        } else {
            Toast.makeText(this, "Please mention the api key in put-extra", Toast.LENGTH_LONG).show()
            finish()
        }

    }

    private fun attachLiveObservers() {

        viewModel.getLiveListOfSearchResultsStream().observe(this, Observer {
            /*refresh the adapter here*/
            binding.searchProgressBar.visibility = View.GONE
            searchListAdapter.setSearchCandidates(it)
        })



        viewModel.getPlaceDetailsLiveDataStream().observe(this, Observer {
            binding.searchProgressBar.visibility = View.GONE
            Log.d(javaClass.simpleName, "attachLiveObservers:  ${it?.geometry?.location?.lat} $it ")
            val resultData = Intent()
            resultData.putExtra(StatusCodes.PLACE_DATA, it)
            setResult(Activity.RESULT_OK, resultData)
            finish()
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)

        })

    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this).get(SearchPlacesViewModel::class.java)
    }

    private fun setOnQueryChangeListener() {

        compositeDisposable.add(

            RxTextView.textChanges(binding.placeNamET)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter {

                    runOnUiThread {
                        if (it.toString().isNotBlank())
                            binding.searchResultsRV.visibility = View.VISIBLE
                        else
                            binding.searchResultsRV.visibility = View.GONE
                    }

                    it.toString().isNotBlank()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<CharSequence?>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: CharSequence) {
                        Log.d(javaClass.simpleName, "setOnQueryChangeListener: ${t}")
                        binding.searchProgressBar.visibility = View.VISIBLE
                        viewModel.requestListOfSearchResults(
                            placeHint = t.toString(),
                            apiKey = API_KEY!!,
                            location = LOCATION ?: "",
                            radius = ENCLOSING_RADIUS ?: "500"
                        )

                    }

                    override fun onError(e: Throwable) {

                    }

                })
        )

    }


    private fun setRecyclerView() {

        binding.searchResultsRV.layoutManager = LinearLayoutManager(this)
        searchListAdapter = SearchResultAdapter(placeClickListerner = this)
        binding.searchResultsRV.adapter = searchListAdapter

    }

    override fun onPlaceClicked(candidateItem: PredictionsItem?) {

        binding.searchProgressBar.visibility = View.VISIBLE
        viewModel.requestPlaceDetails(candidateItem?.placeId.toString(), apiKey = API_KEY!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
        compositeDisposable.clear()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
    }

    override fun onClick(p0: View?) {

        when( p0?.id ){

            R.id.backImageBtn -> {
                onBackPressed()
            }

        }

    }
}
