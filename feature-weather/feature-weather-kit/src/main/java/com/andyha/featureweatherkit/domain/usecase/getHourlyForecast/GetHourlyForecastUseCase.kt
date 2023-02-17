package com.andyha.featureweatherkit.domain.usecase.getHourlyForecast

import com.andyha.coredata.base.BaseUseCase
import com.andyha.featureweatherkit.data.model.HourlyForecastWithDay
import kotlinx.coroutines.flow.Flow


interface GetHourlyForecastUseCase: BaseUseCase<Pair<Double, Double>, Flow<List<HourlyForecastWithDay>>>