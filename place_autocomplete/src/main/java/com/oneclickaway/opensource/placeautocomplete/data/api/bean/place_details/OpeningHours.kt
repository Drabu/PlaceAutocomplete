package com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

/** @author @buren ---> {Google response for place details}*/
@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class OpeningHours(

    @field:SerializedName("open_now")
    var openNow: Boolean? = null,

    @field:SerializedName("periods")
    var periods: List<PeriodsItem?>? = null,

    @field:SerializedName("weekday_text")
    var weekdayText: List<String?>? = null
) : Parcelable