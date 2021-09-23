package com.athar.android.usecases

import com.athar.android.data.LocationDataSource
import com.athar.android.domain.Location

class DeleteSavedLocationUseCase(private val locationRepository: LocationDataSource) {
    suspend fun execute(location: Location) = locationRepository.deleteLocation(location)
    suspend fun excute() = locationRepository.deleteAllLocation()
}