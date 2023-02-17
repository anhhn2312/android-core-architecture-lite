package com.andyha.corenetwork.di

import com.andyha.corenetwork.ResponseConverter
import com.andyha.corenetwork.config.NetworkConfig
import com.andyha.corenetwork.config.NetworkConfigConstants
import com.andyha.corenetwork.interceptor.TimeoutInterceptor
import com.andyha.corenetwork.interceptor.WeatherRequestInterceptor
import com.andyha.corenetwork.qualifier.*
import com.andyha.corenetwork.qualifier.Service.WEATHER
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class WeatherServiceModule {

    @Singleton
    @Provides
    @WeatherService
    fun provideWeatherBaseUrl(networkConfig: NetworkConfig) = networkConfig.weatherBaseUrl

    @Singleton
    @Provides
    @WeatherService
    fun provideRetrofit(
        @WeatherService baseUrl: String,
        gson: Gson,
        @WeatherService okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ResponseConverter(gson))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @WeatherService
    fun provideHttpClient(
        @ForRequestInterceptor(WEATHER) requestInterceptor: Interceptor,
        @ForTimeout timeoutInterceptor: Interceptor,
        @ForLogging logging: Interceptor?,
        @ForHostSelection(WEATHER) hostSelection: Interceptor?,
        autoAuthenticator: okhttp3.Authenticator
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder().apply {
            connectTimeout(NetworkConfigConstants.DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(NetworkConfigConstants.DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(NetworkConfigConstants.DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(requestInterceptor)
            addInterceptor(timeoutInterceptor)
            hostSelection?.let { addInterceptor(it) }
            logging?.let { addInterceptor(it) }
            authenticator(autoAuthenticator)
        }
        return httpClient.build()
    }

    @Singleton
    @Provides
    @ForRequestInterceptor(WEATHER)
    fun provideRequestInterceptor(networkConfig: NetworkConfig): Interceptor =
        WeatherRequestInterceptor(networkConfig)

    @Singleton
    @Provides
    @ForHostSelection(WEATHER)
    fun provideHostSelectionInterceptor(): Interceptor? {
        // return HostSelectionInterceptor(remoteConfigManager.weatherBaseUrl)
        // Disable for now as this demo is not using remote config to configure api base url
        return null
    }

    @Singleton
    @Provides
    @ForTimeout
    fun provideTimeoutInterceptor(): Interceptor = TimeoutInterceptor()
}