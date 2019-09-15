package com.oneclickaway.opensource.placeautocomplete.utils

import android.util.Log
import com.oneclickaway.opensource.placeautocomplete.data.model.room.SearchSelectedItem
import com.oneclickaway.opensource.placeautocomplete.utils.Commons.getPrettyTime
import java.util.*
import kotlin.collections.ArrayList

/**
 *@author Burhan ud din ---> Class is used to group the list of items in groups
 */
class GroupStrategy {

    /**
     *@author Burhan ud din ---> class used to keep time
     */
    class TimeContainer(var nowTime: String, var timeInMilliseconds: Long) {
        override fun equals(other: Any?): Boolean {
            return if (other is TimeContainer) {
                this.nowTime == other.nowTime

            } else {
                false
            }
        }

        override fun hashCode(): Int {
            Log.d("getHasMap", "hashCode: " + nowTime.hashCode())
            return nowTime.hashCode()
        }

        override fun toString(): String {
            return "TimeContainer(nowTime='$nowTime', timeInMilliseconds=$timeInMilliseconds)"
        }


    }

    /**
     *@author Burhan ud din ---> Liste item in recent search
     */
    abstract class ListItem {

        companion object {
            const val TYPE_DATE = 101
            const val TYPE_GENERAL_ITEM = 102
        }

        /**
         *@author Burhan ud din ---> Type of item
         */
        abstract fun getType(): Int

    }

    /**
     *@author Burhan ud din ---> date item in list item
     */
    class DateItem : ListItem() {

        lateinit var date: String

        override fun getType() = TYPE_DATE
    }

    class GeneralItem : ListItem() {
        lateinit var searchSelectedItem: SearchSelectedItem

        override fun getType(): Int = TYPE_GENERAL_ITEM
    }

    /**
     *@author Burhan ud din ---> Compares and sorts the list into group according to time
     */
    class GroupComparator : Comparator<TimeContainer> {
        override fun compare(p0: TimeContainer?, p1: TimeContainer?): Int {
            return if (p0!!.timeInMilliseconds < p1!!.timeInMilliseconds) {
                1
            } else {
                -1
            }

        }

    }

    /**
     *@author Burhan ud din ---> method used to group the data by time
     */
    fun groupDataByTime(myOption: List<SearchSelectedItem>): ArrayList<ListItem> {

        val consolidatedList = ArrayList<ListItem>()

        val hashedMap = getHasMap(myOption)

        for ((key, value) in hashedMap) {

            val dateItem = DateItem()
            dateItem.date = key.nowTime
            consolidatedList.add(dateItem)

            for (itemList in value) {
                val generalItem = GeneralItem()
                generalItem.searchSelectedItem = itemList
                consolidatedList.add(generalItem)
            }


        }

        return consolidatedList

    }

    private fun getHasMap(listOfOptions: List<SearchSelectedItem>): TreeMap<TimeContainer, ArrayList<SearchSelectedItem>> {

        val hashedMap = HashMap<TimeContainer, ArrayList<SearchSelectedItem>>()

        for (searchedItem in listOfOptions) {

            val dateContainer = TimeContainer(
                getPrettyTime(searchedItem.searchCurrentMilliseconds),
                searchedItem.searchCurrentMilliseconds
            )
            if (hashedMap.containsKey(dateContainer)) {
                hashedMap[dateContainer]?.add(searchedItem)
            } else {
                val item = ArrayList<SearchSelectedItem>()
                item.add(searchedItem)
                hashedMap[dateContainer] = item
            }

        }

        val blankTree = TreeMap<TimeContainer, ArrayList<SearchSelectedItem>>(GroupComparator())
        blankTree.putAll(hashedMap)

        return blankTree
    }

}

