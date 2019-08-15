package com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details

import android.arch.persistence.room.Entity
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

/** @author @buren ---> {Google response for place details}*/
@Entity(tableName = "Southwest")
@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class Southwest(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
) : Parcelable