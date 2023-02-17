package com.andyha.featureweatherkit.data.model


data class HourlyForecastWithDay(
    val dayTimeStamp: Long,
    val forecast: List<HourlyForecast>
)