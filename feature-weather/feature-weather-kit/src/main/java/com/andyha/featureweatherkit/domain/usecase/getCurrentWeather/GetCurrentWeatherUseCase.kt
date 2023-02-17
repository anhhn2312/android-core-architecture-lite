package com.andyha.featureweatherkit.domain.usecase.getCurrentWeather

import com.andyha.coredata.base.BaseUseCase
import com.andyha.featureweatherkit.data.model.Weather
import kotlinx.coroutines.flow.Flow


interface GetCurrentWeatherUseCase : BaseUseCase<Pair<Double, Double>, Flow<Weather>>