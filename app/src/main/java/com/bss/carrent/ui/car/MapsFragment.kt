package com.bss.carrent.ui.car

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.bss.carrent.R
import com.bss.carrent.misc.GeoLocationManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult

class MapsFragment : Fragment() {

    private var locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    private fun requestLocationPermission() {
        permissionRequest.launch(locationPermissions)
    }

    private fun isLocationAllowed(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // Permission result
    private val permissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val granted = permissions.entries.all {
            it.value == true
        }
    }

    private var ownUserMarker: Marker? = null
    private val callback = OnMapReadyCallback { googleMap ->

        val permissionGranted = isLocationAllowed()
        var locationManager: GeoLocationManager

        locationManager = GeoLocationManager(activity as Context)
        if (permissionGranted) {
            locationManager.startLocationTracking(locationCallback)
        }else {
            requestLocationPermission()
        }

        val boundsBuilder = LatLngBounds.Builder()

        //TODO: user has temp location when no location is available
        var lat = 51.584397
        var lng = 4.797850
        val yourLocation = LatLng(lat, lng)
        boundsBuilder.include(yourLocation)

        ownUserMarker = googleMap.addMarker(MarkerOptions().position(yourLocation).title(getString(R.string.youarehere)))
        ownUserMarker?.isVisible = false

        //TODO: Car location has a temporary fixed location, replace with real car location
        val yourCarLocation = LatLng(51.587083, 4.798504)

        googleMap.addMarker(MarkerOptions().position(yourCarLocation).title(getString(R.string.yourcarishere)))
        boundsBuilder.include(yourCarLocation)

        val bounds = boundsBuilder.build()
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,100))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.maps_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                ownUserMarker?.position = LatLng(location.latitude, location.longitude)
                ownUserMarker?.isVisible = true
            }
        }
    }
}