package com.andyha.featureweatherkit.data.networking.response

import com.google.gson.annotations.SerializedName


class WeatherConditionDto {
    @SerializedName("text")
    var text: String? = null

    @SerializedName("icon")
    var icon: String? = null

    @SerializedName("code")
    var code: String? = null
}