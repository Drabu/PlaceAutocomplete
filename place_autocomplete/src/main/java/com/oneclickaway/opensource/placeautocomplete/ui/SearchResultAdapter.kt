package com.oneclickaway.opensource.placeautocomplete.ui

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.oneclickaway.opensource.placeautocomplete.R
import com.oneclickaway.opensource.placeautocomplete.api.bean.places_response.PredictionsItem
import com.oneclickaway.opensource.placeautocomplete.interfaces.PlaceClickListerner
import java.util.concurrent.TimeUnit

/** @author @buren ---> {adapter to set result views in row}*/
class SearchResultAdapter(
    var listOfCandidatesItem: List<PredictionsItem?>? = ArrayList(),
    var placeClickListerner: PlaceClickListerner
) :
    RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(

           listOfCandidatesItem = listOfCandidatesItem,
           binding =  LayoutInflater.from(p0.context).inflate(R.layout.search_result_row,   p0,  false),
            placeClickListerner = placeClickListerner

        )

    override fun getItemCount(): Int = listOfCandidatesItem!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        /*holder.binding.placeTitleTV.text = listOfCandidatesItem?.get(position)?.structuredFormatting?.mainText
        holder.binding.placeFormattedAddressTV.text =
            listOfCandidatesItem?.get(position)?.structuredFormatting?.secondaryText*/

    }


    /** @author @buren ---> {sets items in view}*/
    fun setSearchCandidates(listOfCandidatesItem: List<PredictionsItem?>?) {
        this.listOfCandidatesItem = listOfCandidatesItem
        notifyDataSetChanged()
    }


    /** @author @buren ---> {holds row vew}*/
    class ViewHolder(
        var listOfCandidatesItem: List<PredictionsItem?>?,
        var binding: View,
        var placeClickListerner: PlaceClickListerner
    ) : RecyclerView.ViewHolder(binding) {

        init {
            setItemClickListener()
        }

        @SuppressLint("CheckResult")
        private fun setItemClickListener() {
            RxView.clicks(binding)
                .throttleFirst(700, TimeUnit.MILLISECONDS)
                .subscribe {
                    placeClickListerner.onPlaceClicked(listOfCandidatesItem?.get(adapterPosition))
                }
        }
    }

}