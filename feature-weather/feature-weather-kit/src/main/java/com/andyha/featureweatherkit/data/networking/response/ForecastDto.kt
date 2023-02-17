package com.andyha.featureweatherkit.data.networking.response

import com.google.gson.annotations.SerializedName


class ForecastDto {
    @SerializedName("forecastday")
    var forecastDays: List<DailyForecastWrapperDto> = listOf()
}