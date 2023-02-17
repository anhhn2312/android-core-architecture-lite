package com.andyha.featureweatherui.ui.activity

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.andyha.coredata.manager.ConfigurationManager
import com.andyha.coredata.storage.preference.AppSharedPreference
import com.andyha.coredata.storage.preference.currentUsername
import com.andyha.coreextension.getValueOrNull
import com.andyha.coreextension.localehelper.LocaleManager
import com.andyha.coreui.base.viewModel.BaseViewModel
import com.andyha.featureweatherkit.data.model.LocationState
import com.andyha.featureweatherkit.data.model.LocationState.LocationDetected
import com.andyha.featureweatherkit.domain.usecase.getCurrentLocationState.GetLocationStateUseCase
import com.andyha.featureweatherkit.domain.usecase.requestLocationUpdate.RequestLocationUpdateUseCase
import com.andyha.featureweatherkit.domain.usecase.setSelectedLocationUseCase.SetSelectedLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val requestLocationUpdateUseCase: RequestLocationUpdateUseCase,
    private val getLocationStateUseCase: GetLocationStateUseCase,
    private val setSelectedLocationUseCase: SetSelectedLocationUseCase,
    private val localeManager: LocaleManager,
    private val configurationManager: ConfigurationManager,
    private val prefs: AppSharedPreference
) : BaseViewModel() {

    private val _currentLocationState = MutableStateFlow<LocationState>(LocationState.Undefined)
    val currentLocationState = _currentLocationState.asStateFlow()

    private val _logoutSuccessful = MutableStateFlow(false)
    val logoutSucessful = _logoutSuccessful.asStateFlow()

    init {
        requestLocationUpdate()

        viewModelScope.launch {
            getLocationStateUseCase()
                .catch { Timber.e(it) }
                .collect {
                    Timber.d("WeatherViewModel: currentLocationState: $it")
                    _currentLocationState.emit(it)
                }
        }
    }

    fun requestLocationUpdate() {
        requestLocationUpdateUseCase()
    }

    fun selectLocation(location: LocationDetected) {
        viewModelScope.launch {
            setSelectedLocationUseCase(location.address)
                .catch { Timber.e(it) }
                .collectLatest { }
        }
    }

    fun toggleLanguage(activity: Activity) {
        if (configurationManager.currentLocale.getValueOrNull() == "en") {
            localeManager.setAppLanguage(activity, "vi")
        } else {
            localeManager.setAppLanguage(activity, "en")
        }
    }

    fun logout(){
        prefs.currentUsername = ""
        _logoutSuccessful.tryEmit(true)
    }
}