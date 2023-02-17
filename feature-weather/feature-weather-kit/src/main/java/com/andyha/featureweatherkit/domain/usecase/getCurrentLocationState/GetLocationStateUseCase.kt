package com.andyha.featureweatherkit.domain.usecase.getCurrentLocationState

import com.andyha.coredata.base.NoInputUseCase
import com.andyha.featureweatherkit.data.model.LocationState
import kotlinx.coroutines.flow.Flow


interface GetLocationStateUseCase: NoInputUseCase<Flow<LocationState>>