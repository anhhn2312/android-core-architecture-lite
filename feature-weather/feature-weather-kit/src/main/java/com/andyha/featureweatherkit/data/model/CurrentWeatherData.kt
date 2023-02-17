package com.andyha.featureweatherkit.data.model

import androidx.recyclerview.widget.DiffUtil


enum class CurrentWeatherData(
    var title: Int? = null,
    var image: Int? = null,
    var weather: Weather? = null
) {
    Now,
    Humid,
    Wind,
    Pressure,
    Cloud,
    Visibility,
    Uv,
    AirQuality;

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CurrentWeatherData>() {
            override fun areItemsTheSame(
                oldItem: CurrentWeatherData,
                newItem: CurrentWeatherData
            ): Boolean = oldItem.weather == newItem.weather

            override fun areContentsTheSame(
                oldItem: CurrentWeatherData,
                newItem: CurrentWeatherData
            ): Boolean = oldItem.weather == newItem.weather
        }
    }
}