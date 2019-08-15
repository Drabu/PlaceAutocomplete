package com.oneclickaway.opensource.placeautocomplete.utils

import android.content.Context
import android.net.ConnectivityManager

object Commons {

    fun isNetworkConnected(mContext: Context): Boolean {
        val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null
    }

}