package com.oneclickaway.opensource.placeautocomplete.data.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.oneclickaway.opensource.placeautocomplete.R
import com.oneclickaway.opensource.placeautocomplete.interfaces.SearchPlaces
import com.oneclickaway.opensource.placeautocomplete.utils.GroupStrategy

class RecentSearchesAdapter(
    val listSearchSelectedItem: List<GroupStrategy.ListItem>,
    var recentOnItemItemSelectedListener: SearchPlaces.RecentItemSelectedListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        when (viewHolder.itemViewType) {

            GroupStrategy.ListItem.TYPE_DATE -> {
                setDateView(
                    viewHolder as DateViewHolder,
                    listSearchSelectedItem[position] as GroupStrategy.DateItem
                )
            }

            GroupStrategy.ListItem.TYPE_GENERAL_ITEM -> {
                setSearchedItem(
                    viewHolder as GeneralItemViewHolder,
                    listSearchSelectedItem[position] as GroupStrategy.GeneralItem
                )
            }

        }

    }

    private fun setSearchedItem(
        generalItemViewHolder: GeneralItemViewHolder,
        generalItem: GroupStrategy.GeneralItem
    ) {
        generalItemViewHolder.recentPlaceTitleTV.text = generalItem.searchSelectedItem.mainText
        generalItemViewHolder.recentPlaceFormattedAddressTV.text =
            generalItem.searchSelectedItem.secondaryText
    }

    private fun setDateView(dateViewHolder: DateViewHolder, dateItem: GroupStrategy.DateItem) {
        dateViewHolder.groupTitleTV.text = dateItem.date.toUpperCase()
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        itemViewType: Int
    ): RecyclerView.ViewHolder {

        if (itemViewType == GroupStrategy.ListItem.TYPE_GENERAL_ITEM) {

            return GeneralItemViewHolder(
                LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.recent_search_result_row,
                    viewGroup,
                    false
                )
            )

        } else {

            return DateViewHolder(
                LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.date_item_row,
                    viewGroup,
                    false
                )
            )
        }

    }


    override fun getItemViewType(position: Int): Int {

        when (listSearchSelectedItem[position]) {

            is GroupStrategy.DateItem -> {
                return GroupStrategy.ListItem.TYPE_DATE
            }

            is GroupStrategy.GeneralItem -> {
                return GroupStrategy.ListItem.TYPE_GENERAL_ITEM
            }

        }


        return -1
    }

    override fun getItemCount(): Int {
        return listSearchSelectedItem.size
    }

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var groupTitleTV: TextView

        init {
            inflateView()
        }

        private fun inflateView() {
            groupTitleTV = itemView.findViewById(R.id.groupTitleTV)
        }
    }


    inner class GeneralItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        lateinit var recentPlaceTitleTV: TextView
        lateinit var recentPlaceFormattedAddressTV: TextView

        init {
            inflateView()

            setOnClickLister(itemView)
        }

        private fun setOnClickLister(itemView: View) {
            itemView.setOnClickListener {
                recentOnItemItemSelectedListener.onRecantsItemSelected(listSearchSelectedItem[adapterPosition])
            }
        }

        private fun inflateView() {
            recentPlaceFormattedAddressTV =
                itemView.findViewById(R.id.recentPlaceFormattedAddressTV)
            recentPlaceTitleTV = itemView.findViewById(R.id.recentPlaceTitleTV)
        }
    }

}