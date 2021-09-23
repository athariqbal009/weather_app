package com.athar.android.domain

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)