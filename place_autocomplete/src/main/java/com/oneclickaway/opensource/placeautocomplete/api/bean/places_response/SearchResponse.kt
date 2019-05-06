package com.oneclickaway.opensource.placeautocomplete.api.bean.places_response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("predictions")
	val predictions: List<PredictionsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)