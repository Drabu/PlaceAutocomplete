package com.uipep.android.searchplaces.api.bean.places_response

import com.google.gson.annotations.SerializedName

data class MainTextMatchedSubstringsItem(

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("length")
	val length: Int? = null
)