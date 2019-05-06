package com.uipep.android.searchplaces

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_location_search)

        searchLocationBtn = findViewById(R.id.searchLocationBTN)

        searchLocationBtn.setOnClickListener{
            startActivityForResult(Intent(this,  SearchPlaceActivity::class.java),  700)
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
