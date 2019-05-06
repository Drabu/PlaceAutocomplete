package com.oneclickaway.opensource.placeautocomplete.api.bean.place_details

import android.os.Parcelable
import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/** @author @buren ---> {Google response for place details}*/
@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class Southwest(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
) : Parcelable