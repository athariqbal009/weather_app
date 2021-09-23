package com.athar.android.data

import com.athar.android.domain.DetailWeather
import com.athar.android.domain.Location
import kotlinx.coroutines.flow.Flow

interface LocationDataSource {
    suspend fun detailLocationWeather(latitude: Double?, longitude: Double?): Resource<DetailWeather>
    suspend fun getLocationForecast(latitude: Double?, longitude: Double?): Resource<DetailWeather>
    suspend fun savedLocation(location: Location)
    suspend fun deleteLocation(location: Location)
    suspend fun deleteAllLocation()
    fun getSavedLocations(): Flow<List<Location>>
}