package com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

/** @author @buren ---> {Google response for place details}*/
@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class Open(

    @field:SerializedName("time")
    var time: String? = null,

    @field:SerializedName("day")
    var day: Int? = null
) : Parcelable