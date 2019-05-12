PlaceAutocomplete
========
Google Places Api Implementation

[ ![Download](https://api.bintray.com/packages/drabu/PlaceAutocomplete/com.opensource.oneclickaway.android.searchplaces/images/download.svg) ](https://bintray.com/drabu/PlaceAutocomplete/com.opensource.oneclickaway.android.searchplaces/_latestVersion)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/34c0864ec96f4ce8a094a60d040e7ff7)](https://www.codacy.com/app/Drabu/PlaceAutocomplete?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Drabu/PlaceAutocomplete&amp;utm_campaign=Badge_Grade)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-PlaceAutocomplete-blue.svg?style=flat)](https://android-arsenal.com/details/1/7655)

After Google came up with the update that Play Services version of the Places SDK for Android (in Google Play Services 16.0.0) is deprecated as of January 29, 2019, and will be turned off on July 29, 2019.
which includes PlaceAutocompleteFragment also to be removed from the library.After getting this update from google we had to immediately migrate to an alternative where we ended up creating this library,  PlaceAutocompleteFragment  provides you with an elegant user interface to choose a location and works the exact same as PlaceAutocompleteFragment .
### Features
Custom title message for the view.

Sorted places based on your location provided you pass your location.

Radius of eclosure for search

Support for activity animation for a smooth transition

Suupport for landscape orientation.

This Kotlin Library is build with MVVM Archetecture and runs on top of RxJava 2. The library uses various Rx operators like debounce, throttlefirst operators to reduce network calls and optimze location query.

## Dependencies

Since this library is build with MVVM Architecture and uses Kotlin, RxAndroid, RxJava 2, Rx Binding,recyclerview, cardview, retrofit and  various android architecture components, so you can find  the dependencies [here](https://github.com/Drabu/PlaceAutocomplete/blob/master/place_autocomplete/build.gradle). and add them to your build.gradle file.

### How it works

<p align="center">
    <img src="dry_run.gif" alt="Demonstartion image."/>
</p>

Configuration
-------------

Add the dependency: 

    dependencies {
    		//copy the version from download badge above 	
		implementation 'com.opensource.oneclickaway.android.searchplaces:place_autocomplete:x.x.x'
	 }

Mandatory Field: 
        
        val intent = Intent(this, SearchPlaceActivity::class.java)
        intent.putExtra(
            SearchPlacesStatusCodes.CONFIG,
            SearchPlaceActivity.Config(apiKey = API_KEY)
        )
	
Optional parameteres: 
        
    val intent = Intent(this, SearchPlaceActivity::class.java)
        intent.putExtra(
            SearchPlacesStatusCodes.CONFIG,
            SearchPlaceActivity.Config(
                apiKey = API_KEY,
                searchBarTitle = "Enter Source Location",
                location = "12.9716,77.5946",
                enclosingRadius = "500"
            )
        )


For build version greater LOLLIPOP, you can use Activity Transition like this: 

	val pair = Pair.create(searchLocationET as View, SearchPlacesStatusCodes.PLACEHOLDER_TRANSITION)
	val options = ActivityOptions.makeSceneTransitionAnimation(this, pair).toBundle()
	startActivityForResult(intent, 700, options)
#Example Kotlin Class: 

    import com.oneclickaway.opensource.placeautocomplete.api.bean.place_details.PlaceDetails
    import com.oneclickaway.opensource.placeautocomplete.components.SearchPlacesStatusCodes
    import com.oneclickaway.opensource.placeautocomplete.ui.SearchPlaceActivity

    class ExampleLocationSearch : AppCompatActivity() {

     lateinit var searchLocationET: EditText
     lateinit var placeDetailsTV: TextView
     var REQUEST_CODE = 700

     var API_KEY = BuildConfig.ApiKey

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_location_search)

        searchLocationET = findViewById(R.id.searchLocationBTN)
        placeDetailsTV = findViewById(R.id.resultPlaceDetailsTV)

        val intent = Intent(this, SearchPlaceActivity::class.java)
        intent.putExtra(
            SearchPlacesStatusCodes.CONFIG,
            SearchPlaceActivity.Config(apiKey = API_KEY, searchBarTitle = "Enter Source Location")
        )

        searchLocationET.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val pair = Pair.create(searchLocationET as View, SearchPlacesStatusCodes.PLACEHOLDER_TRANSITION)
                val options = ActivityOptions.makeSceneTransitionAnimation(this, pair).toBundle()
                startActivityForResult(intent, REQUEST_CODE, options)

            } else {
                startActivityForResult(intent, REQUEST_CODE)
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
            }


        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val placeDetails = data?.getParcelableExtra<PlaceDetails>(SearchPlacesStatusCodes.PLACE_DATA)

            searchLocationET.setText(placeDetails?.name)

            placeDetailsTV.text = placeDetails.toString()
            Log.i(javaClass.simpleName, "onActivityResult: ${placeDetails}  ")
        }
    }
    }
   
Usage
-----
-Minimum sdk 15
-Returns place information in onActivityResult

License
-----
	MIT License

	Copyright (c) 2019 Burhan ud din

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
