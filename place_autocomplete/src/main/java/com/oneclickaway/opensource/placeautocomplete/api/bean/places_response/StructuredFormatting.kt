package com.oneclickaway.opensource.placeautocomplete.api.bean.places_response

import com.google.gson.annotations.SerializedName

/** @author @buren ---> {Google response for predicted places}*/
data class StructuredFormatting(

	@field:SerializedName("main_text_matched_substrings")
	val mainTextMatchedSubstrings: List<MainTextMatchedSubstringsItem?>? = null,

	@field:SerializedName("secondary_text")
	val secondaryText: String? = null,

	@field:SerializedName("main_text")
	val mainText: String? = null
)