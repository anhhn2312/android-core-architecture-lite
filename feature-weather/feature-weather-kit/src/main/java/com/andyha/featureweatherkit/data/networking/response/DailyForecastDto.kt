package com.andyha.featureweatherkit.data.networking.response

import com.google.gson.annotations.SerializedName


class DailyForecastDto {
    @SerializedName("maxtemp_c")
    var maxTemp: Double? = null

    @SerializedName("mintemp_c")
    var minTemp: Double? = null

    @SerializedName("totalprecip_mm")
    var precipitation: Double? = null

    @SerializedName("daily_chance_of_rain")
    var chanceOfRain: Int? = null

    @SerializedName("condition")
    var condition: ConditionDto? = null
}