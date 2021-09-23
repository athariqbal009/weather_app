package com.athar.android.usecases

import com.athar.android.data.LocationDataSource
import com.athar.android.domain.Location
import kotlinx.coroutines.flow.Flow

class GetSavedLocationUseCase(private val locationRepository: LocationDataSource) {
    fun execute():Flow<List<Location>> {
        return locationRepository.getSavedLocations()
    }
}