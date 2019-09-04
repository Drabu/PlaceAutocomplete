package com.opensource.oneclickaway.android.searchplaces

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Pair
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details.PlaceDetails
import com.oneclickaway.opensource.placeautocomplete.ui.SearchPlaceActivity
import com.oneclickaway.opensource.placeautocomplete.utils.SearchPlacesStatusCodes

/** @author @buren ---> {Sample activity taht implements the feature}*/
class ExampleLocationSearch : AppCompatActivity() {

    lateinit var searchLocationET: EditText
    lateinit var placeDetailsTV: TextView

    var API_KEY = BuildConfig.ApiKey

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_location_search)

        searchLocationET = findViewById(R.id.searchLocationBTN)
        placeDetailsTV = findViewById(R.id.resultPlaceDetailsTV)

        val intent = Intent(this, SearchPlaceActivity::class.java)
        intent.putExtra(
            SearchPlacesStatusCodes.CONFIG,
            SearchPlaceActivity.Config.Builder(apiKey = API_KEY)
                .setSearchBarTitle("Enter Source Location")
                .setMyLocation("12.9716,77.5946")
                .setEnclosingRadius("500")
                .build()
        )

        searchLocationET.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val pair = Pair.create(
                    searchLocationET as View,
                    SearchPlacesStatusCodes.PLACEHOLDER_TRANSITION
                )
                val options = ActivityOptions.makeSceneTransitionAnimation(this, pair).toBundle()
                startActivityForResult(intent, 700, options)

            } else {
                startActivityForResult(intent, 700)
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
            }


        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 700 && resultCode == Activity.RESULT_OK) {

            val placeDetails =
                data?.getParcelableExtra<PlaceDetails>(SearchPlacesStatusCodes.PLACE_DATA)

            searchLocationET.setText(placeDetails?.name)

            placeDetailsTV.text = placeDetails.toString()
            Log.i(javaClass.simpleName, "onActivityResult: ${placeDetails}  ")
        }
    }
}
