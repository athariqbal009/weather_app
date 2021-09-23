package com.athar.android.weatherapp.di

import android.app.Application
import com.athar.android.usecases.*
import com.athar.android.weatherapp.data.local.prefs.PreferenceStorage
import com.athar.android.weatherapp.vm.LocationViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelFactoryModule {
    @Singleton
    @Provides
    fun provideLocationViewModelFactory(
        app: Application,
        detailLocationWeatherUseCase: DetailLocationWeatherUseCase,
        getLocationForecastUseCase: GetLocationForecastUseCase,
        saveLocationUseCase: SaveLocationUseCase,
        getSavedLocationUseCase: GetSavedLocationUseCase,
        deleteSavedLocationUseCase: DeleteSavedLocationUseCase,
        preferenceStorage: PreferenceStorage
    ): LocationViewModelFactory {
        return LocationViewModelFactory(
            app,
            detailLocationWeatherUseCase,
            getLocationForecastUseCase,
            saveLocationUseCase,
            getSavedLocationUseCase,
            deleteSavedLocationUseCase,
            preferenceStorage
        )
    }
}