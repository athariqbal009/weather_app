package com.athar.android.weatherapp.data.local.repository

import com.athar.android.domain.Location
import kotlinx.coroutines.flow.Flow

interface LocationLocalDataSource {
    suspend fun savedLocationToDB(location: Location)
    suspend fun deleteLocationFromDB(location: Location)
    suspend fun deleteAllLocationFromDB()
    fun getSavedLocationsFromDB(): Flow<List<Location>>
}