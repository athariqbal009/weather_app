package com.athar.android.weatherapp.ui.home

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.athar.android.domain.Location
import com.athar.android.weatherapp.R
import com.athar.android.weatherapp.data.local.entity.LocationEntity
import com.athar.android.weatherapp.databinding.FragmentHomeBinding
import com.athar.android.weatherapp.ui.MainActivity
import com.athar.android.weatherapp.vm.LocationViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.util.*

class HomeFragment : Fragment(){
    private lateinit var viewModel:LocationViewModel
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var location: Location
    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.uiSettings.isMyLocationButtonEnabled = true
        googleMap.setOnMapClickListener { latlng ->
            val markerOptions = MarkerOptions().apply {
                position(latlng)
            }
            val addresses:List<Address> = Geocoder(context, Locale.getDefault()).getFromLocation(latlng.latitude, latlng.longitude, 1)
                val name = if (!addresses.isNullOrEmpty() && !addresses[0].locality.isNullOrEmpty()) {
                addresses[0].locality
            } else {
                latlng.latitude.toString().plus(":").plus(latlng.longitude.toString())
            }

            // add location to local db
            Timber.d(name)
            location = Location(null, name, latlng.latitude, latlng.longitude)

            googleMap.apply {
                clear()
                animateCamera(CameraUpdateFactory.newLatLng(latlng))
                addMarker(markerOptions)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentHomeBinding = FragmentHomeBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        setupMapFragment(view)
    }

    private fun setupMapFragment(view:View) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(callback)
        fragmentHomeBinding.floatingActionButton.setOnClickListener {
            if (::location.isInitialized) {
                viewModel.saveLocation(location)
                Snackbar.make(view,"Saved Successfully!", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(view,"Please point a marker", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}