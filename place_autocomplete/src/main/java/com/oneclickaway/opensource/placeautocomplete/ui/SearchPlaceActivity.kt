package com.oneclickaway.opensource.placeautocomplete.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.oneclickaway.opensource.placeautocomplete.R
import com.oneclickaway.opensource.placeautocomplete.api.bean.places_response.PredictionsItem
import com.oneclickaway.opensource.placeautocomplete.components.SearchPlacesStatusCodes
import com.oneclickaway.opensource.placeautocomplete.components.SearchPlacesViewModel
import com.oneclickaway.opensource.placeautocomplete.databinding.ActivitySearchPlaceBinding
import com.oneclickaway.opensource.placeautocomplete.interfaces.PlaceClickListerner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize
import java.util.concurrent.TimeUnit

/** @author @buren ---> {This activity will take care of picking the place and returning back the response}*/
class SearchPlaceActivity : AppCompatActivity(), PlaceClickListerner, View.OnClickListener {
    override fun onPlaceClicked(candidateItem: PredictionsItem?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var viewModel: SearchPlacesViewModel
//    private lateinit var binding: ActivitySearchPlaceBinding
    private var compositeDisposable = CompositeDisposable()
    private lateinit var searchListAdapter: SearchResultAdapter

    private var apiKey: String? = null
    private var location: String? = null
    private var enclosingRadius: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_place)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_place)

        initializeDependency()

        setViewModel()

        setOnClickListeners()

        /*setRecyclerView()*/

//        setOnQueryChangeListener()
//
//        attachLiveObservers()

    }

    private fun setOnClickListeners() {
//        binding.backImageBtn.setOnClickListener(this)
    }

    private fun initializeDependency() {


        if (intent.hasExtra(SearchPlacesStatusCodes.CONFIG)) {

            val configuration = intent.extras?.getParcelable<Config>(SearchPlacesStatusCodes.CONFIG)
            apiKey = configuration?.apiKey
            location = configuration?.location
            enclosingRadius = configuration?.enclosingRadius
//            binding.searchTitleTV.text = configuration?.searchBarTitle

        } else {
            /*finish*/
            Toast.makeText(this, "Please mention the api key in put-extra", Toast.LENGTH_LONG).show()
            finish()
        }

    }

  /*  private fun attachLiveObservers() {

        viewModel.getLiveListOfSearchResultsStream().observe(this, Observer {
            *//*refresh the adapter here*//*
            binding.searchProgressBar.visibility = View.GONE
            searchListAdapter.setSearchCandidates(it)
            if (it?.size == 0) {
                if (binding.placeNamET.text.toString().isNotEmpty()) {
                    binding.noPlacesFoundLL.visibility = View.VISIBLE
                    Log.i(javaClass.simpleName, "attachLiveObservers: List is empty!")
                } else {
                    binding.noPlacesFoundLL.visibility = View.GONE
                }

            } else {
                binding.noPlacesFoundLL.visibility = View.GONE
                Log.i(javaClass.simpleName, "attachLiveObservers: List has contents!")
            }
        })



        viewModel.getPlaceDetailsLiveDataStream().observe(this, Observer {
            binding.searchProgressBar.visibility = View.GONE
            Log.d(javaClass.simpleName, "attachLiveObservers:  ${it?.geometry?.location?.lat} $it ")
            val resultData = Intent()
            resultData.putExtra(SearchPlacesStatusCodes.PLACE_DATA, it)
            setResult(Activity.RESULT_OK, resultData)


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition()
            } else {
                finish()
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
            }

        })

    }*/

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this).get(SearchPlacesViewModel::class.java)
    }

    /*private fun setRecyclerView() {

        binding.searchResultsRV.layoutManager = LinearLayoutManager(this)
        searchListAdapter = SearchResultAdapter(placeClickListerner = this)
        binding.searchResultsRV.adapter = searchListAdapter

    }*/

    /*private fun setOnQueryChangeListener() {

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
                            apiKey = apiKey!!,
                            location = location ?: "",
                            radius = enclosingRadius ?: "500"
                        )

                    }

                    override fun onError(e: Throwable) {

                    }

                })
        )

    }*/

    /*override fun onPlaceClicked(candidateItem: PredictionsItem?) {

        binding.searchProgressBar.visibility = View.VISIBLE
        viewModel.requestPlaceDetails(candidateItem?.placeId.toString(), apiKey = apiKey!!)
    }*/

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

        when (p0?.id) {

            R.id.backImageBtn -> {
                onBackPressed()
            }

        }

    }

    /** @author @buren ---> {Configuration class for Search places}
     *
     * @param apiKey This is a mandatory field which is used to query google places api
     * @param enclosingRadius (radius in meters from current location) This is an optional field which is required to search a location within a specific radius
     * @param location You can pass your location which sorts the list as per your nearest location
     * @param searchBarTitle This can be used to set title for search activity
     *
     * */
    @Parcelize
    class Config(
        var apiKey: String,
        var location: String = "",
        var enclosingRadius: String = "",
        var searchBarTitle: String = "Enter Location"
    ) : Parcelable

}
