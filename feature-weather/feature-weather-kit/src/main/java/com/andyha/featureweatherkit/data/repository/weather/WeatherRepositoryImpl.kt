package com.andyha.featureweatherkit.data.repository.weather

import com.andyha.featureweatherkit.data.datasource.weather.WeatherRemoteDataSource
import com.andyha.featureweatherkit.data.model.DailyForecast
import com.andyha.featureweatherkit.data.model.HourlyForecastWithDay
import com.andyha.featureweatherkit.data.model.Weather
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {
    override fun getCurrentWeather(lat: Double, lng: Double): Flow<Weather> {
        return weatherRemoteDataSource
            .getCurrentWeather(lat, lng)
    }

    override fun getHourlyForecast(lat: Double, lng: Double): Flow<List<HourlyForecastWithDay>> {
        return weatherRemoteDataSource
            .getHourlyForecast(lat, lng)
    }

    override fun getDailyForecast(lat: Double, lng: Double): Flow<List<DailyForecast>> {
        return weatherRemoteDataSource
            .getDailyForecast(lat, lng)
    }
}