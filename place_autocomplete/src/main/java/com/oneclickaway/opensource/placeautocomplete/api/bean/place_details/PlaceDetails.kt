package com.oneclickaway.opensource.placeautocomplete.api.bean.place_details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

/** @author @buren ---> {Google response for place details}*/
@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class PlaceDetails (

	@field:SerializedName("utc_offset")
	val utcOffset: Int? = null,

	@field:SerializedName("formatted_address")
	val formattedAddress: String? = null,

	@field:SerializedName("types")
	val types: List<String?>? = null,

	@field:SerializedName("website")
	val website: String? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("rating")
	val rating: Double? = null,

	@field:SerializedName("address_components")
	val addressComponents: List<AddressComponentsItem?>? = null,

	@field:SerializedName("photos")
	val photos: List<PhotosItem?>? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("reference")
	val reference: String? = null,

	@field:SerializedName("user_ratings_total")
	val userRatingsTotal: Int? = null,

	@field:SerializedName("reviews")
	val reviews: List<ReviewsItem?>? = null,

	@field:SerializedName("scope")
	val scope: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("opening_hours")
	val openingHours: OpeningHours? = null,

	@field:SerializedName("geometry")
	val geometry: Geometry? = null,

	@field:SerializedName("vicinity")
	val vicinity: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("adr_address")
	val adrAddress: String? = null,

	@field:SerializedName("plus_code")
	val plusCode: PlusCode? = null,

	@field:SerializedName("formatted_phone_number")
	val formattedPhoneNumber: String? = null,

	@field:SerializedName("international_phone_number")
	val internationalPhoneNumber: String? = null,

	@field:SerializedName("place_id")
	val placeId: String? = null
) : Parcelable