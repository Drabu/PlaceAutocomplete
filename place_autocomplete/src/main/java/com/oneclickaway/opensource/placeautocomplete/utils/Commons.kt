package com.oneclickaway.opensource.placeautocomplete.utils

import android.content.Context
import android.net.ConnectivityManager
import java.util.*

/**
 *@author Burhan ud din ---> Common utilities
 */
object Commons {

    /**
     *@author Burhan ud din ---> tell you if the user is connected to the network
     */
    fun isNetworkConnected(mContext: Context): Boolean {
        val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null
    }

    /**
     *@author Burhan ud din ---> Used to get pretty time from milliseconds
     */
    fun getPrettyTime(milliSeconds: Long): String {
        val default = 0

        val minutesToYesterday = 1440
        val minutesToWeek = 10080
        val minutesToPost = ((System.currentTimeMillis() - milliSeconds) / 1000F) / 60F

        val currentDateInstance = Calendar.getInstance()
        currentDateInstance.set(
            currentDateInstance.get(Calendar.YEAR),
            currentDateInstance.get(Calendar.DAY_OF_MONTH),
            currentDateInstance.get(Calendar.DATE)
        )


        val currentTimeCalender = Calendar.getInstance()
        currentTimeCalender.set(
            currentTimeCalender.get(Calendar.YEAR),
            currentTimeCalender.get(Calendar.DAY_OF_MONTH),
            currentTimeCalender.get(Calendar.DATE),
            default, default, default
        )

        val minutesElapsedForToday =
            ((currentDateInstance.timeInMillis - currentTimeCalender.timeInMillis) / 1000F) / 60F

        return when {
            minutesToPost < minutesElapsedForToday -> "Today"
            (minutesToPost - minutesElapsedForToday) < minutesToYesterday -> "Yesterday"
            (minutesToPost - minutesElapsedForToday) < minutesToWeek -> "Earlier this week"
            else -> "Previous Searches"
        }

    }

}