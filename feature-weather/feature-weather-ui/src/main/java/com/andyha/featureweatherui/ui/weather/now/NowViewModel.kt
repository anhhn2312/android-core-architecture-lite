package com.andyha.featureweatherui.ui.weather.now

import com.andyha.featureweatherkit.data.model.CurrentWeatherData
import com.andyha.featureweatherkit.data.model.CurrentWeatherData.*
import com.andyha.featureweatherkit.domain.usecase.getCurrentWeather.GetCurrentWeatherUseCase
import com.andyha.featureweatherui.R
import com.andyha.featureweatherui.ui.base.BaseWeatherViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class NowViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
) : BaseWeatherViewModel() {

    private val _currentWeather = MutableSharedFlow<List<CurrentWeatherData>>()
    val currentWeather = _currentWeather.asSharedFlow()

    fun getCurrentWeather() {
        currentLocation?.let { currentLocation ->
            getCurrentWeatherUseCase(Pair(currentLocation.lat, currentLocation.lng))
                .map {
                    mutableListOf(
                        Now.apply { weather = it },
                        Humid.apply {
                            title = R.string.humidity
                            image = R.drawable.ic_humidity
                            weather = it
                        },
                        Wind.apply {
                            title = R.string.wind
                            image = R.drawable.ic_wind
                            weather = it
                        },
                        Pressure.apply {
                            title = R.string.pressure
                            image = R.drawable.ic_pressure
                            weather = it
                        },
                        Cloud.apply {
                            title = R.string.cloud
                            image = R.drawable.ic_cloud
                            weather = it
                        },
                        Visibility.apply {
                            title = R.string.visibility
                            image = R.drawable.ic_visibility
                            weather = it
                        },
                        Uv.apply {
                            title = R.string.uv
                            image = R.drawable.ic_uv
                            weather = it
                        },
                        AirQuality.apply {
                            weather = it
                        }
                    )
                }
                .justLaunch { _currentWeather.emit(it) }
        } ?: requestLocationUpdate()
    }

    override fun onLocationDetected() {
        getCurrentWeather()
    }
}