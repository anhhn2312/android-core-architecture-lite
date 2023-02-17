package com.andyha.featureweatherui.ui.weather.daily

import com.andyha.featureweatherkit.data.model.DailyForecast
import com.andyha.featureweatherkit.domain.usecase.getDailyForecast.GetDailyForecastUseCase
import com.andyha.featureweatherui.ui.base.BaseWeatherViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class DailyViewModel @Inject constructor(
    private val getDailyForecastUseCase: GetDailyForecastUseCase
) : BaseWeatherViewModel() {

    private val _dailyForecast = MutableStateFlow<List<DailyForecast>>(listOf())
    val dailyForecast = _dailyForecast.asStateFlow()

    fun getDailyForecast() {
        currentLocation?.let { currentLocation ->
            getDailyForecastUseCase
                .invoke(Pair(currentLocation.lat, currentLocation.lng))
                .justLaunch { _dailyForecast.emit(it) }
        } ?: requestLocationUpdate()
    }

    override fun onLocationDetected() {
        getDailyForecast()
    }
}