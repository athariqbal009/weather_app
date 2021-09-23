package com.athar.android.weatherapp.di

import com.athar.android.data.LocationDataSource
import com.athar.android.weatherapp.data.LocationDataSourceImpl
import com.athar.android.weatherapp.data.local.repository.LocationLocalDataSource
import com.athar.android.weatherapp.data.remote.repository.LocationRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun provideLocationDataSource(
        locationLocalDataSource: LocationLocalDataSource,
        locationRemoteDataSource: LocationRemoteDataSource
    ): LocationDataSource {
        return LocationDataSourceImpl(locationLocalDataSource, locationRemoteDataSource)
    }
}