package com.andyha.featureweatherkit.di

import com.andyha.corenetwork.qualifier.WeatherService
import com.andyha.featureweatherkit.data.networking.api.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun bindWeatherApi(@WeatherService retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)
}