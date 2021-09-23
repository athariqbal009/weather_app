package com.athar.android.domain

data class DetailWeather(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Lists>,
    val message: Int,
    var kelvinToCelsius: Boolean?
)