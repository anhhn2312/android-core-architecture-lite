package com.andyha.featureweatherkit.data.datasource.weather

import com.andyha.featureweatherkit.data.model.DailyForecast
import com.andyha.featureweatherkit.data.model.HourlyForecastWithDay
import com.andyha.featureweatherkit.data.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRemoteDataSource {
    fun getCurrentWeather(lat: Double, lng: Double): Flow<Weather>
    fun getHourlyForecast(lat: Double, lng: Double): Flow<List<HourlyForecastWithDay>>
    fun getDailyForecast(lat: Double, lng: Double): Flow<List<DailyForecast>>
}