package com.andyha.featureweatherkit.data.networking.api

import com.andyha.featureweatherkit.data.networking.response.ForecastResponse
import com.andyha.featureweatherkit.data.networking.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("/$VERSION/current.json?aqi=yes")
    suspend fun getCurrentWeather(@Query("q") query: String): WeatherResponse

    @GET("/$VERSION/forecast.json?days=3")
    suspend fun getHourlyForecast(@Query("q") query: String): ForecastResponse

    @GET("/$VERSION/forecast.json?days=10")
    suspend fun getDailyForecast(@Query("q") query: String): ForecastResponse

    companion object {
        const val VERSION = "v1"
    }
}