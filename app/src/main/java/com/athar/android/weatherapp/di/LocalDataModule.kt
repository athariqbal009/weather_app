package com.athar.android.weatherapp.di

import com.athar.android.weatherapp.data.local.dao.LocationDao
import com.athar.android.weatherapp.data.local.repository.LocationLocalDataSource
import com.athar.android.weatherapp.data.local.repository.LocationLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(locationDao: LocationDao): LocationLocalDataSource {
        return LocationLocalDataSourceImpl(locationDao)
    }
}