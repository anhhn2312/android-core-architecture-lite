package com.andyha.featureweatherkit.domain.usecase.getDailyForecast

import com.andyha.coredata.base.BaseUseCase
import com.andyha.featureweatherkit.data.model.DailyForecast
import kotlinx.coroutines.flow.Flow


interface GetDailyForecastUseCase : BaseUseCase<Pair<Double, Double>, Flow<List<DailyForecast>>>