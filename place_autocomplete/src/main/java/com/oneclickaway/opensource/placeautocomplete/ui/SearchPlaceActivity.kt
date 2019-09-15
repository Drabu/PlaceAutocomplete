package com.oneclickaway.opensource.placeautocomplete.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.arch.lifecycle.MutableLiveData
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
import com.oneclickaway.opensource.placeautocomplete.data.adapter.RecentSearchesAdapter
import com.oneclickaway.opensource.placeautocomplete.data.adapter.SearchResultAdapter
import com.oneclickaway.opensource.placeautocomplete.data.api.bean.places_response.PredictionsItem
import com.oneclickaway.opensource.placeautocomplete.data.model.view.SearchPlacesViewModel
import com.oneclickaway.opensource.placeautocomplete.data.room.RecentSearchesDB
import com.oneclickaway.opensource.placeautocomplete.interfaces.SearchPlaces
import com.oneclickaway.opensource.placeautocomplete.utils.GroupStrategy
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
class SearchPlaceActivity : AppCompatActivity(), SearchPlaces.PlaceItemSelectedListener,
    SearchPlaces.RecentItemSelectedListener, View.OnClickListener {

    private lateinit var viewModel: SearchPlacesViewModel
    private lateinit var searchListAdapter: SearchResultAdapter
    lateinit var gettingPlaceDataDialog: Dialog

    private var apiKey: String? = null
    private var location: String? = null
    private var enclosingRadius: String? = null

    private var liveQueryInEditText: MutableLiveData<String> = MutableLiveData()
    /*view*/

    lateinit var searchTitleTV: TextView
    lateinit var searchProgressBar: ProgressBar
    lateinit var secondaryInformationLL: LinearLayout
    lateinit var placeNamET: EditText
    lateinit var searchResultsRV: RecyclerView

    lateinit var secondaryInfoSubTitleTV: TextView
    lateinit var secondaryInfoTitleTV: TextView
    lateinit var backImageBtn: ImageView

    lateinit var eraseCurrentEntryIV: ImageView

    var recentSearchesDB: RecentSearchesDB? = null
    var recentSearchesLL: LinearLayout? = null

    lateinit var recentSearchesRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_place)

        setViewModel()

        initDb()

        inflateViews()

        initializeDependency()

        initDialogForGettingPlaceDetails()

        setOnClickListeners()

        setRecyclerView()

        setOnQueryChangeListener()

        attachLiveObservers()


    }

    private fun initDb() {
        recentSearchesDB = RecentSearchesDB.getInstance(this)
        getViewModel().requestListOfRecentSearches()
    }

    private fun inflateViews() {

        searchTitleTV = findViewById(R.id.searchTitleTV)
        searchProgressBar = findViewById(R.id.searchProgressBar)
        secondaryInformationLL = findViewById(R.id.secondaryInformationLL)
        placeNamET = findViewById(R.id.placeNamET)
        searchResultsRV = findViewById(R.id.searchResultsRV)
        secondaryInfoTitleTV = findViewById(R.id.secondaryInfoTitleTV)
        secondaryInfoSubTitleTV = findViewById(R.id.secondaryInfoSubTitleTV)
        backImageBtn = findViewById(R.id.backImageBtn)
        recentSearchesRV = findViewById(R.id.recentSearchesRV)
        recentSearchesLL = findViewById(R.id.recentSearchesLL)
        eraseCurrentEntryIV = findViewById(R.id.eraseCurrentEntryIV)

    }

    private fun setOnClickListeners() {
        backImageBtn.setOnClickListener(this)
        eraseCurrentEntryIV.setOnClickListener(this)
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
            Toast.makeText(this, "Please mention the api key in put-extra", Toast.LENGTH_LONG)
                .show()
            finish()
        }

    }

    private fun attachLiveObservers() {

        getViewModel().getLiveListOfSearchResultsStream().observe(this, Observer {
            //refresh the adapter here
            searchListAdapter.setSearchCandidates(it)

        })

        getViewModel().getPlaceDetailsLiveDataStream().observe(this, Observer {

            if (it != null) {
                val resultData = Intent()
                resultData.putExtra(SearchPlacesStatusCodes.PLACE_DATA, it)
                setResult(Activity.RESULT_OK, resultData)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition()
            } else {
                finish()
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
            }

        })

        getViewModel().getLoadingPredictionManager().observe(this, Observer {

            when (it) {

                STATE_REFRESHING -> {

                    recentSearchesLL!!.visibility = GONE
                    searchProgressBar.visibility = View.VISIBLE
                    /*user is searching a random world*/
                    if (secondaryInformationLL.visibility == VISIBLE) {
                        /*STATE_REFRESHING */
                    } else {
                        searchResultsRV.visibility = View.VISIBLE
                    }
                }

                STATE_COMPLETED -> {
                    recentSearchesLL!!.visibility = GONE
                    searchResultsRV.visibility = View.VISIBLE
                    searchProgressBar.visibility = View.GONE
                    secondaryInformationLL.visibility = View.GONE
                }


                STATE_NO_INTERNET -> {
                    recentSearchesLL!!.visibility = GONE
                    setSecondaryStateInformation(STATE_NO_INTERNET)
                }


                STATE_ERROR -> {
                    recentSearchesLL!!.visibility = GONE
                    Log.d("VISIBLITY", "STATE_ERROR")
                    setSecondaryStateInformation(STATE_ERROR)
                }

                STATE_NO_RESULT -> {
                    recentSearchesLL!!.visibility = GONE
                    setSecondaryStateInformation(STATE_NO_RESULT)

                }

                STATE_IDLE -> {
                    eraseCurrentEntryIV.visibility = GONE
                    searchResultsRV.visibility = GONE
                    searchProgressBar.visibility = GONE
                    secondaryInformationLL.visibility = GONE
                    recentSearchesLL!!.visibility = VISIBLE
                }


                else -> print("No loading state detected")
            }

        })

        getViewModel().getLoadingPlaceManager().observe(this, Observer {

            when (it) {

                STATE_REFRESHING -> {
                    gettingPlaceDataDialog.show()
                }

                STATE_COMPLETED -> {
                    gettingPlaceDataDialog.cancel()
                }


                STATE_NO_INTERNET -> {
                    gettingPlaceDataDialog.cancel()
                    Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
                }


                STATE_ERROR -> {
                    gettingPlaceDataDialog.cancel()
                    Toast.makeText(
                        this,
                        getString(R.string.trouble_getting_data),
                        Toast.LENGTH_LONG
                    ).show()
                }


                else -> print("No loading state detected")
            }

        })

        getViewModel().getRecentSearchesManager().observe(this, Observer {

            when (it) {

                STATE_COMPLETED -> {
                }

                STATE_REFRESHING -> {
                }

                STATE_ERROR -> {
                    Toast.makeText(this, "State Error", Toast.LENGTH_LONG).show()
                }

                STATE_NO_INTERNET -> {

                }

                STATE_NO_RESULT -> {
                }


                else -> {


                }
            }
        })

        getViewModel().getRecentSearchesData().observe(this, Observer {
            if (it != null) {
                recentSearchesRV.layoutManager = LinearLayoutManager(this)
                recentSearchesRV.adapter =
                    RecentSearchesAdapter(GroupStrategy().groupDataByTime(it), this)
            }
        })

        attachEraserObserver()

    }

    private fun attachEraserObserver() {


        liveQueryInEditText.observe(this, Observer {

            if (it != null && it.isNotEmpty()) {
                eraseCurrentEntryIV.visibility = VISIBLE
            } else {
                eraseCurrentEntryIV.visibility = GONE

            }
        })

    }




    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this).get(SearchPlacesViewModel::class.java)
    }

    private fun setRecyclerView() {

        searchResultsRV.layoutManager = LinearLayoutManager(this)
        searchListAdapter =
            SearchResultAdapter(placeItemSelectedListener = this)
        searchResultsRV.adapter = searchListAdapter

    }

    private fun setOnQueryChangeListener() {

        RxTextView.textChanges(placeNamET)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : DisposableObserver<CharSequence?>() {
                override fun onComplete() {
                    print("Completed")
                }

                override fun onNext(t: CharSequence) {

                    liveQueryInEditText.postValue(t.toString())

                    getViewModel().requestListOfSearchResults(
                        placeHint = t.toString(),
                        apiKey = apiKey!!,
                        location = location ?: "",
                        radius = enclosingRadius ?: "500"
                    )

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

            })

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

            R.id.eraseCurrentEntryIV -> {
                placeNamET.text.clear()
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
    ) : Parcelable {

        @Parcelize
        class Builder(var apiKey: String) : Parcelable {

            var location: String = ""
            var enclosingRadius: String = ""
            var searchBarTitle: String = "Enter Location"

            /**
             *@author Burhan ud din ---> Set location in lat, lng which sorts location resulsts based on your lcoation
             */
            fun setMyLocation(location: String): Builder {
                this.location = location
                return this
            }

            /**
             *@author Burhan ud din ---> enclosing radius
             */
            fun setEnclosingRadius(enclosingRadius: String): Builder {
                this.enclosingRadius = enclosingRadius
                return this
            }

            /**
             *@author Burhan ud din ---> Title of the bar
             */
            fun setSearchBarTitle(searchBarTitle: String): Builder {
                this.searchBarTitle = searchBarTitle
                return this
            }

            /**
             *@author Burhan ud din ---> Build the object
             */
            fun build(): Config {
                return Config(
                    apiKey = apiKey,
                    enclosingRadius = enclosingRadius,
                    location = location,
                    searchBarTitle = searchBarTitle
                )
            }


        }


    }

    private fun initDialogForGettingPlaceDetails() {
        // custom dialog
        gettingPlaceDataDialog = Dialog(this)
        gettingPlaceDataDialog.setContentView(R.layout.loading_place_details)

        val progressView = gettingPlaceDataDialog.findViewById<ImageView>(R.id.progressImageIV)
        Glide.with(this)
            .asGif()
            .load(R.raw.loading_spinner)
            .into(progressView)
    }

    override fun onRecantsItemSelected(savedItem: GroupStrategy.ListItem) {
        if (savedItem is GroupStrategy.GeneralItem) {
            /*tapped on place*/
            getViewModel().requestPlaceDetails(savedItem.searchSelectedItem.placeId, apiKey!!)
        } else {
            /*tapped on group title*/
        }

    }

    override fun onPlaceItemSelected(candidateItem: PredictionsItem?) {
        if (candidateItem != null) {
            getViewModel().requestPlaceDetails(candidateItem.placeId.toString(), apiKey = apiKey!!)
        }

    }

    private fun getViewModel() = viewModel
}

