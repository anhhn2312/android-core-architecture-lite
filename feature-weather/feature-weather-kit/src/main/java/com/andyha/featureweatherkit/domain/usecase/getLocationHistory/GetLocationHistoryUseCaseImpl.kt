package com.andyha.featureweatherkit.domain.usecase.getLocationHistory

import com.andyha.featureweatherkit.data.model.LocationState.LocationDetected
import com.andyha.featureweatherkit.data.repository.location.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetLocationHistoryUseCaseImpl @Inject constructor(
    private val locationRepository: LocationRepository
): GetLocationHistoryUseCase {
    override fun invoke(): Flow<List<LocationDetected>> {
        return locationRepository.getLocationHistory()
    }
}