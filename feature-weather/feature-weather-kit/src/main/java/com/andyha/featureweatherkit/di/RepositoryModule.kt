package com.andyha.featureweatherkit.di

import com.andyha.featureweatherkit.data.repository.location.LocationRepository
import com.andyha.featureweatherkit.data.repository.location.LocationRepositoryImpl
import com.andyha.featureweatherkit.data.repository.weather.WeatherRepository
import com.andyha.featureweatherkit.data.repository.weather.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository
}