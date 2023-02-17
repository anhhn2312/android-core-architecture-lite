package com.andyha.corearchitecture.di

import com.andyha.corearchitecture.config.NetworkConfigImpl
import com.andyha.corenetwork.config.NetworkConfig
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ConfigModule {

    @Binds
    @Singleton
    abstract fun bindNetworkConfig(networkConfig: NetworkConfigImpl): NetworkConfig
}