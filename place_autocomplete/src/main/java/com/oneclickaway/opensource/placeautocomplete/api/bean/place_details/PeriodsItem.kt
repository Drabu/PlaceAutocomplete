package com.oneclickaway.opensource.placeautocomplete.api.bean.place_details

import android.os.Parcelable
import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class PeriodsItem(

	@field:SerializedName("close")
	val close: Close? = null,

	@field:SerializedName("open")
	val open: Open? = null
) : Parcelable