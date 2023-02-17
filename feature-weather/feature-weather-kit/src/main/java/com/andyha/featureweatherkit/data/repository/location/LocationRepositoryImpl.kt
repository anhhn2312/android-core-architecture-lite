package com.andyha.featureweatherkit.data.repository.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import com.andyha.corenetwork.di.GlobalScope
import com.andyha.coreutils.permission.RxPermission
import com.andyha.featureweatherkit.data.dao.LocationDetectedDao
import com.andyha.featureweatherkit.data.model.LocationState
import com.andyha.featureweatherkit.data.model.LocationState.*
import com.andyha.featureweatherkit.data.repository.weather.WeatherRepository
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@ExperimentalCoroutinesApi
class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @GlobalScope private val globalScope: CoroutineScope,
    private val locationDetectedDao: LocationDetectedDao,
    private val weatherRepository: WeatherRepository,
) : LocationRepository {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    override val currentLocationState = MutableSharedFlow<LocationState>(replay = 1)

    init {
        globalScope.launch {
            locationDetectedDao
                .getSelectedLocation()
                .distinctUntilChanged()
                .collect {
                    if (it.isNotEmpty()) {
                        currentLocationState.emit(it[0])
                    }
                }
        }
    }

    override fun requestLocationUpdate() {
        RxPermission
            .getInstance(context)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .doOnDenyForever {
                globalScope.launch { currentLocationState.emit(PermissionDeniedForever) }
            }
            .requestForFullResult {
                if (it) getCurrentLocation()
                else globalScope.launch { currentLocationState.emit(PermissionDenied) }
            }
    }

    override fun getLocationHistory(): Flow<List<LocationDetected>> {
        return locationDetectedDao.getLocations()
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        globalScope.launch {
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(LocationRequest.create())

            val client = LocationServices.getSettingsClient(context)
            val checkLocationTask = client.checkLocationSettings(builder.build())
            checkLocationTask
                .addOnSuccessListener {
                    // All location settings are satisfied, process to get current location

                    val cancellationToken = object : CancellationToken() {
                        override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                            CancellationTokenSource().token

                        override fun isCancellationRequested() = false
                    }


                    fusedLocationClient
                        .getCurrentLocation(PRIORITY_HIGH_ACCURACY, cancellationToken)
                        .addOnSuccessListener { location ->
                            if (location != null) {
                                globalScope.launch {
                                    weatherRepository
                                        .getCurrentWeather(location.latitude, location.longitude)
                                        .map {
                                            LocationDetected(
                                                it.address,
                                                it.region,
                                                it.country,
                                                location.latitude,
                                                location.longitude,
                                                it.temperature,
                                                it.icon ?: "",
                                                System.currentTimeMillis(),
                                                true
                                            )
                                        }
                                        .catch { Timber.e(it) }
                                        .collectLatest {
                                            locationDetectedDao.setUnselected(it.address)
                                            locationDetectedDao.insertLocation(it)
                                        }
                                }
                            } else {
                                currentLocationState.tryEmit(LocationGetFailed)
                            }
                        }
                }
                .addOnFailureListener { exception ->
                    if (exception is ResolvableApiException) {
                        currentLocationState.tryEmit(LocationSettingOff(exception))
                    }
                }
        }
    }

    override fun setSelectedLocation(address: String): Flow<Boolean> {
        return flow {
            val updatedRow = locationDetectedDao.setSelected(address)
            if (updatedRow != 0)
                locationDetectedDao.setUnselected(address)
            emit(updatedRow != 0)
        }
    }
}