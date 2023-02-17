package com.andyha.corearchitecture.manager

import com.andyha.coredata.manager.SessionManager
import com.andyha.coredata.manager.SessionState
import com.andyha.coredata.manager.SessionState.LoggedIn
import com.andyha.coredata.manager.SessionState.LoggedOut
import com.andyha.coredata.storage.preference.*
import com.andyha.coreextension.getValueOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import okio.ByteString
import javax.inject.Inject


class SessionManagerImpl @Inject constructor(
    private val prefs: AppSharedPreference,
) : SessionManager {
    private val _sessionState = MutableSharedFlow<SessionState>(replay = 1)
    override val sessionState = _sessionState.asSharedFlow()

    override fun onTokenChanged(accessToken: String?, refreshToken: String?, idToken: String?) {
        accessToken?.let {
            prefs.token = accessToken
        }

        refreshToken?.let {
            prefs.refreshToken = refreshToken
        }

        idToken?.let { prefs.idToken = idToken }

        _sessionState.tryEmit(LoggedIn)

        ByteString
    }

    override fun signOut() {
        prefs.token = ""
        _sessionState.tryEmit(LoggedOut)
    }

    override fun isLoggedIn(): Boolean = _sessionState.getValueOrNull() == LoggedIn
}

fun Flow<SessionState>.doOnLoggedIn(onLoggedIn: () -> Unit): Flow<SessionState> =
    map {
        if (it == LoggedIn) {
            onLoggedIn.invoke()
        }
        return@map it
    }

fun Flow<SessionState>.doOnLoggedOut(onLoggedOut: () -> Unit): Flow<SessionState> =
    map {
        if (it == LoggedOut) {
            onLoggedOut.invoke()
        }
        return@map it
    }