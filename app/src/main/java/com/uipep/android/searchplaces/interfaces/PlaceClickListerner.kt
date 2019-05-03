package com.uipep.android.searchplaces.interfaces

import com.uipep.android.searchplaces.api.bean.places_response.PredictionsItem

/** @author @buren ---> {place click listener used to get clicked item}*/
interface PlaceClickListerner {

    /** @author @buren --->  {place clicked}*/
    fun onPlaceClicked(candidateItem: PredictionsItem?)

}