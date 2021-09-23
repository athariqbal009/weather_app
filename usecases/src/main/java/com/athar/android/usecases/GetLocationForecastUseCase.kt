package com.athar.android.usecases

import com.athar.android.data.LocationDataSource
import com.athar.android.data.Resource
import com.athar.android.domain.DetailWeather

class GetLocationForecastUseCase(private val locationDataSource: LocationDataSource) {
    suspend fun execute(latitude: Double?, longitude: Double?): Resource<DetailWeather> {
        return locationDataSource.getLocationForecast(latitude, longitude)
    }
}