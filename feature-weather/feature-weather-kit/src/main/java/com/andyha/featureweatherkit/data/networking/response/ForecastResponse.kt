package com.andyha.featureweatherkit.data.networking.response

import com.google.gson.annotations.SerializedName


class ForecastResponse {
    @SerializedName("forecast")
    var forecastDto: ForecastDto? = null
}