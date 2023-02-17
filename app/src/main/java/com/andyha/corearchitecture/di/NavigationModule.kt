package com.andyha.corearchitecture.di

import com.andyha.corearchitecture.navigation.AppNavigatorImpl
import com.andyha.coreui.base.navigation.AppNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped


@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {

    @Binds
    @ActivityScoped
    abstract fun provideAppNavigator(impl: AppNavigatorImpl): AppNavigator
}