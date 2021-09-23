package com.athar.android.weatherapp.di

import com.athar.android.weatherapp.ui.detail.ForecastAdapter
import com.athar.android.weatherapp.ui.saved.SavedLocationAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Singleton
    @Provides
    fun provideSavedLocationAdapter(): SavedLocationAdapter{
        return SavedLocationAdapter()
    }

    @Singleton
    @Provides
    fun provideForecastLocationAdapter(): ForecastAdapter{
        return ForecastAdapter()
    }
}