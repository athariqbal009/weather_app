package com.athar.android.weatherapp.di

import com.athar.android.weatherapp.data.remote.LocationAPIService
import com.athar.android.weatherapp.data.remote.repository.LocationRemoteDataSource
import com.athar.android.weatherapp.data.remote.repository.LocationRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(locationAPIService: LocationAPIService): LocationRemoteDataSource {
        return LocationRemoteDataSourceImpl(locationAPIService)
    }
}