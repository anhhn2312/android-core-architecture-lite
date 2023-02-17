package com.andyha.coreui.base.navigation

import dagger.hilt.android.scopes.ActivityScoped


@ActivityScoped
interface AppNavigator {

    //Login
    fun navigateToLogin()

    // Main ----------------------------------------------------------------------------------------
    fun navigateToWeather()
}