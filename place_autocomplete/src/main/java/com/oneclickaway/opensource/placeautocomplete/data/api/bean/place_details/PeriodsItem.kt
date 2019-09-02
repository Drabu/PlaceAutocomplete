package com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

/** @author @buren ---> {Google response for place details}*/
@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class PeriodsItem(

    @field:SerializedName("close")
    var close: Close? = null,

    @field:SerializedName("open")
    var open: Open? = null
) : Parcelable