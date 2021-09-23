package com.athar.android.weatherapp.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @BindingAdapter(value = ["format_date"], requireAll = false)
    @JvmStatic
    fun formatDate(view: TextView, date: String?) {
        view.text = "Last Update:\n${date?.convertDate()}"
    }

    @BindingAdapter(value = ["to_celsius", "is_kelvin_to_celsius"])
    @JvmStatic
    fun toCelsius(view: TextView, temp: Double?, isKelvinToCelsius: Boolean) {
        view.text = if (isKelvinToCelsius) {
            String.format("%.2f", temp?.kelvinToCelsius()).plus(" ℃")
        } else {
            String.format("%.2f", temp).plus(" K")
        }
    }
}