package com.andyha.featureweatherkit.data.networking.response

import com.andyha.featureweatherkit.data.networking.request.LocationDto
import com.google.gson.annotations.SerializedName


class WeatherResponse {
    @SerializedName("location")
    var location: LocationDto? = null

    @SerializedName("current")
    var currentWeather: CurrentWeatherDto? = null
}