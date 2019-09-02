package com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details

import android.arch.persistence.room.Entity
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

/** @author @buren ---> {Google response for place details}*/

@Entity(tableName = "ReviewsItem")
@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class ReviewsItem(

    @field:SerializedName("author_name")
    var authorName: String? = null,

    @field:SerializedName("profile_photo_url")
    var profilePhotoUrl: String? = null,

    @field:SerializedName("author_url")
    var authorUrl: String? = null,

    @field:SerializedName("rating")
    var rating: Int? = null,

    @field:SerializedName("language")
    var language: String? = null,

    @field:SerializedName("text")
    var text: String? = null,

    @field:SerializedName("time")
    var time: Int? = null,

    @field:SerializedName("relative_time_description")
    var relativeTimeDescription: String? = null

) : Parcelable