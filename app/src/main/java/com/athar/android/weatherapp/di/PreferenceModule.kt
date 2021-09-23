package com.athar.android.weatherapp.di

import android.app.Application
import com.athar.android.weatherapp.data.local.prefs.AppPreferences
import com.athar.android.weatherapp.data.local.prefs.PreferenceStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferenceModule {

    @Singleton
    @Provides
    fun providePreference(app: Application): PreferenceStorage {
        return AppPreferences(app)
    }
}