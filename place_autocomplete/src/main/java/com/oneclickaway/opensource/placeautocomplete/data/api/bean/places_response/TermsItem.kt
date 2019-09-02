package com.oneclickaway.opensource.placeautocomplete.data.api.bean.places_response

import com.google.gson.annotations.SerializedName

/** @author @buren ---> {Google response for predicted places}*/
data class TermsItem(

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("value")
	val value: String? = null
)