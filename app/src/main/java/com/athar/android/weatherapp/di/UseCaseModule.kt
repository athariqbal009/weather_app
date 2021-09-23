package com.athar.android.weatherapp.di

import com.athar.android.data.LocationDataSource
import com.athar.android.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideDetailLocationWeatherUseCase(locationDataSource: LocationDataSource): DetailLocationWeatherUseCase {
        return DetailLocationWeatherUseCase(locationDataSource)
    }

    @Singleton
    @Provides
    fun provideLocationForecastUseCase(locationDataSource: LocationDataSource): GetLocationForecastUseCase {
        return GetLocationForecastUseCase(locationDataSource)
    }

    @Singleton
    @Provides
    fun provideSaveLocationUseCase(locationDataSource: LocationDataSource): SaveLocationUseCase {
        return SaveLocationUseCase(locationDataSource)
    }

    @Singleton
    @Provides
    fun provideGetSavedLocationUseCase(locationDataSource: LocationDataSource): GetSavedLocationUseCase {
        return GetSavedLocationUseCase(locationDataSource)
    }

    @Singleton
    @Provides
    fun provideDeleteSavedLocationUseCase(locationDataSource: LocationDataSource): DeleteSavedLocationUseCase {
        return DeleteSavedLocationUseCase(locationDataSource)
    }
}
