package com.athar.android.weatherapp.data.remote.repository

import com.athar.android.domain.DetailWeather
import retrofit2.Response

interface LocationRemoteDataSource {
    suspend fun getLocationWeatherDetail(latitude: Double?, longitude: Double?):Response<DetailWeather>
    suspend fun getLocationForecast(latitude: Double?, longitude: Double?): Response<DetailWeather>
}