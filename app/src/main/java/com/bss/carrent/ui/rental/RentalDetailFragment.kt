package com.bss.carrent.ui.rental

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bss.carrent.databinding.RentalDetailFragmentBinding
import com.bss.carrent.misc.Helpers
import com.bss.carrent.ui.car.CarDetailFragmentDirections

class RentalDetailFragment : Fragment() {
    private var _binding: RentalDetailFragmentBinding? = null
    private val args: RentalDetailFragmentArgs by navArgs()

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

        binding.rentalCancelButton.setOnClickListener {
            rentalDetailViewModel.cancel(requireContext())
        }
        binding.rentalDeliverCarButton.setOnClickListener {
            rentalDetailViewModel.deliver(requireContext(), args.rental.id)
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
            val action = RentalDetailFragmentDirections.actionNavRentalDetailsToNavInvoices()
            requireParentFragment().findNavController().navigate(action)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}