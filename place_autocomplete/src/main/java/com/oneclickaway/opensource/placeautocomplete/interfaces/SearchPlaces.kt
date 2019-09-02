package com.oneclickaway.opensource.placeautocomplete.interfaces

import com.oneclickaway.opensource.placeautocomplete.data.api.bean.places_response.PredictionsItem
import com.oneclickaway.opensource.placeautocomplete.utils.GroupStrategy

/** @author @buren ---> {place click listener used to get clicked item}*/

interface SearchPlaces {

    interface PlaceItemSelectedListener {
        /** @author @buren --->  {place clicked}*/
        fun onPlaceItemSelected(candidateItem: PredictionsItem?)

    }

    interface RecentItemSelectedListener {
        fun onRecantsItemSelected(savedItem: GroupStrategy.ListItem)
    }

}

