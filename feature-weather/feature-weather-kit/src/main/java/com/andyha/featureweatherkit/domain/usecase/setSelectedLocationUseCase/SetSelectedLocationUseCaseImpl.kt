package com.andyha.featureweatherkit.domain.usecase.setSelectedLocationUseCase

import com.andyha.featureweatherkit.data.repository.location.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SetSelectedLocationUseCaseImpl @Inject constructor(
    private val locationRepository: LocationRepository
): SetSelectedLocationUseCase {

    override fun invoke(params: String): Flow<Boolean> {
        return locationRepository.setSelectedLocation(params)
    }
}