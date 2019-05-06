package com.oneclickaway.opensource.placeautocomplete.api.bean.places_response

import com.google.gson.annotations.SerializedName

/** @author @buren ---> {Google response for predicted places}*/
data class SearchResponse(

	@field:SerializedName("predictions")
	val predictions: List<PredictionsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)