package com.andyha.featureweatherkit.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = HourlyForecast.TABLE_NAME)
data class HourlyForecast(
    @PrimaryKey
    @ColumnInfo(name = "timestamp")
    val timeStamp: Long,

    @ColumnInfo(name = "temperature")
    val temperature: Int?,

    @ColumnInfo(name = "feels_like")
    val feelsLike: Int?,

    @ColumnInfo(name = "condition")
    val condition: String?,

    @ColumnInfo(name = "icon")
    val icon: String?,

    @ColumnInfo(name = "chance_of_rain")
    val chanceOfRain: Int?,

    @ColumnInfo(name = "precipitation")
    val precipitation: Double?
) {
    companion object {
        const val TABLE_NAME = "hourly_forecast"

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HourlyForecast>() {
            override fun areItemsTheSame(
                oldItem: HourlyForecast,
                newItem: HourlyForecast
            ): Boolean = oldItem.timeStamp == newItem.timeStamp

            override fun areContentsTheSame(
                oldItem: HourlyForecast,
                newItem: HourlyForecast
            ): Boolean = oldItem == newItem
        }
    }
}