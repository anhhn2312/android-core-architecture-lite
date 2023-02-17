package com.andyha.featureweatherui.ui.weather.hourly.adapter

import com.andyha.coreui.base.adapter.multiSectionsSelection.BaseSectionItemViewHolder
import com.andyha.featureweatherkit.data.model.HourlyForecast
import com.andyha.featureweatherui.R
import com.andyha.featureweatherui.databinding.ViewHolderHourlyForecastBinding


class HourlyForecastViewHolder(private val viewBinding: ViewHolderHourlyForecastBinding) :
    BaseSectionItemViewHolder<HourlyForecast>(viewBinding) {

    override fun getCheckboxId() = null

    override fun getDividerId() = R.id.divider

    override fun bindItemInternal(item: HourlyForecast) {
        viewBinding.hourlyForecast = item
    }
}