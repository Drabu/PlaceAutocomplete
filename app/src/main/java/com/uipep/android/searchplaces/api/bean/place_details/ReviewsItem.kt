package com.uipep.android.searchplaces.api.bean.place_details

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class ReviewsItem(

	@field:SerializedName("author_name")
	val authorName: String? = null,

	@field:SerializedName("profile_photo_url")
	val profilePhotoUrl: String? = null,

	@field:SerializedName("author_url")
	val authorUrl: String? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("time")
	val time: Int? = null,

	@field:SerializedName("relative_time_description")
	val relativeTimeDescription: String? = null
)