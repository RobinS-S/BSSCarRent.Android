package com.bss.carrent.ui.rental

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bss.carrent.databinding.RentalCreateFragmentBinding
import com.bss.carrent.misc.Helpers
import java.time.LocalDate
import java.util.*

class RentalCreateFragment : Fragment() {
    private val args: RentalCreateFragmentArgs by navArgs()
    private lateinit var navController: NavController
    private var _binding: RentalCreateFragmentBinding? = null
    private lateinit var viewModel: RentalCreateViewModel
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(requireActivity())[RentalCreateViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = RentalCreateFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rentalCreateKmPrice.text =
            Helpers.formatDoubleWithOptionalDecimals(args.car.pricePerKilometer)
        binding.rentalInitialCost.text =
            Helpers.formatDoubleWithOptionalDecimals(args.car.initialCost)
        binding.editTextLayoutKmPackage.doOnTextChanged { text, start, count, after ->
            if (!text.isNullOrBlank()) {
                val num = binding.editTextLayoutKmPackage.text.toString().toInt()
                binding.rentalCreateCalculatedKmPrice.text =
                    Helpers.formatDoubleWithOptionalDecimals(num * args.car.pricePerKilometer)
            }
        }

        binding.rentalCreateChangeStartDateButton.setOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    val selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
                    binding.rentalCreateStartDate.text = Helpers.formatShortDate(selectedDate)
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.maxDate = cal.timeInMillis
            datePickerDialog.show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}