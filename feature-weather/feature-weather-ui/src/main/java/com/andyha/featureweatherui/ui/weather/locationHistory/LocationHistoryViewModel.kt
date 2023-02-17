package com.andyha.featureweatherui.ui.weather.locationHistory

import androidx.lifecycle.viewModelScope
import com.andyha.coreextension.TAG
import com.andyha.coreui.base.adapter.multiSectionsSelection.Section
import com.andyha.coreui.base.viewModel.BaseViewModel
import com.andyha.featureweatherkit.data.model.LocationState.LocationDetected
import com.andyha.featureweatherkit.domain.usecase.getLocationHistory.GetLocationHistoryUseCase
import com.andyha.featureweatherui.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class LocationHistoryViewModel @Inject constructor(
    private val getLocationHistoryUseCase: GetLocationHistoryUseCase
) : BaseViewModel() {

    private val _locations = MutableStateFlow<List<Section<Int, LocationDetected>>>(listOf())
    val locations = _locations.asStateFlow()

    init {
        viewModelScope.launch {
            getLocationHistoryUseCase().justLaunch {
                Timber.d(TAG, "location history: $it")
                _locations.emit(
                    mutableListOf<Section<Int, LocationDetected>>().apply {
                        if (it.isNotEmpty()) {
                            add(Section(R.string.current_location, listOf(it[0])))
                        }
                        if (it.size > 1) {
                            add(Section(R.string.recent, it.subList(1, it.size)))
                        }
                    }
                )
            }
        }
    }
}