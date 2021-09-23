package com.athar.android.weatherapp.ui.setting

import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.athar.android.weatherapp.R
import com.athar.android.weatherapp.ui.MainActivity
import com.athar.android.weatherapp.vm.LocationViewModel
import com.google.android.material.snackbar.Snackbar

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var viewModel: LocationViewModel
    private lateinit var preference: Preference
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        preference = findPreference(getString(R.string.key_clear_storage))!!
        preference.setOnPreferenceClickListener {
            viewModel.deleteAllLocation()
            Snackbar.make(view, "Deleted Successfully", Snackbar.LENGTH_LONG).show()
            true
        }
    }
}