package com.oneclickaway.opensource.placeautocomplete.data.model.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 *@author Burhan ud din ---> item table
 */
@Entity(tableName = "SearchSelectedItem")
data class SearchSelectedItem(
    @PrimaryKey var placeId: String,
    var mainText: String,
    var secondaryText: String,
    var searchCurrentMilliseconds: Long
)