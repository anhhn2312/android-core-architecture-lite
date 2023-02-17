package com.andyha.featureweatherui.ui.base

import androidx.lifecycle.viewModelScope
import com.andyha.coreui.base.viewModel.BaseViewModel
import com.andyha.featureweatherkit.data.model.LocationState
import com.andyha.featureweatherkit.data.model.LocationState.LocationDetected
import com.andyha.featureweatherkit.domain.usecase.getCurrentLocationState.GetLocationStateUseCase
import com.andyha.featureweatherkit.domain.usecase.requestLocationUpdate.RequestLocationUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class BaseWeatherViewModel @Inject constructor(
): BaseViewModel() {
    @Inject
    lateinit var getLocationStateUseCase: GetLocationStateUseCase

    @Inject
    lateinit var requestLocationUpdateUseCase: RequestLocationUpdateUseCase

    private val _currentLocationState = MutableStateFlow<LocationState>(LocationState.Undefined)
    val currentLocationState = _currentLocationState.asStateFlow()

    internal var currentLocation: LocationDetected? = null

    fun getCurrentLocationState(){
        viewModelScope.launch {
            getLocationStateUseCase()
                .catch { Timber.e(it) }
                .collect {
                    _currentLocationState.emit(it)
                    if (it is LocationDetected) {
                        currentLocation = it
                        onLocationDetected()
                    }
                }
        }
    }

    fun requestLocationUpdate(){
        requestLocationUpdateUseCase()
    }

    open fun onLocationDetected(){}
}