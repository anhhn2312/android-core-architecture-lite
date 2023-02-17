package com.andyha.featureweatherkit.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.andyha.coredata.base.BaseDao
import com.andyha.featureweatherkit.data.model.Weather


@Dao
abstract class WeatherDao: BaseDao<Weather>(){
    @Query("SELECT * FROM ${Weather.TABLE_NAME} ORDER BY date DESC LIMIT 1")
    abstract fun getCurrentWeather(): List<Weather>
}