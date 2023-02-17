package com.andyha.featureweatherkit.data.networking.response

import com.google.gson.annotations.SerializedName


class CurrentWeatherDto {
    @SerializedName("last_updated_epoch")
    var timeStamp: Long? = null

    @SerializedName("temp_c")
    var temperature: Double? = null //degree C

    @SerializedName("feelslike_c")
    var feelLike: Double? = null //degree C

    @SerializedName("is_day")
    var isDay: Int? = null //1=day 0=night

    @SerializedName("condition")
    var condition: ConditionDto? = null

    @SerializedName("wind_kph")
    var windSpeed: Double? = null //km per hour

    @SerializedName("wind_degree")
    var windDegree: Double? = null

    @SerializedName("wind_dir")
    var windDirection: String? = null

    @SerializedName("pressure_mb")
    var pressure: Double? = null //in millibars

    @SerializedName("humidity")
    var humidity: Int? = null //percentage

    @SerializedName("cloud")
    var cloudCover: Int? = null //percentage

    @SerializedName("vis_km")
    var visibility: Double? = null //in km

    @SerializedName("uv")
    var uv: Double? = null //uv index

    @SerializedName("air_quality")
    var airQuality: AirQualityDto? = null
}