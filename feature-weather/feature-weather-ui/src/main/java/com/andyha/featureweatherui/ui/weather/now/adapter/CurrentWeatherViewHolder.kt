package com.andyha.featureweatherui.ui.weather.now.adapter

import com.andyha.coreui.base.adapter.BaseViewHolder
import com.andyha.featureweatherkit.data.model.CurrentWeatherData
import com.andyha.featureweatherui.databinding.ViewHolderCurrentWeatherBinding


class CurrentWeatherViewHolder(private val viewBinding: ViewHolderCurrentWeatherBinding) :
    BaseViewHolder<CurrentWeatherData>(viewBinding.root) {

    override fun bind(item: CurrentWeatherData) {
        viewBinding.weather = item.weather
    }
}