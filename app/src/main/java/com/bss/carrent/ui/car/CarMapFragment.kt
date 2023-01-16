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
import androidx.navigation.fragment.navArgs
import com.bss.carrent.R
import com.bss.carrent.misc.GeoLocationManager
import com.bss.carrent.ui.rental.RentalCreateFragmentArgs
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class CarMapFragment : Fragment() {
    private val args: CarMapFragmentArgs by navArgs()

    private var ownUserMarker: Marker? = null
    private val callback = OnMapReadyCallback { googleMap ->

        var locationManager = GeoLocationManager(requireContext())
        val permissionGranted = locationManager.isLocationAllowed(requireContext())
        if (permissionGranted) {
            locationManager.startLocationTracking(locationCallback)
        } else {
            locationManager.requestLocationPermission(this)
        }

        val boundsBuilder = LatLngBounds.Builder()

        //TODO: user has temp location when no location is available
        var lat = 51.584397
        var lng = 4.797850
        val yourLocation = LatLng(lat, lng)
        boundsBuilder.include(yourLocation)

        ownUserMarker = googleMap.addMarker(
            MarkerOptions().position(yourLocation).title(getString(R.string.youarehere))
        )
        ownUserMarker?.isVisible = false

        val yourCarLocation = LatLng(args.car.lat, args.car.lng)

        googleMap.addMarker(
            MarkerOptions().position(yourCarLocation).title(getString(R.string.yourcarishere))
        )
        boundsBuilder.include(yourCarLocation)

        val bounds = boundsBuilder.build()
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
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