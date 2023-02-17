package com.andyha.featureweatherui.ui.weather.locationHistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.andyha.coreui.base.adapter.multiSectionsSelection.BaseMultiSectionListAdapter
import com.andyha.featureweatherkit.data.model.LocationState.LocationDetected
import com.andyha.featureweatherui.databinding.ViewHolderLocationHistoryHeaderBinding
import com.andyha.featureweatherui.databinding.ViewHolderLocationHistoryItemBinding

class LocationHistoryAdapter(private val onItemClicked: (LocationDetected) -> Unit) :
    BaseMultiSectionListAdapter<Int, LocationDetected>(
        LocationDetected.DIFF_CALLBACK,
        onItemClicked = onItemClicked
    ) {

    override fun createHeaderViewHolder(parent: ViewGroup) = LocationHistoryHeaderViewHolder(
        ViewHolderLocationHistoryHeaderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun createItemViewHolder(parent: ViewGroup) = LocationHistoryItemViewHolder(
        ViewHolderLocationHistoryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun isItemClickable() = true
}