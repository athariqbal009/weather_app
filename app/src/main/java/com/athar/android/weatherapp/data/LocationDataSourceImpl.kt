package com.athar.android.weatherapp.data

import com.athar.android.data.LocationDataSource
import com.athar.android.data.Resource
import com.athar.android.domain.DetailWeather
import com.athar.android.domain.Location
import com.athar.android.weatherapp.data.local.repository.LocationLocalDataSource
import com.athar.android.weatherapp.data.remote.repository.LocationRemoteDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class LocationDataSourceImpl(
    private val locationLocalDataSource: LocationLocalDataSource,
    private val locationRemoteDataSource: LocationRemoteDataSource
):LocationDataSource {

    override suspend fun detailLocationWeather(
        latitude: Double?,
        longitude: Double?
    ): Resource<DetailWeather> {
        return responseToResource(locationRemoteDataSource.getLocationWeatherDetail(latitude, longitude))
    }

    override suspend fun getLocationForecast(
        latitude: Double?,
        longitude: Double?
    ): Resource<DetailWeather> {
        return responseToResource(locationRemoteDataSource.getLocationForecast(latitude, longitude))
    }

    override suspend fun savedLocation(location: Location) {
        locationLocalDataSource.savedLocationToDB(location)
    }

    override suspend fun deleteLocation(location: Location) {
        locationLocalDataSource.deleteLocationFromDB(location)
    }

    override suspend fun deleteAllLocation() {
        locationLocalDataSource.deleteAllLocationFromDB()
    }

    override fun getSavedLocations(): Flow<List<Location>> {
        return locationLocalDataSource.getSavedLocationsFromDB()
    }

    private fun responseToResource(response: Response<DetailWeather>):Resource<DetailWeather>{
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}