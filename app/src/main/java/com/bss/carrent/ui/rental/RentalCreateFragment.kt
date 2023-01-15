package com.bss.carrent.ui.rental

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bss.carrent.data.rental.RentalPeriodDto
import com.bss.carrent.databinding.RentalCreateFragmentBinding
import com.bss.carrent.misc.Helpers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
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

        val layoutManager = LinearLayoutManager(context)
        binding.rentalCreateTimeslotsView.layoutManager = layoutManager

        viewModel.hoursCost.observe(viewLifecycleOwner) {
            binding.rentalCreateHourPriceCalculated.text = Helpers.formatCurrency(it)
        }

        val adapter = RentalCreateSlotAdapter()
        binding.rentalCreateTimeslotsView.adapter = adapter

        viewModel.usedTimeSlots.observe(viewLifecycleOwner) { usedTimeSlots ->
            adapter.setSlotList(usedTimeSlots)
            adapter.notifyDataSetChanged()
        }

        lifecycleScope.launch {
            viewModel.getUsedSlots(requireContext(), args.car.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = RentalCreateFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rentalCreateKmPrice.text =
            Helpers.formatCurrency(args.car.pricePerKilometer)
        binding.rentalInitialCost.text =
            Helpers.formatCurrency(args.car.initialCost)
        binding.editTextLayoutKmPackage.doOnTextChanged { text, start, count, after ->
            if (!text.isNullOrBlank() && text.length < 7) {
                val num = binding.editTextLayoutKmPackage.text.toString().toInt()
                binding.rentalCreateCalculatedKmPrice.text =
                    Helpers.formatCurrency(num * args.car.pricePerKilometer)
            } else {
                binding.rentalCreateCalculatedKmPrice.text = "-"
            }
        }

        // fix your SHIT DUDE
        // From date/time
        binding.rentalCreateChangeStartDateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month+1, dayOfMonth)
                binding.rentalCreateStartDate.text = Helpers.formatShortDate(selectedDate)
                viewModel.setReservedFromDate(selectedDate)
                viewModel.calculateHoursCost(args.car.pricePerHour)
                updateAvailability()
            }
            val datePickerDialog = DatePickerDialog(requireContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        binding.rentalCreateChangeStartTimeButton.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                val selectedTime = LocalTime.of(hour, minute)
                binding.rentalCreateStartTime.text = selectedTime.toString()
                viewModel.setReservedFromTime(selectedTime)
                viewModel.calculateHoursCost(args.car.pricePerHour)
                updateAvailability()
            }
            val now = LocalDateTime.now()
            val timePickerDialog = TimePickerDialog(requireContext(), timeSetListener, now.hour, now.minute, true)
            timePickerDialog.show()
        }
        // Until date/time
        binding.rentalCreateChangeEndDateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month+1, dayOfMonth)
                binding.rentalCreateEndDate.text = Helpers.formatShortDate(selectedDate)
                viewModel.setReservedUntilDate(selectedDate)
                viewModel.calculateHoursCost(args.car.pricePerHour)
                updateAvailability()
            }
            val datePickerDialog = DatePickerDialog(requireContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        binding.rentalCreateChangeEndTimeButton.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                val selectedTime = LocalTime.of(hour, minute)
                binding.rentalCreateEndTime.text = selectedTime.toString()
                viewModel.setReservedUntilTime(selectedTime)
                viewModel.calculateHoursCost(args.car.pricePerHour)
                updateAvailability()
            }
            val now = LocalDateTime.now()
            val timePickerDialog =
                TimePickerDialog(requireContext(), timeSetListener, now.hour, now.minute, true)
            timePickerDialog.show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun updateAvailability() {
        val available: Boolean = viewModel.isCurrentTimeDateAvailable() ?: return
        if (available) {
            binding.rentalCreateUnavailableText.visibility = GONE
            binding.rentalCreateUnavailableDatetime.visibility = GONE
            binding.rentalCreateUnavailableText3.visibility = GONE
            binding.rentalCreateAvailable.visibility = VISIBLE
        } else {
            binding.rentalCreateAvailable.visibility = GONE
            binding.rentalCreateUnavailableDatetime.text = "${Helpers.formatDateTime(viewModel.getFullFromDateTime()!!)} - ${Helpers.formatDateTime(viewModel.getFullUntilDateTime()!!)}"
            binding.rentalCreateUnavailableText.visibility = VISIBLE
            binding.rentalCreateUnavailableDatetime.visibility = VISIBLE
            binding.rentalCreateUnavailableText3.visibility = VISIBLE
        }
    }
}