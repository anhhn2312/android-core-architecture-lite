package com.andyha.corearchitecture.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.andyha.coreui.base.navigation.AppNavigator
import com.andyha.featureloginui.login.LoginActivity
import com.andyha.featureweatherui.ui.activity.WeatherActivity
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AppNavigatorImpl @Inject constructor(@ActivityContext private val context: Context) :
    AppNavigator {

    override fun navigateToLogin() {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }

    override fun navigateToWeather() {
        context.startActivity(Intent(context, WeatherActivity::class.java))
    }
}