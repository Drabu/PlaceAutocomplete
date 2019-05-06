package com.oneclickaway.opensource.placeautocomplete.api.bean.place_details

import android.os.Parcelable
import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/** @author @buren ---> {Google response for place details}*/
@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class Close(

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("day")
	val day: Int? = null
) : Parcelable