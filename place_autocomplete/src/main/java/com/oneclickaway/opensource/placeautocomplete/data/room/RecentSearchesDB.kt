package com.oneclickaway.opensource.placeautocomplete.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details.*

/** @author Burhan ud din ---> Database class for local storage*/
@Database(
    entities = [
        AddressComponentsItem::class,
        Close::class,
        Geometry::class,
        Location::class,
        Northeast::class,
        Open::class,
        OpeningHours::class,
        PeriodsItem::class,
        PhotosItem::class,
        PlaceDetails::class,
        PlacesDetailsResponse::class,
        PlusCode::class,
        ReviewsItem::class,
        Southwest::class,
        Viewport::class

    ], version = 2, exportSchema = false
)
//@TypeConverters(DataTypeConverter::class)
abstract class RecentSearchesDB : RoomDatabase() {

    /** @author Burhan ud din ---> Data Access Object with inbuilt implementation*/
    abstract fun repDao(): RecentSearchesDAO

}