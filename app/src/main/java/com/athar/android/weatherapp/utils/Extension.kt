package com.athar.android.weatherapp.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun String.convertDate(): String {
    val parse = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(this)
    return SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault()).format(parse)
}

fun String.convertTime(): String {
    val parse = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(this)
    return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(parse)
}

fun Double.kelvinToFahrenheit(): Double{
    return ((this - 32) * 5) / 9
}

fun Double.kelvinToCelsius(): Double{
    return (this - 273.15)
}