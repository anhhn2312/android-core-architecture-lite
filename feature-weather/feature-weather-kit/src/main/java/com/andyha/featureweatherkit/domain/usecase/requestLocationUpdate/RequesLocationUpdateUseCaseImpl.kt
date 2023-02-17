package com.andyha.featureweatherkit.domain.usecase.requestLocationUpdate

import com.andyha.featureweatherkit.data.repository.location.LocationRepository
import javax.inject.Inject


class RequesLocationUpdateUseCaseImpl @Inject constructor(
    private val locationRepository: LocationRepository
): RequestLocationUpdateUseCase {

    override fun invoke(){
        return locationRepository.requestLocationUpdate()
    }
}