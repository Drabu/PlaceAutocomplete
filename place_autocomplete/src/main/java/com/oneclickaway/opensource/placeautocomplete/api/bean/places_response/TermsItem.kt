package com.oneclickaway.opensource.placeautocomplete.api.bean.places_response

import com.google.gson.annotations.SerializedName

data class TermsItem(

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("value")
	val value: String? = null
)