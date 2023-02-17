package com.andyha.featureloginui.login

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.andyha.coredata.manager.ConfigurationManager
import com.andyha.coredata.storage.preference.AppSharedPreference
import com.andyha.coredata.storage.preference.currentUserEmail
import com.andyha.coredata.storage.preference.currentUsername
import com.andyha.coreextension.getValueOrNull
import com.andyha.coreextension.localehelper.LocaleManager
import com.andyha.coreui.base.theme.toggleTheme
import com.andyha.coreui.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sharedPreference: AppSharedPreference,
    private val localeManager: LocaleManager,
    private val configurationManager: ConfigurationManager
) : BaseViewModel() {
    private val _isUserAuthorized = MutableStateFlow(false)
    val isUserAuthorized = _isUserAuthorized.asStateFlow()

    init {
        checkLoginSession()
    }

    private fun checkLoginSession() {
        if (sharedPreference.currentUsername?.isNotEmpty() == true) {
            _isUserAuthorized.tryEmit(true)
        }
    }

    fun login(username: String) {
        viewModelScope.launch {
            sharedPreference.currentUsername = username
            delay(1000L)
            _isUserAuthorized.tryEmit(true)
        }
    }

    fun toggleLanguage(activity: Activity) {
        if (configurationManager.currentLocale.getValueOrNull() == "en") {
            localeManager.setAppLanguage(activity, "vi")
        } else {
            localeManager.setAppLanguage(activity, "en")
        }
    }

    fun toggleTheme(): Int {
        return sharedPreference.toggleTheme()
    }
}