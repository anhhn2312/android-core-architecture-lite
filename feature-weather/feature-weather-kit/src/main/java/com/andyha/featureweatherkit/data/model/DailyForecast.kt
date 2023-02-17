package com.andyha.featureweatherkit.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = DailyForecast.TABLE_NAME)
data class DailyForecast(
    @PrimaryKey
    @ColumnInfo(name = "time_stamp")
    val timeStamp: Long,

    @ColumnInfo(name = "max_temp")
    val maxTemp: Int?,

    @ColumnInfo(name = "min_temp")
    val minTemp: Int?,

    @ColumnInfo(name = "precipitation")
    val precipitation: Double?,

    @ColumnInfo(name = "chance_of_rain")
    val chanceOfRain: Int?,

    @ColumnInfo(name = "condition")
    val condition: String?,

    @ColumnInfo(name = "icon")
    val icon: String?,
) {

    companion object {
        const val TABLE_NAME = "forecast_day"

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DailyForecast>() {
            override fun areItemsTheSame(
                oldItem: DailyForecast, newItem: DailyForecast
            ): Boolean {
                return oldItem.timeStamp == newItem.timeStamp
            }

            override fun areContentsTheSame(
                oldItem: DailyForecast, newItem: DailyForecast
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}