package com.bss.carrent.ui.car

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bss.carrent.databinding.CarEditFragmentBinding
import com.bss.carrent.misc.Helpers

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

        binding.carDetailConstructionyear.text?.clear()
        binding.carDetailConstructionyear.text?.append(args.car.constructed.year.toString())

        binding.carDetailPricePerHour.text?.clear()
        binding.carDetailPricePerHour.text?.append(Helpers.formatDoubleWithOptionalDecimals(args.car.pricePerHour))

        binding.carDetailPricePerKilometer.text?.clear()
        binding.carDetailPricePerKilometer.text?.append(
            Helpers.formatDoubleWithOptionalDecimals(
                args.car.pricePerKilometer
            )
        )

        binding.carDetailHireprice.text?.clear()
        binding.carDetailHireprice.text?.append(Helpers.formatDoubleWithOptionalDecimals(args.car.initialCost))

        binding.carDetailCurrentKilometers.text?.clear()
        binding.carDetailCurrentKilometers.text?.append(args.car.kilometersCurrent.toString())

        binding.carDetailApk.text?.clear()
        binding.carDetailApk.text?.append(Helpers.formatShortDate(args.car.apkUntil))

        binding.carDetailCartype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position) as String
                // TODO: update DTO member and conditionally set fuelType to null
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}