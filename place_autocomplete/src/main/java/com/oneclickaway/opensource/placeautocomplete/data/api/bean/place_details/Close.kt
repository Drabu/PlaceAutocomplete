package com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/** @author @buren ---> {Google response for place details}*/
@Parcelize
data class Close(

    @field:SerializedName("time")
    var time: String? = null,

    @field:SerializedName("day")
    var day: Int

) : Parcelable