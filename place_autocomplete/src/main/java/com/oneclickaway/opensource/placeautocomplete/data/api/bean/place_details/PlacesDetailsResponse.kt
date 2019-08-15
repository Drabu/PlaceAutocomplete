package com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details

import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

/** @author @buren ---> {Google response for place details}*/
@Generated("com.robohorse.robopojogenerator")
data class PlacesDetailsResponse(

	@field:SerializedName("result")
	val result: PlaceDetails? = null,

	@field:SerializedName("html_attributions")
	val htmlAttributions: List<Any?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)