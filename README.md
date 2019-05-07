PlaceAutocomplete
========
Google Places Api Implementation

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/34c0864ec96f4ce8a094a60d040e7ff7)](https://www.codacy.com/app/Drabu/PlaceAutocomplete?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Drabu/PlaceAutocomplete&amp;utm_campaign=Badge_Grade)
[![](https://jitpack.io/v/Drabu/PlaceAutocomplete.svg)](https://jitpack.io/#Drabu/PlaceAutocomplete)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

After Google came up with the update that Play Services version of the Places SDK for Android (in Google Play Services 16.0.0) is deprecated as of January 29, 2019, and will be turned off on July 29, 2019.
which includes PlaceAutoComplete also to be removed from the library.I was using the same in our client project but after getting this news we had to immidiately switch to an alternative where i endded up creating this library,  PlacesAutocomplete provides you with an elegant user interface to choose a location and works the exact same as PlaceAutoComplete.

You can provide your own custom title message for the view 

You can also provide your current location and radius for location based results

This Kotlin Library is build with MVVM Archetecture and runs on top of RxJava 2. The library uses Rx debounce operator to reduce network calls and supports landscape orientation   

### How it works

<p align="center">
    <img src="demo.gif" alt="Demonstartion image."/>
</p>

Configuration
-------------


Add it in your root build.gradle at the end of repositories:
    
    allprojects {
        repositories {
          ...
          maven { url 'https://jitpack.io' }
        }
     }
Add the dependency: 

    dependencies {
		implementation 'com.github.Drabu:PlaceAutocomplete:1.0.0'
	 }

Mandatory Field: 
        
 	  val intent =  Intent(this,  SearchPlaceActivity::class.java)
      intent.putExtra(SearchPlacesStatusCodes.GOOGLE_API_KEY, "API_KEY")
	
Optional parameteres: 
        
     intent.putExtra(SearchPlacesStatusCodes.SEARCH_TITLE,  "Enter pickup location")
     intent.putExtra(SearchPlacesStatusCodes.CURRENT_LOCATION, "12.885970,77.656181")
     intent.putExtra(SearchPlacesStatusCodes.ENCLOSE_RADIUS, "500")



#Example Kotlin Class: 

    import com.oneclickaway.opensource.placeautocomplete.api.bean.place_details.PlaceDetails
    import com.oneclickaway.opensource.placeautocomplete.components.SearchPlacesStatusCodes
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
        //intent.putExtra(StatusCodes.SEARCH_TITLE,  "Enter pickup location")
        //intent.putExtra(StatusCodes.CURRENT_LOCATION, "12.885970,77.656181")
        //intent.putExtra(StatusCodes.ENCLOSE_RADIUS, "500")

        searchLocationBtn.setOnClickListener{
            startActivityForResult(intent,  700)
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 700 && resultCode == Activity.RESULT_OK){
            val placeDetails = data?.getParcelableExtra<PlaceDetails>(StatusCodes.PLACE_DATA)
        }
    }
  }
   
Usage
-----
-Minimum sdk 15

License
-----
	MIT License

	Copyright (c) 2019 Burhan ud din Drabu

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
	
