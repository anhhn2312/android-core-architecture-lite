package com.andyha.featureweatherkit.domain.usecase.getCurrentWeather

import com.andyha.featureweatherkit.data.model.Weather
import com.andyha.featureweatherkit.data.repository.weather.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCurrentWeatherUseCaseImpl @Inject constructor(
    private val repository: WeatherRepository
) : GetCurrentWeatherUseCase {
    override fun invoke(coordinates: Pair<Double, Double>): Flow<Weather> {
        return repository.getCurrentWeather(coordinates.first, coordinates.second)
    }
}