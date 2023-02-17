package com.andyha.featureweatherkit.domain.usecase.getLocationHistory

import com.andyha.coredata.base.NoInputUseCase
import com.andyha.featureweatherkit.data.model.LocationState.LocationDetected
import kotlinx.coroutines.flow.Flow


interface GetLocationHistoryUseCase : NoInputUseCase<Flow<List<LocationDetected>>>