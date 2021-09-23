package com.athar.android.weatherapp.vm

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.athar.android.data.Resource
import com.athar.android.domain.DetailWeather
import com.athar.android.domain.Location
import com.athar.android.usecases.*
import com.athar.android.weatherapp.R
import com.athar.android.weatherapp.data.local.entity.LocationEntity
import com.athar.android.weatherapp.data.local.prefs.PreferenceStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class LocationViewModel(
    private val app:Application,
    private val detailLocationWeatherUseCase: DetailLocationWeatherUseCase,
    private val getLocationForecastUseCase: GetLocationForecastUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val deleteSavedLocationUseCase: DeleteSavedLocationUseCase,
    private val preferenceStorage: PreferenceStorage
): AndroidViewModel(app) {
    val locationWeather: MutableLiveData<Resource<DetailWeather>> = MutableLiveData()
    val forecastWeather: MutableLiveData<Resource<DetailWeather>> = MutableLiveData()
    val progress : MutableLiveData<Boolean> = MutableLiveData()
    val helpData: MutableLiveData<Resource<String>> = MutableLiveData()
    val isKelvinToCelsius: MutableLiveData<Boolean> = MutableLiveData(preferenceStorage.getBool(app.getString(R.string.key_kelvin_to_celsius)))
    fun getLocationWeather(latitude: Double?, longitude: Double?) = viewModelScope.launch(Dispatchers.IO) {
        locationWeather.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val result = async { detailLocationWeatherUseCase.execute(latitude, longitude) }
                locationWeather.postValue(result.await())
            } else {
                locationWeather.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e:Exception) {
            locationWeather.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getForecastWeather(latitude: Double?, longitude: Double?) = viewModelScope.launch(Dispatchers.IO) {
        forecastWeather.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val result = async { getLocationForecastUseCase.execute(latitude, longitude) }
                forecastWeather.postValue(result.await())
            } else {
                forecastWeather.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e:Exception) {
            forecastWeather.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getHelp() {
        helpData.postValue(Resource.Loading())
        viewModelScope.launch {
            delay(1000)
            helpData.postValue(Resource.Success("file:///android_asset/help.html"))
        }
    }

    fun saveLocation(location: Location) = viewModelScope.launch {
        saveLocationUseCase.execute(location)
    }

    fun getLocation() = liveData {
        getSavedLocationUseCase.execute().collect {
            emit(it)
        }
    }

    fun deleteLocation(location: Location) = viewModelScope.launch {
        deleteSavedLocationUseCase.execute(location)
    }

    fun deleteAllLocation() = viewModelScope.launch {
        deleteSavedLocationUseCase.excute()
    }

    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}