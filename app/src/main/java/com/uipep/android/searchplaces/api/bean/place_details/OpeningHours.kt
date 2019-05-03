package com.uipep.android.searchplaces.api.bean.place_details

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class OpeningHours(

	@field:SerializedName("open_now")
	val openNow: Boolean? = null,

	@field:SerializedName("periods")
	val periods: List<PeriodsItem?>? = null,

	@field:SerializedName("weekday_text")
	val weekdayText: List<String?>? = null
)