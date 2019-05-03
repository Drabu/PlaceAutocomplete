package com.uipep.android.searchplaces.api.bean.place_details

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class PlacesDetailsResponse(

	@field:SerializedName("result")
	val result: PlaceDetails? = null,

	@field:SerializedName("html_attributions")
	val htmlAttributions: List<Any?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)