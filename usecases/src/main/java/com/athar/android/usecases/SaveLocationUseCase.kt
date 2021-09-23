package com.athar.android.usecases

import com.athar.android.data.LocationDataSource
import com.athar.android.domain.Location

class SaveLocationUseCase(private val locationRepository: LocationDataSource) {
    suspend fun execute(location: Location) = locationRepository.savedLocation(location)
}