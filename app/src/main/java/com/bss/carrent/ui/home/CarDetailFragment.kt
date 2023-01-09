package com.bss.carrent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.bss.carrent.databinding.FragmentCarDetailBinding
import com.bss.carrent.misc.Helpers
import com.bss.carrent.viewmodel.CarDetailViewModel

class CarDetailFragment : Fragment() {
    private var _binding: FragmentCarDetailBinding? = null
    private var carId: Long = -1

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        carId = arguments?.getSerializable("carId") as Long

        val carDetailViewModel =
            ViewModelProvider(this)[CarDetailViewModel::class.java]

        _binding = FragmentCarDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val carModelName: TextView = binding.carDetailModelName
        val carDetailSwipeRefresh: SwipeRefreshLayout = binding.carDetailSwipeRefresh
        val carDetailImagesViewpager: ViewPager2 = binding.carDetailImagesViewpager
        val carDetailCarOwner: TextView = binding.carDetailCarOwner
        val carDetailOwnerButton: Button = binding.carDetailOwnerButton
        val carDetailBrandName: TextView = binding.carDetailBrandName
        val carDetailApk: TextView = binding.carDetailApk
        val carDetailCarType: TextView = binding.carDetailCartype
        val carDetailYear: TextView = binding.carDetailConstructionyear
        val carDetailFuelTypeLabel: TextView = binding.carDetailFueltypeLabel
        val carDetailFuelType: TextView = binding.carDetailFueltype
        val carDetailPricePerHour: TextView = binding.carDetailPricePerHour
        val carDetailPricePerKilometer: TextView = binding.carDetailPricePerKilometer
        val carDetailHireprice: TextView = binding.carDetailHireprice
        val carDetailCurrentKilometers: TextView = binding.carDetailCurrentKilometers
        val carDetailPriceTotalLabel: TextView = binding.carDetailPriceTotalLabel
        val carDetailPriceTotal: TextView = binding.carDetailPriceTotal
        val carDetailButtonViewRentalOptions: Button = binding.carDetailButtonViewRentalOptions
        val carKmSeekBar: SeekBar = binding.carDetailKmSlider
        carDetailSwipeRefresh.isRefreshing = true

        carDetailSwipeRefresh.isEnabled = false
        carKmSeekBar.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> carDetailSwipeRefresh.isEnabled = true
            }
            false
        }

        carDetailViewModel.carDto.observe(viewLifecycleOwner) {
            carDetailBrandName.text = it.brand
            carModelName.text = it.model
            //carColor????
            carDetailCurrentKilometers.text = it.kilometersCurrent.toString()
            carDetailPricePerHour.text = Helpers.formatDoubleWithOptionalDecimals(it.pricePerHour)
            carDetailPricePerKilometer.text = Helpers.formatDoubleWithOptionalDecimals(it.pricePerKilometer)
            //licenseplate??
            carDetailYear.text = it.constructed.year.toString()
            carDetailCarType.text = it.carType.toString()
            carDetailApk.text = Helpers.formatShortDate(it.apkUntil)
            carDetailFuelTypeLabel.isVisible = it.fuelType != null
            carDetailFuelType.text = it.fuelType.toString()
            carDetailFuelType.isVisible = it.fuelType != null
            carDetailHireprice.text = Helpers.formatDoubleWithOptionalDecimals(it.initialCost)
            carDetailSwipeRefresh.isRefreshing = false
        }
        carDetailViewModel.userDto.observe(viewLifecycleOwner) {
            carDetailCarOwner.text = Helpers.getFormattedName(it)
        }

        carDetailSwipeRefresh.setOnRefreshListener {
            carDetailViewModel.getCar(requireContext(), carId)
        }
        carDetailViewModel.getCar(requireContext(), carId)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}