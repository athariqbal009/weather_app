package com.athar.android.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.athar.android.weatherapp.data.local.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert(onConflict = REPLACE)
    suspend fun addLocation(location: LocationEntity)

    @Query("SELECT * FROM location ORDER BY id DESC")
    fun getAllLocation(): Flow<List<LocationEntity>>

    @Delete
    suspend fun deleteLocation(location: LocationEntity)

    @Query("DELETE FROM location")
    suspend fun deleteAllLocation()
}