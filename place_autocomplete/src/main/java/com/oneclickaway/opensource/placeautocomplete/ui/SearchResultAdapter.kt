package com.oneclickaway.opensource.placeautocomplete.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oneclickaway.opensource.placeautocomplete.api.bean.places_response.PredictionsItem
import com.oneclickaway.opensource.placeautocomplete.databinding.SearchResultRowBinding
import com.oneclickaway.opensource.placeautocomplete.interfaces.PlaceClickListerner

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
        holder.binding.placeFormattedAddressTV.text = listOfCandidatesItem?.get(position)?.structuredFormatting?.secondaryText

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
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View) {
            placeClickListerner.onPlaceClicked(listOfCandidatesItem?.get(adapterPosition))
        }

    }

}