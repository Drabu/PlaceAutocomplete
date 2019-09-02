package com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

/** @author @buren ---> {Google response for place details}*/
@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class Geometry(

    @field:SerializedName("viewport")
    var viewport: Viewport? = null,

    @field:SerializedName("location")
    var location: Location? = null
) : Parcelable