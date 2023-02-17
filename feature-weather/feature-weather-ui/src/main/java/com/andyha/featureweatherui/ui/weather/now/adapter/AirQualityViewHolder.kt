package com.andyha.featureweatherui.ui.weather.now.adapter

import com.andyha.coreui.base.adapter.BaseViewHolder
import com.andyha.featureweatherkit.data.model.CurrentWeatherData
import com.andyha.featureweatherui.databinding.ViewHolderAirQualityBinding


class AirQualityViewHolder(private val viewBinding: ViewHolderAirQualityBinding) :
    BaseViewHolder<CurrentWeatherData>(viewBinding.root) {

    override fun bind(item: CurrentWeatherData) {
        viewBinding.weather = item.weather
    }
}