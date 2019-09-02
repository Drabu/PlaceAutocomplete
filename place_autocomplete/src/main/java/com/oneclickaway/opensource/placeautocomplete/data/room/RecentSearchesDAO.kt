package com.oneclickaway.opensource.placeautocomplete.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.oneclickaway.opensource.placeautocomplete.data.model.room.SearchSelectedItem

@Dao
interface RecentSearchesDAO {

    @Query("SELECT * FROM SearchSelectedItem ORDER BY searchCurrentMilliseconds DESC")
    fun getRecentSearches(): List<SearchSelectedItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSearchItem(searchSelectedItem: SearchSelectedItem)

}