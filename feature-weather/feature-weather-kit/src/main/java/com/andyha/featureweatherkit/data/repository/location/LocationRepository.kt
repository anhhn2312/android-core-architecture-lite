package com.andyha.featureweatherkit.data.repository.location

import com.andyha.featureweatherkit.data.model.LocationState
import com.andyha.featureweatherkit.data.model.LocationState.LocationDetected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow


interface LocationRepository {
    val currentLocationState: SharedFlow<LocationState>

    fun requestLocationUpdate()
    fun getLocationHistory(): Flow<List<LocationDetected>>
    fun setSelectedLocation(address: String): Flow<Boolean>
}