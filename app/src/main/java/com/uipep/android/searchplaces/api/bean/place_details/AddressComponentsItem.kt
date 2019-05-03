package com.uipep.android.searchplaces.api.bean.place_details

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class AddressComponentsItem(

	@field:SerializedName("types")
	val types: List<String?>? = null,

	@field:SerializedName("short_name")
	val shortName: String? = null,

	@field:SerializedName("long_name")
	val longName: String? = null
)