package com.athar.android.weatherapp.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPreferences @Inject constructor(@ApplicationContext context: Context) : PreferenceStorage {
    private val preference by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun getPrefs(): SharedPreferences {
        return preference
    }

    override fun getBool(key: String): Boolean {
        return preference.getBoolean(key, false)
    }
}