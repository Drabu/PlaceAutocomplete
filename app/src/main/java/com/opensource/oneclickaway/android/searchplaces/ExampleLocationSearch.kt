package com.opensource.oneclickaway.android.searchplaces

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.oneclickaway.opensource.placeautocomplete.api.bean.place_details.PlaceDetails
import com.oneclickaway.opensource.placeautocomplete.components.StatusCodes
import com.oneclickaway.opensource.placeautocomplete.ui.SearchPlaceActivity

class ExampleLocationSearch : AppCompatActivity() {

    lateinit var searchLocationBtn  : Button

    var API_KEY     = "API_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_location_search)

        searchLocationBtn = findViewById(R.id.searchLocationBTN)

        val intent =  Intent(this,  SearchPlaceActivity::class.java)
        intent.putExtra(StatusCodes.GOOGLE_API_KEY, API_KEY)
//        intent.putExtra(StatusCodes.SEARCH_TITLE,  "Enter pickup location")
//        intent.putExtra(StatusCodes.CURRENT_LOCATION, "12.885970,77.656181")
//        intent.putExtra(StatusCodes.ENCLOSE_RADIUS, "10000")

        searchLocationBtn.setOnClickListener{
            startActivityForResult(intent,  700)
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 700 && resultCode == Activity.RESULT_OK){

            val placeDetails = data?.getParcelableExtra<PlaceDetails>(StatusCodes.PLACE_DATA)

            Log.i(javaClass.simpleName, "onActivityResult: ${placeDetails?.name}  ${placeDetails?.geometry?.location} ")
        }
    }
}
