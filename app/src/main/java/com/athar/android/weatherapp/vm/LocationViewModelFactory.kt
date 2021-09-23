package com.athar.android.weatherapp.vm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.athar.android.usecases.*
import com.athar.android.weatherapp.data.local.prefs.PreferenceStorage

class LocationViewModelFactory(
    private val app:Application,
    private val detailLocationWeatherUseCase: DetailLocationWeatherUseCase,
    private val getLocationForecastUseCase: GetLocationForecastUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val deleteSavedLocationUseCase: DeleteSavedLocationUseCase,
    private val preferenceStorage: PreferenceStorage
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LocationViewModel(
            app,
            detailLocationWeatherUseCase,
            getLocationForecastUseCase,
            saveLocationUseCase,
            getSavedLocationUseCase,
            deleteSavedLocationUseCase,
            preferenceStorage
        ) as T
    }
}