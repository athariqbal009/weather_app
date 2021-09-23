package com.athar.android.weatherapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.athar.android.weatherapp.R
import com.athar.android.weatherapp.data.local.prefs.PreferenceStorage
import com.athar.android.weatherapp.databinding.ActivityMainBinding
import com.athar.android.weatherapp.ui.detail.ForecastAdapter
import com.athar.android.weatherapp.ui.saved.SavedLocationAdapter
import com.athar.android.weatherapp.utils.hide
import com.athar.android.weatherapp.utils.show
import com.athar.android.weatherapp.vm.LocationViewModel
import com.athar.android.weatherapp.vm.LocationViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    @Inject
    lateinit var factory: LocationViewModelFactory

    @Inject
    lateinit var savedAdapter: SavedLocationAdapter

    @Inject
    lateinit var forecastAdapter: ForecastAdapter

    @Inject
    lateinit var preferences: PreferenceStorage

    lateinit var viewModel: LocationViewModel
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory).get(LocationViewModel::class.java)
        viewModel.progress.observe(this, {
            if (it) binding.progressBar.show() else binding.progressBar.hide()
        })
        setNavigation()
    }

    private fun setNavigation() {
        val actionBar = supportActionBar!!
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(
            navController
        )

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Timber.d("OnDestinationChanged: $arguments")
            when (destination.id) {
                R.id.detailFragment -> {
                    actionBar.setDisplayHomeAsUpEnabled(true)
                }
                R.id.helpFragment -> {
                    actionBar.title = getString(R.string.title_help)
                    actionBar.setDisplayHomeAsUpEnabled(true)
                }
                R.id.settingsFragment -> {
                    actionBar.title = getString(R.string.title_setting)
                    actionBar.setDisplayHomeAsUpEnabled(true)
                }
                else -> {
                    actionBar.setDisplayHomeAsUpEnabled(false)
                    actionBar.title = getString(R.string.app_name)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Timber.d("Item selected: ${item.itemId}")
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.help -> {
                if (navController.currentDestination?.id != R.id.helpFragment) {
                    navHostFragment.findNavController()
                        .navigate(R.id.action_homeFragment_to_helpFragment)
                }
                true
            }
            R.id.settings -> {
                if (navController.currentDestination?.id != R.id.settingsFragment) {
                    navHostFragment.findNavController()
                        .navigate(R.id.action_homeFragment_to_settingsFragment)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onResume() {
        super.onResume()
        preferences.getPrefs().registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferences.getPrefs().unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == getString(R.string.key_kelvin_to_celsius)) {
            val value = preferences.getBool(key)
            Timber.d("Shared Preference: $value")
            viewModel.isKelvinToCelsius.postValue(value)
        }
    }
}