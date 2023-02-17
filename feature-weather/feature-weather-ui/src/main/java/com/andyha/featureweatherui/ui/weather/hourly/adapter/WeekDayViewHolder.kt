package com.andyha.featureweatherui.ui.weather.hourly.adapter

import com.andyha.coreui.base.adapter.multiSectionsSelection.BaseSectionHeaderViewHolder
import com.andyha.featureweatherui.databinding.ViewHolderWeekDayBinding


class WeekDayViewHolder(private val viewBinding: ViewHolderWeekDayBinding) :
    BaseSectionHeaderViewHolder<Long>(viewBinding) {
    override fun bind(item: Long) {
        viewBinding.weekDay = item
    }
}