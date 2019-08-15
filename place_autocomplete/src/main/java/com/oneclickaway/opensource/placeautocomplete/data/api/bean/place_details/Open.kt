package com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details

import android.arch.persistence.room.Entity
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

/** @author @buren ---> {Google response for place details}*/
@Entity(tableName = "Open")
@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class Open(

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("day")
	val day: Int? = null
) : Parcelable