package com.oneclickaway.opensource.placeautocomplete.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details.PlaceDetails

@Dao
interface RecentSearchesDAO {

    @Query("")
    fun getRecentSearches(): List<PlaceDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSearchItem(placeDetails: PlaceDetails)

}