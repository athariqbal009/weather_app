package com.athar.android.weatherapp.data.remote.repository

import com.athar.android.domain.DetailWeather
import com.athar.android.weatherapp.data.remote.LocationAPIService
import retrofit2.Response

class LocationRemoteDataSourceImpl(
    private val locationAPIService: LocationAPIService
):LocationRemoteDataSource {
    override suspend fun getLocationWeatherDetail(
        latitude: Double?,
        longitude: Double?
    ): Response<DetailWeather> {
        return locationAPIService.getLocationWeather(latitude, longitude)
    }

    override suspend fun getLocationForecast(
        latitude: Double?,
        longitude: Double?
    ): Response<DetailWeather> {
        return locationAPIService.getLocationForecast(latitude, longitude)
    }
}