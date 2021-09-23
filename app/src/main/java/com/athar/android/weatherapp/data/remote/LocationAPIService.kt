package com.athar.android.weatherapp.data.remote

import com.athar.android.domain.DetailWeather
import com.athar.android.weatherapp.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationAPIService {

    @GET("data/2.5/weather")
    suspend fun getLocationWeather(
        @Query("lat")
        lat:Double?,
        @Query("lon")
        lon:Double?,
        @Query("appid")
        appid: String = BuildConfig.API_KEY
    ): Response<DetailWeather>

    @GET("data/2.5/forecast")
    suspend fun getLocationForecast(
        @Query("lat")
        lat:Double?,
        @Query("lon")
        lon:Double?,
        @Query("appid")
        appid: String = BuildConfig.API_KEY
    ): Response<DetailWeather>
}