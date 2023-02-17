package com.andyha.featureweatherui.ui.weather.locationHistory.adapter


import com.andyha.coreui.base.adapter.multiSectionsSelection.BaseSectionHeaderViewHolder
import com.andyha.featureweatherui.databinding.ViewHolderLocationHistoryHeaderBinding


class LocationHistoryHeaderViewHolder(private val viewBinding: ViewHolderLocationHistoryHeaderBinding) :
    BaseSectionHeaderViewHolder<Int>(viewBinding) {
    override fun bind(item: Int) {
        viewBinding.title = item
    }
}