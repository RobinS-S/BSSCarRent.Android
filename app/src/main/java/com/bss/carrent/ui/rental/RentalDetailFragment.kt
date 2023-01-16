package com.bss.carrent.ui.rental

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bss.carrent.data.rental.RentalDeliverDto
import com.bss.carrent.databinding.RentalDetailFragmentBinding
import com.bss.carrent.misc.GeoLocationManager
import com.bss.carrent.misc.Helpers
import com.bss.carrent.ui.car.CarDetailFragmentDirections
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class RentalDetailFragment : Fragment() {
    private var _binding: RentalDetailFragmentBinding? = null
    private val args: RentalDetailFragmentArgs by navArgs()
    private var coords: LatLng? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rentalDetailViewModel =
            ViewModelProvider(this)[RentalDetailViewModel::class.java]

        _binding = RentalDetailFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var locationManager = GeoLocationManager(requireContext())
        val permissionGranted = locationManager.isLocationAllowed(requireContext())
        if (permissionGranted) {
            locationManager.startLocationTracking(locationCallback)
        } else {
            locationManager.requestLocationPermission(this)
        }

        binding.rentalDetailReservedFrom.text = Helpers.formatDateTime(args.rental.reservedFrom)
        binding.rentalDetailReservedUntil.text = Helpers.formatDateTime(args.rental.reservedUntil)
        binding.rentalDetailMileageTotal.text = args.rental.mileageTotal.toString()
        binding.rentalDetailKilometerPackage.text = args.rental.kmPackage.toString()
        binding.rentalDetailPickedUpAt.text =
            if (args.rental.pickedUpAt != null) Helpers.formatDateTime(args.rental.pickedUpAt!!) else "-"
        binding.rentalDetailDeliveredAt.text =
            if (args.rental.deliveredAt != null) Helpers.formatDateTime(args.rental.deliveredAt!!) else "-"

        binding.rentalDetailViewTenant.setOnClickListener {
            val action = RentalDetailFragmentDirections.actionNavRentalDetailsToNavUser(
                args.rental.tenantId
            )
            requireParentFragment().findNavController().navigate(action)
        }
        binding.rentalDetailViewOwner.setOnClickListener {
            val action = RentalDetailFragmentDirections.actionNavRentalDetailsToNavUser(
                args.rental.ownerId
            )
            requireParentFragment().findNavController().navigate(action)
        }

        binding.rentalDetailViewCar.setOnClickListener {
            val action = RentalDetailFragmentDirections.actionNavRentalDetailsToNavCarDetails(
                args.rental.carId
            )
            requireParentFragment().findNavController().navigate(action)
        }

        binding.rentalCancelButton.isEnabled = !args.rental.cancelled && args.rental.deliveredAt == null
        binding.rentalPickupButton.isEnabled = !args.rental.cancelled && args.rental.pickedUpAt == null
        binding.rentalDeliverCarButton.isEnabled = !args.rental.cancelled && args.rental.pickedUpAt != null && args.rental.deliveredAt == null
        binding.rentalDetailNewMileage.isEnabled = args.rental.pickedUpAt != null && args.rental.deliveredAt == null

        binding.rentalCancelButton.setOnClickListener {
            rentalDetailViewModel.cancel(requireContext())
        }

        binding.rentalDeliverCarButton.setOnClickListener {
            val mileageTotal = binding.rentalDetailNewMileageText.text?.toString()?.toLongOrNull()
            if(mileageTotal == null) {
                binding.rentalDetailNewMileage.error = "Invalid new mileage"
            }
            else {
                if(coords == null) {
                    Toast.makeText(requireContext(), "You need to allow GPS to get a location first.", Toast.LENGTH_LONG)
                }
                else lifecycleScope.launch {
                    rentalDetailViewModel.deliver(requireContext(), args.rental.id, RentalDeliverDto(
                        mileageTotal, 1.0, coords!!.latitude, coords!!.longitude))
                }
            }
        }
        binding.rentalPickupButton.setOnClickListener {
            rentalDetailViewModel.pickup(requireContext(), args.rental.id)
        }

        rentalDetailViewModel.isCancelled.observe(viewLifecycleOwner) {
            requireParentFragment().findNavController().popBackStack()
        }

        rentalDetailViewModel.pickup.observe(viewLifecycleOwner) {
            requireParentFragment().findNavController().popBackStack()
        }

        rentalDetailViewModel.invoice.observe(viewLifecycleOwner) {
            requireParentFragment().findNavController().popBackStack()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                coords = LatLng(location.latitude, location.longitude)
            }
        }
    }
}