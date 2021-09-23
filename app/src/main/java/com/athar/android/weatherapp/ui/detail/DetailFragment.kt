package com.athar.android.weatherapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.athar.android.data.Resource
import com.athar.android.domain.Location
import com.athar.android.weatherapp.R
import com.athar.android.weatherapp.databinding.FragmentDetailBinding
import com.athar.android.weatherapp.ui.MainActivity
import com.athar.android.weatherapp.vm.LocationViewModel
import timber.log.Timber

class DetailFragment : Fragment() {
    private lateinit var fragmentDetailBinding: FragmentDetailBinding
    private lateinit var viewModel: LocationViewModel
    private lateinit var forecastAdapter: ForecastAdapter
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return fragmentDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forecastAdapter = (activity as MainActivity).forecastAdapter
        val args: DetailFragmentArgs by navArgs()
        val location = args.selectedLocation
        Timber.d("latitude:${location.latitude} longitude:${location.longitude}")
        viewModel = (activity as MainActivity).viewModel
        initRecyclerView()
        //initDetailWeather(location)
        initForecastWeather(location)
    }

    private fun initForecastWeather(location: Location) {
        viewModel.getForecastWeather(location.latitude, location.longitude)
        viewModel.forecastWeather.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    viewModel.progress.value = true
                }
                is Resource.Success -> {
                    viewModel.progress.value = false
                    response.data?.let {
                        (activity as MainActivity).supportActionBar?.title = it.city.name
                        Timber.d("Kelvin to Celsius: ${viewModel.isKelvinToCelsius.value}")
                        it.kelvinToCelsius = viewModel.isKelvinToCelsius.value
                        fragmentDetailBinding.model = it
                        forecastAdapter.kelvinToCelsius = viewModel.isKelvinToCelsius.value!!
                        forecastAdapter.differ.submitList(it.list.subList(1, it.list.size))
                    }
                    Timber.d(response.data.toString())
                }
                is Resource.Error -> {
                    viewModel.progress.value = false
                    Timber.d(response.message.toString())
                }
            }
        })
    }

    private fun initDetailWeather(location: Location) {
        viewModel.getLocationWeather(location.latitude, location.longitude)
        viewModel.locationWeather.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    viewModel.progress.value = true
                }
                is Resource.Success -> {
                    viewModel.progress.value = false
                    response.data?.let {
                        fragmentDetailBinding.model = it
                    }
                    Timber.d(response.data.toString())
                }
                is Resource.Error -> {
                    viewModel.progress.value = false
                    Timber.d(response.message.toString())
                }
            }
        })
    }

    private fun initRecyclerView() {
        fragmentDetailBinding.recyclerView.apply {
            adapter = forecastAdapter
        }
    }
}