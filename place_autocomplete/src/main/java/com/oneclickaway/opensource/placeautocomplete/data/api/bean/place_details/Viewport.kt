package com.oneclickaway.opensource.placeautocomplete.data.api.bean.place_details

import android.arch.persistence.room.Entity
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.annotation.Generated

/** @author @buren ---> {Google response for place details}*/
@Entity(tableName = "Viewport")
@Parcelize
@Generated("com.robohorse.robopojogenerator")
data class Viewport(

	@field:SerializedName("southwest")
	val southwest: Southwest? = null,

	@field:SerializedName("northeast")
	val northeast: Northeast? = null
) : Parcelable