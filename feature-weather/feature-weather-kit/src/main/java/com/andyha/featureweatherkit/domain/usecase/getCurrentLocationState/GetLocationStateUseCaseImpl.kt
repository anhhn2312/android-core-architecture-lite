package com.andyha.featureweatherkit.domain.usecase.getCurrentLocationState

import com.andyha.featureweatherkit.data.model.LocationState
import com.andyha.featureweatherkit.data.repository.location.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetLocationStateUseCaseImpl @Inject constructor(
    private val locationRepository: LocationRepository
): GetLocationStateUseCase {
    override fun invoke(): Flow<LocationState> {
        return locationRepository.currentLocationState
    }
}