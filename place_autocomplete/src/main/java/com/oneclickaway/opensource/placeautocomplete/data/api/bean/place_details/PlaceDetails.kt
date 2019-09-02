package com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

/** @author @buren ---> {Google response for place details}*/
@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class PlaceDetails (

    @field:SerializedName("utc_offset")
    var utcOffset: Int? = null,

    @field:SerializedName("formatted_address")
    var formattedAddress: String? = null,

    @field:SerializedName("types")
    var types: List<String?>? = null,

    @field:SerializedName("website")
    var website: String? = null,

    @field:SerializedName("icon")
    var icon: String? = null,

    @field:SerializedName("rating")
    var rating: Double? = null,

    @field:SerializedName("address_components")
    var addressComponents: List<AddressComponentsItem?>? = null,

    @field:SerializedName("photos")
    var photos: List<PhotosItem?>? = null,

    @field:SerializedName("url")
    var url: String? = null,

    @field:SerializedName("reference")
    var reference: String? = null,

    @field:SerializedName("user_ratings_total")
    var userRatingsTotal: Int? = null,

    @field:SerializedName("reviews")
    var reviews: List<ReviewsItem?>? = null,

    @field:SerializedName("scope")
    var scope: String? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("opening_hours")
    var openingHours: OpeningHours? = null,

    @field:SerializedName("geometry")
    var geometry: Geometry? = null,

    @field:SerializedName("vicinity")
    var vicinity: String? = null,

    @field:SerializedName("id")
    var id: String? = null,

    @field:SerializedName("adr_address")
    var adrAddress: String? = null,

    @field:SerializedName("plus_code")
    var plusCode: PlusCode? = null,

    @field:SerializedName("formatted_phone_number")
    var formattedPhoneNumber: String? = null,

    @field:SerializedName("international_phone_number")
    var internationalPhoneNumber: String? = null,

    @field:SerializedName("place_id")
    var placeId: String? = null
) : Parcelable