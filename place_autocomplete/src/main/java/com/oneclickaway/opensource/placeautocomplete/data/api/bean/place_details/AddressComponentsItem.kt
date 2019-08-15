package com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details

import android.arch.persistence.room.Entity
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

/** @author @buren ---> {Google response for place details}*/
@Entity(tableName = "AddressComponentsItem")
@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class AddressComponentsItem(

	@field:SerializedName("types")
	val types: List<String?>? = null,

	@field:SerializedName("short_name")
	val shortName: String? = null,

	@field:SerializedName("long_name")
	val longName: String? = null
) : Parcelable