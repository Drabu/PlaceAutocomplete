package com.oneclickaway.opensource.placeautocomplete.api.bean.places_response

import com.google.gson.annotations.SerializedName

/** @author @buren ---> {Google response for predicted places}*/
data class PredictionsItem(

    @field:SerializedName("reference")
    val reference: String? = null,

    @field:SerializedName("types")
    val types: List<String?>? = null,

    @field:SerializedName("matched_substrings")
    val matchedSubstrings: List<MatchedSubstringsItem?>? = null,

    @field:SerializedName("terms")
    val terms: List<TermsItem?>? = null,

    @field:SerializedName("structured_formatting")
    val structuredFormatting: StructuredFormatting? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("place_id")
    val placeId: String? = null
)