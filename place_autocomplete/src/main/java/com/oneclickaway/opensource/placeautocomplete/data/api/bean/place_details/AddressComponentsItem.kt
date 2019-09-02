package com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details

import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

/** @author @buren ---> {Google response for place details}*/
@Parcelize
@Generated("com.robohorse.robopojogenerator")
class AddressComponentsItem(

    @field:SerializedName("types")
    var types: List<String?>? = null,

    @field:SerializedName("short_name")
    var shortName: String? = null,

	@field:SerializedName("long_name")
    @PrimaryKey var longName: String

) : Parcelable