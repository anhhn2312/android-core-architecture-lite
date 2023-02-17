package com.andyha.featureweatherkit.data.networking.transformer

import com.andyha.coreutils.time.TimeFormatter
import com.andyha.featureweatherkit.data.model.DailyForecast
import com.andyha.featureweatherkit.data.model.HourlyForecast
import com.andyha.featureweatherkit.data.model.HourlyForecastWithDay
import com.andyha.featureweatherkit.data.model.Weather
import com.andyha.featureweatherkit.data.networking.response.ForecastResponse
import com.andyha.featureweatherkit.data.networking.response.HourlyForecastDto
import com.andyha.featureweatherkit.data.networking.response.WeatherResponse
import kotlin.math.roundToInt


fun WeatherResponse.toWeatherModel() = Weather(
    date = TimeFormatter.timestampToDayMonthYear(null, (currentWeather?.timeStamp ?: 0) * 1000) ?: "",
    address = location?.name ?: "",
    region = location?.region ?: "",
    country = location?.country ?: "",
    lastUpdated = (currentWeather?.timeStamp ?: 0) * 1000,
    temperature = currentWeather?.temperature?.roundToInt(),
    feelLike = currentWeather?.feelLike?.roundToInt(),
    isDay = currentWeather?.isDay == 1,
    icon = "https:" + currentWeather?.condition?.icon,
    condition = currentWeather?.condition?.text,
    windSpeed = currentWeather?.windSpeed,
    windDegree = currentWeather?.windDegree,
    windDirection = currentWeather?.windDirection,
    pressure = currentWeather?.pressure,
    humidity = currentWeather?.humidity,
    cloudCover = currentWeather?.cloudCover,
    visibility = currentWeather?.visibility,
    uv = currentWeather?.uv,
    airQuality = currentWeather?.airQuality?.quality
)

fun ForecastResponse.toDailyForecast() = (forecastDto?.forecastDays?.map {
    DailyForecast(
        timeStamp = (it.timeStamp ?: 0) * 1000,
        maxTemp = it.day?.maxTemp?.roundToInt(),
        minTemp = it.day?.minTemp?.roundToInt(),
        precipitation = it.day?.precipitation,
        chanceOfRain = it.day?.chanceOfRain,
        condition = it.day?.condition?.text,
        icon = it.day?.condition?.icon?.let { "https:$it" }
    )
} ?: listOf()).let {
    mutableListOf<DailyForecast>().apply {
        addAll(it)
        addAll(it)
        addAll(it)
        addAll(it)
    }
}

fun ForecastResponse.toHourlyForecast(): List<HourlyForecastWithDay> {
    val ret = mutableListOf<HourlyForecastWithDay>()
    forecastDto?.forecastDays?.let {
        for (forecastDay in it) {
            forecastDay.timeStamp?.let {
                val hours = forecastDay.hours.filter {
                    it.timeStamp?.let { it * 1000 > System.currentTimeMillis() } ?: false
                }.map { it.toHour() }
                if (hours.isNotEmpty()) {
                    ret.add(HourlyForecastWithDay(it * 1000, hours))
                }
            }
        }
    }
    return ret
}

fun HourlyForecastDto.toHour(): HourlyForecast = HourlyForecast(
    timeStamp = (timeStamp ?: 0) * 1000,
    temperature = temperature?.roundToInt(),
    feelsLike = feelsLike?.roundToInt(),
    condition = condition?.text,
    icon = condition?.icon?.let { "https:$it" },
    chanceOfRain = chanceOfRain,
    precipitation = totalPrecipitation,
)