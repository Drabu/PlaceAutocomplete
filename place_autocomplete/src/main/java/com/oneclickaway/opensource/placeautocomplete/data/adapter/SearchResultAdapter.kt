package com.oneclickaway.opensource.placeautocomplete.data.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.oneclickaway.opensource.placeautocomplete.R
import com.oneclickaway.opensource.placeautocomplete.data.api.bean.places_response.PredictionsItem
import com.oneclickaway.opensource.placeautocomplete.interfaces.SearchPlaces
import java.util.concurrent.TimeUnit

/** @author @buren ---> {adapter to set result views in row}*/
class SearchResultAdapter(
    var listOfCandidatesItem: List<PredictionsItem?>? = ArrayList(),
    var placeItemSelectedListener: SearchPlaces.PlaceItemSelectedListener
) :
    RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(
            binding = LayoutInflater.from(p0.context).inflate(R.layout.search_result_row, p0, false)
        )

    override fun getItemCount(): Int = listOfCandidatesItem!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.placeTitleTV.text = listOfCandidatesItem?.get(position)?.structuredFormatting?.mainText
        holder.placeFormattedAddressTV.text =
            listOfCandidatesItem?.get(position)?.structuredFormatting?.secondaryText

    }


    /** @author @buren ---> {sets items in view}*/
    fun setSearchCandidates(listOfCandidatesItem: List<PredictionsItem?>?) {
        this.listOfCandidatesItem = listOfCandidatesItem
        notifyDataSetChanged()
    }


    /** @author @buren ---> {holds row vew}*/
    inner class ViewHolder(
        var binding: View
    ) : RecyclerView.ViewHolder(binding) {

        lateinit var placeTitleTV: TextView
        lateinit var placeFormattedAddressTV: TextView

        init {

            inflateViews()

            setItemClickListener()
        }

        private fun inflateViews() {

            placeTitleTV = itemView.findViewById(R.id.placeTitleTV)
            placeFormattedAddressTV = itemView.findViewById(R.id.placeFormattedAddressTV)


        }

        @SuppressLint("CheckResult")
        private fun setItemClickListener() {
            RxView.clicks(binding)
                .throttleFirst(700, TimeUnit.MILLISECONDS)
                .subscribe {
                    placeItemSelectedListener.onPlaceItemSelected(
                        listOfCandidatesItem?.get(
                            adapterPosition
                        )
                    )
                }
        }
    }

}