package com.andyha.corearchitecture.manager

import com.andyha.coredata.manager.SessionManager
import com.andyha.coredata.storage.preference.AppSharedPreference
import com.andyha.corenetwork.config.NetworkConfig
import com.andyha.corenetwork.config.TokenRefresher
import javax.inject.Inject


class TokenRefresherImpl @Inject constructor(
    private val prefs: AppSharedPreference,
    private val sessionManager: SessionManager,
    private val networkConfig: NetworkConfig,
) : TokenRefresher {
    override fun refreshToken(): String? {
        return null
    }
}