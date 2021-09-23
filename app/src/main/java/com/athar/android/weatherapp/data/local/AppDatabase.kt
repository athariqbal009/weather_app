package com.athar.android.weatherapp.data.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.athar.android.weatherapp.data.local.dao.LocationDao
import com.athar.android.weatherapp.data.local.entity.LocationEntity

private const val DATABASE_VERSION = 1

@Database(
    entities = [LocationEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}
