package com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

/** @author @buren ---> {Google response for place details}*/
@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class PhotosItem(

    @field:SerializedName("photo_reference")
    var photoReference: String? = null,

    @field:SerializedName("width")
    var width: Int? = null,

    @field:SerializedName("html_attributions")
    var htmlAttributions: List<String?>? = null,

    @field:SerializedName("height")
    var height: Int? = null

) : Parcelable