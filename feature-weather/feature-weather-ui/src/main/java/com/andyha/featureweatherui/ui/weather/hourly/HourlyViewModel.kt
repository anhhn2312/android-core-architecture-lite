package com.andyha.featureweatherui.ui.weather.hourly

import com.andyha.coreui.base.adapter.multiSectionsSelection.Section
import com.andyha.featureweatherkit.data.model.HourlyForecast
import com.andyha.featureweatherkit.domain.usecase.getHourlyForecast.GetHourlyForecastUseCase
import com.andyha.featureweatherui.ui.base.BaseWeatherViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class HourlyViewModel @Inject constructor(
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase,
) : BaseWeatherViewModel() {

    private val _hourlyForecast = MutableStateFlow<List<Section<Long, HourlyForecast>>>(listOf())
    val hourlyForecast = _hourlyForecast.asStateFlow()

    fun getHourlyForecast() {
        currentLocation?.let { currentLocation ->
            getHourlyForecastUseCase.invoke(Pair(currentLocation.lat, currentLocation.lng))
                .map { it.map { Section(it.dayTimeStamp, it.forecast) } }
                .justLaunch { _hourlyForecast.emit(it) }
        } ?: requestLocationUpdate()
    }

    override fun onLocationDetected() {
        getHourlyForecast()
    }
}