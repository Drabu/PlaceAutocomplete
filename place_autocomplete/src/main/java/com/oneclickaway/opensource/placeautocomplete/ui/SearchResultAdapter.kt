package com.oneclickaway.opensource.placeautocomplete.ui

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.oneclickaway.opensource.placeautocomplete.api.bean.places_response.PredictionsItem
import com.oneclickaway.opensource.placeautocomplete.databinding.SearchResultRowBinding
import com.oneclickaway.opensource.placeautocomplete.interfaces.PlaceClickListerner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/** @author @buren ---> {adapter to set result views in row}*/
class SearchResultAdapter(
    var listOfCandidatesItem: List<PredictionsItem?>? = ArrayList(),
    var placeClickListerner: PlaceClickListerner
) :
    RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(
            binding = SearchResultRowBinding.inflate(LayoutInflater.from(p0.context), p0, false),
            listOfCandidatesItem = listOfCandidatesItem,
            placeClickListerner = placeClickListerner
        )

    override fun getItemCount(): Int = listOfCandidatesItem!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.placeTitleTV.text = listOfCandidatesItem?.get(position)?.structuredFormatting?.mainText
        holder.binding.placeFormattedAddressTV.text =
            listOfCandidatesItem?.get(position)?.structuredFormatting?.secondaryText

    }


    /** @author @buren ---> {sets items in view}*/
    fun setSearchCandidates(listOfCandidatesItem: List<PredictionsItem?>?) {
        this.listOfCandidatesItem = listOfCandidatesItem
        notifyDataSetChanged()
    }


    /** @author @buren ---> {holds row vew}*/
    class ViewHolder(
        var listOfCandidatesItem: List<PredictionsItem?>?,
        var binding: SearchResultRowBinding,
        var placeClickListerner: PlaceClickListerner
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            setItemClickListener()
        }

        @SuppressLint("CheckResult")
        private fun setItemClickListener() {
            RxView.clicks(binding.root)
                .throttleFirst(700, TimeUnit.MILLISECONDS)
                .subscribe {
                    placeClickListerner.onPlaceClicked(listOfCandidatesItem?.get(adapterPosition))
                }
        }
    }

}