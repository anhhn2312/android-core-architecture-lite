package com.andyha.featureweatherkit.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = Weather.TABLE_NAME)
data class Weather(
    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: String, //in format: yyyy/MM/dd

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "region")
    val region: String,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long, //unix timestamp

    @ColumnInfo(name = "temperature")
    val temperature: Int?, //degree C

    @ColumnInfo(name = "feel_like")
    val feelLike: Int?, //degree C

    @ColumnInfo(name = "is_day")
    val isDay: Boolean?, //true=day, false=night

    @ColumnInfo(name = "icon")
    val icon: String?,

    @ColumnInfo(name = "condition")
    val condition: String?, //sunny, rainy, cloudy

    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double?, //km per hour

    @ColumnInfo(name = "wind_degree")
    val windDegree: Double?,

    @ColumnInfo(name = "wind_direction")
    val windDirection: String?,

    @ColumnInfo(name = "pressure")
    val pressure: Double?, //in millibars

    @ColumnInfo(name = "humidity")
    val humidity: Int?, //percentage

    @ColumnInfo(name = "cloud_cover")
    val cloudCover: Int?, //percentage

    @ColumnInfo(name = "visibility")
    val visibility: Double?, //in km

    @ColumnInfo(name = "uv")
    val uv: Double?, //uv index

    @ColumnInfo(name = "air_quality")
    val airQuality: Int?
) {
    companion object {
        const val TABLE_NAME = "weather"
    }
}