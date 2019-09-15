package com.oneclickaway.opensource.placeautocomplete.interfaces

import com.oneclickaway.opensource.placeautocomplete.data.api.bean.places_response.PredictionsItem
import com.oneclickaway.opensource.placeautocomplete.utils.GroupStrategy

/** @author @buren ---> {place click listener used to get clicked item}*/
interface SearchPlaces {

    /**
     *@author Burhan ud din ---> Place selected
     */
    interface PlaceItemSelectedListener {
        /** @author @buren --->  {place clicked}*/
        fun onPlaceItemSelected(candidateItem: PredictionsItem?)

    }

    /**
     *@author Burhan ud din ---> place selected in recent
     */
    interface RecentItemSelectedListener {
        /**
         *@author Burhan ud din ---> recent items
         */
        fun onRecantsItemSelected(savedItem: GroupStrategy.ListItem)
    }

}

