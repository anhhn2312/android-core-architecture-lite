package com.andyha.corearchitecture.config

import com.andyha.corearchitecture.BuildConfig
import com.andyha.corenetwork.config.NetworkConfig
import javax.inject.Inject


class NetworkConfigImpl @Inject constructor() : NetworkConfig {
    override var weatherBaseUrl = BuildConfig.weatherBaseUrl
    override var weatherApiKey = BuildConfig.weatherApiKey
}