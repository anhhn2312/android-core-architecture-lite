package com.andyha.corearchitecture

import android.app.Application
import com.andyha.coreutils.timber.AppTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogging()
    }

    private fun initLogging() {
        Timber.plant(AppTree())
    }
}