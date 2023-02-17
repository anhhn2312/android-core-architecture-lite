package com.andyha.featureweatherkit.data.datasource.weather

import com.andyha.featureweatherkit.data.model.DailyForecast
import com.andyha.featureweatherkit.data.model.HourlyForecastWithDay
import com.andyha.featureweatherkit.data.model.Weather
import com.andyha.featureweatherkit.data.networking.api.WeatherApi
import com.andyha.featureweatherkit.data.networking.transformer.toDailyForecast
import com.andyha.featureweatherkit.data.networking.transformer.toHourlyForecast
import com.andyha.featureweatherkit.data.networking.transformer.toWeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class WeatherRemoteDataSourceImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRemoteDataSource {
    override fun getCurrentWeather(lat: Double, lng: Double): Flow<Weather> =
        flow {
            weatherApi.getCurrentWeather("$lat,$lng").also {
                emit(it.toWeatherModel())
            }
        }

    override fun getHourlyForecast(lat: Double, lng: Double): Flow<List<HourlyForecastWithDay>> =
        flow {
            weatherApi.getHourlyForecast("$lat,$lng").also {
                emit(it.toHourlyForecast())
            }
        }

    override fun getDailyForecast(lat: Double, lng: Double): Flow<List<DailyForecast>> =
        flow {
            weatherApi.getDailyForecast("$lat,$lng").also {
                emit(it.toDailyForecast())
            }
        }
}