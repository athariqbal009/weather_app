package com.athar.android.weatherapp.data.local.prefs

import android.content.SharedPreferences

interface PreferenceStorage {
    fun getPrefs():SharedPreferences
    fun getBool(key: String) : Boolean
}