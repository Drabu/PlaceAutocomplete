package com.oneclickaway.opensource.placeautocomplete.api.bean.place_details

import android.os.Parcelable
import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class OpeningHours(

	@field:SerializedName("open_now")
	val openNow: Boolean? = null,

	@field:SerializedName("periods")
	val periods: List<PeriodsItem?>? = null,

	@field:SerializedName("weekday_text")
	val weekdayText: List<String?>? = null
) : Parcelable