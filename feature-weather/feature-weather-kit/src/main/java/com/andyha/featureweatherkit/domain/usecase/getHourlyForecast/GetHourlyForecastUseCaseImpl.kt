package com.andyha.featureweatherkit.domain.usecase.getHourlyForecast

import com.andyha.featureweatherkit.data.model.HourlyForecastWithDay
import com.andyha.featureweatherkit.data.repository.weather.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetHourlyForecastUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
): GetHourlyForecastUseCase {
    override fun invoke(params: Pair<Double, Double>): Flow<List<HourlyForecastWithDay>> {
        return weatherRepository.getHourlyForecast(params.first, params.second)
    }
}