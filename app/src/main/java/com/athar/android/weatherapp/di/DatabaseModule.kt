package com.athar.android.weatherapp.di

import android.app.Application
import androidx.room.Room
import com.athar.android.weatherapp.data.local.AppDatabase
import com.athar.android.weatherapp.data.local.dao.LocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "location-database")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideLocationDao(appDatabase: AppDatabase):LocationDao {
        return appDatabase.locationDao()
    }
}