package com.athar.android.weatherapp.di

import com.athar.android.weatherapp.BuildConfig
import com.athar.android.weatherapp.data.remote.LocationAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideLocationAPIService(retrofit: Retrofit):LocationAPIService {
        return retrofit.create(LocationAPIService::class.java)
    }
}