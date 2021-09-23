package com.athar.android.weatherapp.data.local.repository

import com.athar.android.domain.Location
import com.athar.android.weatherapp.data.local.dao.LocationDao
import com.athar.android.weatherapp.data.local.entity.LocationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class LocationLocalDataSourceImpl(
    private val locationDao: LocationDao
) : LocationLocalDataSource {
    override suspend fun savedLocationToDB(location: Location) {
        locationDao.addLocation(
            LocationEntity(
                id = location.id,
                name = location.name,
                latitude = location.latitude,
                longitude = location.longitude
            )
        )
    }

    override suspend fun deleteLocationFromDB(location: Location) {
        locationDao.deleteLocation(
            LocationEntity(
                id = location.id,
                name = location.name,
                latitude = location.latitude,
                longitude = location.longitude
            )
        )
    }

    override suspend fun deleteAllLocationFromDB() {
        locationDao.deleteAllLocation()
    }

    override fun getSavedLocationsFromDB(): Flow<List<Location>> {
        return locationDao.getAllLocation().map {
            Timber.d(it.size.toString())
            it.map { it ->
                Location(
                    id = it.id,
                    name = it.name,
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            }
        }
    }
}