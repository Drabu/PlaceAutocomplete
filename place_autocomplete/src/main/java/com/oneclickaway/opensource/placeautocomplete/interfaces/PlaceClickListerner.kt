package com.oneclickaway.opensource.placeautocomplete.interfaces

import com.oneclickaway.opensource.placeautocomplete.data.api.bean.places_response.PredictionsItem

/** @author @buren ---> {place click listener used to get clicked item}*/
interface PlaceClickListerner {

    /** @author @buren --->  {place clicked}*/
    fun onPlaceClicked(candidateItem: PredictionsItem?)

}