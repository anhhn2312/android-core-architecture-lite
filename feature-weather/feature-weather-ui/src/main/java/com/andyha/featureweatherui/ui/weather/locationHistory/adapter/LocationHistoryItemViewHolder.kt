package com.andyha.featureweatherui.ui.weather.locationHistory.adapter


import com.andyha.coreui.base.adapter.multiSectionsSelection.BaseSectionItemViewHolder
import com.andyha.featureweatherkit.data.model.LocationState.LocationDetected
import com.andyha.featureweatherui.databinding.ViewHolderLocationHistoryItemBinding


class LocationHistoryItemViewHolder(
    private val viewBinding: ViewHolderLocationHistoryItemBinding,
) : BaseSectionItemViewHolder<LocationDetected>(viewBinding) {

    override fun getCheckboxId() = null

    override fun getDividerId() = null

    override fun bindItemInternal(item: LocationDetected) {
        viewBinding.location = item
    }
}