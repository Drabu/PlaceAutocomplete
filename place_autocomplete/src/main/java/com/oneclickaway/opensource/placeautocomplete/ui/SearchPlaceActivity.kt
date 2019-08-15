package com.oneclickaway.opensource.placeautocomplete.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.widget.RxTextView
import com.oneclickaway.opensource.placeautocomplete.R
import com.oneclickaway.opensource.placeautocomplete.data.api.bean.places_response.PredictionsItem
import com.oneclickaway.opensource.placeautocomplete.data.view_model.SearchPlacesViewModel
import com.oneclickaway.opensource.placeautocomplete.interfaces.PlaceClickListerner
import com.oneclickaway.opensource.placeautocomplete.utils.Commons.isNetworkConnected
import com.oneclickaway.opensource.placeautocomplete.utils.LoadingManager
import com.oneclickaway.opensource.placeautocomplete.utils.LoadingManager.*
import com.oneclickaway.opensource.placeautocomplete.utils.SearchPlacesStatusCodes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize
import java.util.concurrent.TimeUnit

/** @author @buren ---> {This activity will take care of picking the place and returning back the response}*/
@SuppressLint("CheckResult")
class SearchPlaceActivity : AppCompatActivity(), PlaceClickListerner, View.OnClickListener {

    private lateinit var viewModel: SearchPlacesViewModel
    private lateinit var searchListAdapter: SearchResultAdapter
    lateinit var gettingPlaceData: Dialog

    private var apiKey: String? = null
    private var location: String? = null
    private var enclosingRadius: String? = null

    /*view*/

    lateinit var searchTitleTV: TextView
    lateinit var searchProgressBar: ProgressBar
    lateinit var secondaryInformationLL: LinearLayout
    lateinit var placeNamET: EditText
    lateinit var searchResultsRV: RecyclerView

    lateinit var secondaryInfoSubTitleTV: TextView
    lateinit var secondaryInfoTitleTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_place)

        inflateViews()

        initializeDependency()

        initDialogForGettingPlaceDetails()

        setViewModel()

        setOnClickListeners()

        setRecyclerView()

        setOnQueryChangeListener()

        attachLiveObservers()

    }

    private fun inflateViews() {

        searchTitleTV = findViewById(R.id.searchTitleTV)
        searchProgressBar = findViewById(R.id.searchProgressBar)
        secondaryInformationLL = findViewById(R.id.secondaryInformationLL)
        placeNamET = findViewById(R.id.placeNamET)
        searchResultsRV = findViewById(R.id.searchResultsRV)

        secondaryInfoTitleTV = findViewById(R.id.secondaryInfoTitleTV)
        secondaryInfoSubTitleTV = findViewById(R.id.secondaryInfoSubTitleTV)

    }

    private fun setOnClickListeners() {
//        binding.backImageBtn.setOnClickListener(this)
    }


    private fun setSecondaryStateInformation(pageStatus: LoadingManager) {

        searchResultsRV.visibility = GONE
        searchProgressBar.visibility = GONE
        secondaryInformationLL.visibility = VISIBLE

        when (pageStatus) {

            STATE_NO_INTERNET -> {
                secondaryInfoTitleTV.text = "No internet"
                secondaryInfoSubTitleTV.text = "Please connect to internet and try again"

            }

            STATE_ERROR -> {
                secondaryInfoTitleTV.text = "Oops!"
                secondaryInfoSubTitleTV.text = "We're having some trouble connecting to our servers"
            }

            STATE_NO_RESULT -> {
                secondaryInfoTitleTV.text = getString(R.string.location_not_found)
                secondaryInfoSubTitleTV.text = getString(R.string.please_check_spell_errors)
            }


            else -> {
                print("no state detected")
            }
        }

    }

    private fun initializeDependency() {


        if (intent.hasExtra(SearchPlacesStatusCodes.CONFIG)) {

            val configuration = intent.extras?.getParcelable<Config>(SearchPlacesStatusCodes.CONFIG)
            apiKey = configuration?.apiKey
            location = configuration?.location
            enclosingRadius = configuration?.enclosingRadius
            searchTitleTV.text = configuration?.searchBarTitle

        } else {
            /*finish*/
            Toast.makeText(this, "Please mention the api key in put-extra", Toast.LENGTH_LONG).show()
            finish()
        }

    }

    private fun attachLiveObservers() {

        viewModel.getLiveListOfSearchResultsStream().observe(this, Observer {
            //refresh the adapter here
            searchListAdapter.setSearchCandidates(it)

        })


        viewModel.getPlaceDetailsLiveDataStream().observe(this, Observer {

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


        viewModel.getLoadingPredictionManager().observe(this, Observer {

            when (it) {

                STATE_REFRESHING -> {
                    searchProgressBar.visibility = View.VISIBLE
                    /*user is searching a random world*/
                    if (secondaryInformationLL.visibility == VISIBLE) {
                        Log.d("VISIBLITY", "STATE_REFRESHING _ IF")
//                        searchResultsRV.visibility = View.INVISIBLE
//                        secondaryInformationLL.visibility = View.GONE

                    } else {
                        Log.d("VISIBLITY", "STATE_REFRESHING _ ELSE")

                        searchResultsRV.visibility = View.VISIBLE
                    }
                }

                STATE_COMPLETED -> {
                    Log.d("VISIBLITY", "STATE_COMPLETED")
                    searchResultsRV.visibility = View.VISIBLE
                    searchProgressBar.visibility = View.GONE
                    secondaryInformationLL.visibility = View.GONE

                }


                STATE_NO_INTERNET -> {
                    Log.d("VISIBLITY", "STATE_NO_INTERNET")
                    setSecondaryStateInformation(STATE_NO_INTERNET)
                }


                STATE_ERROR -> {
                    Log.d("VISIBLITY", "STATE_ERROR")
                    setSecondaryStateInformation(STATE_ERROR)
                }

                STATE_NO_RESULT -> {
                    Log.d("VISIBLITY", "STATE_NO_RESULT")
                    setSecondaryStateInformation(STATE_NO_RESULT)

                }

                STATE_IDLE -> {
                    Log.d("VISIBLITY", "STATE_IDLE")
                    searchResultsRV.visibility = View.GONE
                    searchProgressBar.visibility = View.GONE
                    secondaryInformationLL.visibility = View.GONE
                }


                else -> print("No loading state detected")
            }

        })


        viewModel.getLoadingPlaceManager().observe(this, Observer {

            when (it) {

                STATE_REFRESHING -> {
                    gettingPlaceData.show()
                }

                STATE_COMPLETED -> {
                    gettingPlaceData.cancel()
                }


                STATE_NO_INTERNET -> {
                    gettingPlaceData.cancel()
                }


                STATE_ERROR -> {
                    gettingPlaceData.cancel()
                }


                else -> print("No loading state detected")
            }

        })

    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this).get(SearchPlacesViewModel::class.java)
    }

    private fun setRecyclerView() {

        searchResultsRV.layoutManager = LinearLayoutManager(this)
        searchListAdapter = SearchResultAdapter(placeClickListerner = this)
        searchResultsRV.adapter = searchListAdapter

    }

    private fun setOnQueryChangeListener() {

        RxTextView.textChanges(placeNamET)
            .debounce(500, TimeUnit.MILLISECONDS)
            .filter {

                runOnUiThread {
                    if (it.toString().isNotBlank()) {
                        /*search box contains location query checking internet connection*/

                    } else
                        viewModel.getLoadingPredictionManager().postValue(STATE_IDLE)
                }

                it.toString().isNotBlank()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : DisposableObserver<CharSequence?>() {
                override fun onComplete() {
                }

                override fun onNext(t: CharSequence) {

                    if (!isNetworkConnected(this@SearchPlaceActivity)) {
                        viewModel.getLoadingPredictionManager().postValue(STATE_NO_INTERNET)
                        return
                    }

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

    }

    override fun onPlaceClicked(candidateItem: PredictionsItem?) {

        viewModel.requestPlaceDetails(candidateItem?.placeId.toString(), apiKey = apiKey!!)
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

    private fun initDialogForGettingPlaceDetails() {
        // custom dialog
        gettingPlaceData = Dialog(this)
        gettingPlaceData.setContentView(R.layout.loading_place_details)

        val progressView = gettingPlaceData.findViewById<ImageView>(R.id.progressImageIV)
        Glide.with(this)
            .asGif()
            .load(R.raw.loading_spinner)
            .into(progressView)

    }
}

