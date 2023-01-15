package com.bss.carrent.ui.car

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bss.carrent.data.car.CarUpdateDto
import com.bss.carrent.databinding.CarEditFragmentBinding
import com.bss.carrent.misc.Helpers
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class CarEditFragment : Fragment() {
    private var _binding: com.bss.carrent.databinding.CarEditFragmentBinding? = null
    private val args: CarEditFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val carEditViewModel =
            ViewModelProvider(this)[CarEditViewModel::class.java]

        _binding = CarEditFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.carDetailBrandNameEdit.text?.clear()
        binding.carDetailBrandNameEdit.text?.append(args.car.brand)

        binding.carDetailModelName.text?.clear()
        binding.carDetailModelName.text?.append(args.car.model)

        binding.carDetailColorName.text?.clear()
        binding.carDetailColorName.text?.append(args.car.color)

        binding.carDetailPricePerHour.text?.clear()
        binding.carDetailPricePerHour.text?.append(Helpers.formatDoubleWithOptionalDecimals(args.car.pricePerHour))

        binding.carDetailPricePerKilometer.text?.clear()
        binding.carDetailPricePerKilometer.text?.append(
            Helpers.formatDoubleWithOptionalDecimals(
                args.car.pricePerKilometer
            )
        )

        binding.carDetailCurrentKilometers.text?.clear()
        binding.carDetailCurrentKilometers.text?.append(args.car.kilometersCurrent.toString())

        binding.carDetailInitialprice.text?.clear()
        binding.carDetailInitialprice.text?.append(args.car.initialCost.toString())

        binding.carDetailApk.text?.clear()
        binding.carDetailApk.text?.append(Helpers.formatShortDate(args.car.apkUntil))

        carEditViewModel.updatedCar.observe(viewLifecycleOwner) {
            carDto -> if(carDto != null) {
                requireParentFragment().findNavController().popBackStack()
            }
        }

        binding.carSaveEditsButton.setOnClickListener {
            val updateDto = validateFields()
            if(updateDto != null) {
                carEditViewModel.updateCar(requireContext(), args.car.id, updateDto)
            }
        }

        return root
    }

    private fun validateFields(): CarUpdateDto? {
        var validated = true
        val brand = binding.carDetailBrandNameEdit.text
        val model = binding.carDetailModelName.text
        val color = binding.carDetailColorName.text
        val kilometersCurrent = binding.carDetailCurrentKilometers.text
        val pricePerKilometer = binding.carDetailPricePerKilometer.text
        val pricePerHour = binding.carDetailPricePerHour.text
        val licensePlate = binding.carDetailLicenseplate.text
        val apkUntil = binding.carDetailApk.text
        val initialCost = binding.carDetailInitialprice.text
        if(brand == null || brand.isEmpty()) {
            binding.carDetailBrandNameEdit.error = "Brand name cannot be empty"
            validated = false
        } else binding.carDetailBrandNameEdit.error = null

        if(model == null || model.isEmpty()) {
            binding.carDetailModelName.error = "Model name cannot be empty"
            validated = false
        } else binding.carDetailModelName.error = null

        if(color == null || color.isEmpty()) {
            binding.carDetailColorName.error = "Color name cannot be empty"
            validated = false
        } else binding.carDetailColorName.error = null

        if(kilometersCurrent == null || kilometersCurrent.isEmpty()) {
            binding.carDetailCurrentKilometers.error = "Current kilometers cannot be empty"
            validated = false
        } else {
            if(kilometersCurrent.toString().toDoubleOrNull() == null && kilometersCurrent.toString().toIntOrNull() == null) {
                binding.carDetailCurrentKilometers.error = "Current kilometers must be a valid number"
                validated = false
            } else binding.carDetailCurrentKilometers.error = null
        }

        if(pricePerKilometer == null || pricePerKilometer.isEmpty() == null) {
            binding.carDetailPricePerKilometer.error = "Price per kilometer must be a valid number"
            validated = false
        } else {
            if(pricePerKilometer.toString().toDoubleOrNull() == null && pricePerKilometer.toString().toIntOrNull() == null) {
                binding.carDetailPricePerKilometer.error = "Price per kilometer must be a valid number"
                validated = false
            }
            else binding.carDetailPricePerKilometer.error = null
        }

        if(pricePerHour == null || pricePerHour.isEmpty()) {
            binding.carDetailPricePerHour.error = "Price per hour cannot be empty"
            validated = false
        } else {
            if(pricePerHour.toString().toDoubleOrNull() == null && pricePerHour.toString().toIntOrNull() == null) {
                binding.carDetailPricePerHour.error = "Price per hour must be a valid number"
                validated = false
            } else binding.carDetailPricePerHour.error = null
        }

        if(licensePlate == null || licensePlate.isEmpty()) {
            binding.carDetailLicenseplate.error = "License plate cannot be empty"
            validated = false
        } else binding.carDetailLicenseplate.error = null

        if(apkUntil == null || apkUntil.isEmpty()) {
            binding.carDetailApk.error = "APK cannot be empty"
            validated = false
        } else {
            if(Helpers.parseShortDate(binding.carDetailApk.text.toString()) != null) binding.carDetailApk.error = null
            else binding.carDetailApk.error = "Invalid date, dd-mm-yyyy"
        }

        if(initialCost == null || initialCost.isEmpty()) {
            binding.carDetailInitialprice.error = "Initial cost cannot be empty"
            validated = false
        } else {
            if(initialCost.toString().toDoubleOrNull() == null && initialCost.toString().toIntOrNull() == null) {
                binding.carDetailInitialprice.error = "Initial cost must be a valid number"
                validated = false
            } else binding.carDetailInitialprice.error = null
        }
        if(!validated) return null

        return CarUpdateDto(brand.toString(), model.toString(), color.toString(), kilometersCurrent.toString().toLong(), pricePerKilometer.toString().toDouble(), pricePerHour.toString().toDouble(), licensePlate.toString(), Helpers.parseShortDate(apkUntil.toString())!!, initialCost.toString().toDouble())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}