package com.andyha.featureweatherkit.di

import com.andyha.featureweatherkit.domain.usecase.getCurrentLocationState.GetLocationStateUseCase
import com.andyha.featureweatherkit.domain.usecase.getCurrentLocationState.GetLocationStateUseCaseImpl
import com.andyha.featureweatherkit.domain.usecase.requestLocationUpdate.RequestLocationUpdateUseCase
import com.andyha.featureweatherkit.domain.usecase.requestLocationUpdate.RequesLocationUpdateUseCaseImpl
import com.andyha.featureweatherkit.domain.usecase.getCurrentWeather.GetCurrentWeatherUseCase
import com.andyha.featureweatherkit.domain.usecase.getCurrentWeather.GetCurrentWeatherUseCaseImpl
import com.andyha.featureweatherkit.domain.usecase.getDailyForecast.GetDailyForecastUseCase
import com.andyha.featureweatherkit.domain.usecase.getDailyForecast.GetDailyForecastUseCaseImpl
import com.andyha.featureweatherkit.domain.usecase.getHourlyForecast.GetHourlyForecastUseCase
import com.andyha.featureweatherkit.domain.usecase.getHourlyForecast.GetHourlyForecastUseCaseImpl
import com.andyha.featureweatherkit.domain.usecase.getLocationHistory.GetLocationHistoryUseCase
import com.andyha.featureweatherkit.domain.usecase.getLocationHistory.GetLocationHistoryUseCaseImpl
import com.andyha.featureweatherkit.domain.usecase.setSelectedLocationUseCase.SetSelectedLocationUseCase
import com.andyha.featureweatherkit.domain.usecase.setSelectedLocationUseCase.SetSelectedLocationUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetCurrentWeatherUseCase(impl: GetCurrentWeatherUseCaseImpl): GetCurrentWeatherUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetHourlyForecastUseCase(impl: GetHourlyForecastUseCaseImpl): GetHourlyForecastUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetDailyForecastUseCase(impl: GetDailyForecastUseCaseImpl): GetDailyForecastUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindRequestLocationUpdateUseCase(impl: RequesLocationUpdateUseCaseImpl): RequestLocationUpdateUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetLocationStateUseCase(impl: GetLocationStateUseCaseImpl): GetLocationStateUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetLocationHistoryUseCase(impl: GetLocationHistoryUseCaseImpl): GetLocationHistoryUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSetSelectedLocationUseCase(impl: SetSelectedLocationUseCaseImpl): SetSelectedLocationUseCase
}