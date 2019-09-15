package com.oneclickaway.opensource.placeautocomplete.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.oneclickaway.opensource.placeautocomplete.data.model.room.SearchSelectedItem

/** @author Burhan ud din ---> Database class for local storage*/
@Database(
    entities = [
        SearchSelectedItem::class
    ], version = 1, exportSchema = false
)
abstract class RecentSearchesDB : RoomDatabase() {

    /** @author Burhan ud din ---> Data Access Object with inbuilt implementation*/
    abstract fun repDao(): RecentSearchesDAO

    companion object {

        var INSTANCE: RecentSearchesDB? = null

        /**
         *@author Burhan ud din ---> Gets the instance of database
         */
        fun getInstance(context: Context): RecentSearchesDB? {

            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(
                        context.applicationContext,
                        RecentSearchesDB::class.java,
                        "recent_searches"
                    )
                        .build()

            } else {
                INSTANCE
            }
            return INSTANCE
        }
    }
}