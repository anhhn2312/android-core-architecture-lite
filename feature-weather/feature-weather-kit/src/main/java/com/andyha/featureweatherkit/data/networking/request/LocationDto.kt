package com.andyha.featureweatherkit.data.networking.request

import com.google.gson.annotations.SerializedName


class LocationDto {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("region")
    var region: String? = null

    @SerializedName("country")
    var country: String? = null

    @SerializedName("lat")
    var lat: Double? = null

    @SerializedName("lon")
    var lng: Double? = null
}