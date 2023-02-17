package com.andyha.featureweatherkit.data.networking.response

import com.google.gson.annotations.SerializedName


class HourlyForecastDto {
    @SerializedName("time_epoch")
    var timeStamp: Long? = null

    @SerializedName("temp_c")
    var temperature: Double? = null

    @SerializedName("feelslike_c")
    var feelsLike: Double? = null

    @SerializedName("condition")
    var condition: ConditionDto? = null

    @SerializedName("chance_of_rain")
    var chanceOfRain: Int? = null

    @SerializedName("precip_mm")
    var totalPrecipitation: Double? = null
}