package com.athar.android.weatherapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "location")
data class LocationEntity (
    @PrimaryKey(autoGenerate = true) val id:Int? = 0,
    @ColumnInfo(name = "name") val name:String?,
    @ColumnInfo(name = "latitude") val latitude:Double?,
    @ColumnInfo(name = "longitude") val longitude:Double?
)