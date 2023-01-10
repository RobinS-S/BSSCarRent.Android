package com.bss.carrent.ui.car

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.bss.carrent.R
import com.bss.carrent.api.CarApi
import com.bss.carrent.databinding.CarDetailFragmentBinding
import com.bss.carrent.misc.Helpers
import com.bss.carrent.ui.rental.RentalCreateFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CarDetailFragment : Fragment() {
    private var _binding: CarDetailFragmentBinding? = null
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

        _binding = CarDetailFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val carModelName: TextView = binding.carDetailModelName
        val carColorName: TextView = binding.carDetailColorName
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
        val carDetailButtonViewRentalOptions: Button = binding.carDetailButtonViewRentalOptions
        carDetailSwipeRefresh.isRefreshing = true

        carDetailViewModel.carDto.observe(viewLifecycleOwner) {
            carDetailBrandName.text = it.brand
            carModelName.text = it.model
            carColorName.text = it.color
            carDetailCurrentKilometers.text = it.kilometersCurrent.toString()
            carDetailPricePerHour.text = Helpers.formatDoubleWithOptionalDecimals(it.pricePerHour)
            carDetailPricePerKilometer.text =
                Helpers.formatDoubleWithOptionalDecimals(it.pricePerKilometer)
            carDetailYear.text = it.constructed.year.toString()
            carDetailCarType.setText(Helpers.getCarTypeName(it.carType))
            carDetailApk.text = Helpers.formatShortDate(it.apkUntil)
            carDetailFuelTypeLabel.isVisible = it.fuelType != null
            if (it.fuelType != null) {
                carDetailFuelType.setText(Helpers.getCombustionFuelTypeName(it.fuelType))
            }
            carDetailFuelType.isVisible = it.fuelType != null
            carDetailHireprice.text = Helpers.formatDoubleWithOptionalDecimals(it.initialCost)

            if (it.imageIds != null && it.imageIds.isNotEmpty()) {
                val tabLayout: TabLayout = binding.tabLayout
                val imageUrls = it.imageIds.map { img -> CarApi.generateImageUrl(it.id, img) }

                carDetailImagesViewpager.adapter = CarImageSliderAdapter(this, imageUrls)
                TabLayoutMediator(tabLayout, carDetailImagesViewpager) { _, _ -> }.attach()
            }

            carDetailSwipeRefresh.isRefreshing = false
        }
        carDetailViewModel.userDto.observe(viewLifecycleOwner) {
            carDetailCarOwner.text = Helpers.getFormattedName(it)
        }

        carDetailSwipeRefresh.setOnRefreshListener {
            carDetailViewModel.getCar(requireContext(), carId)
        }
        carDetailViewModel.getCar(requireContext(), carId)

        carDetailButtonViewRentalOptions.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()

            val rentalCreateFragment = RentalCreateFragment()
            val args = Bundle()
            args.putSerializable("car", carDetailViewModel.carDto.value)
            rentalCreateFragment.arguments = args

            fragmentTransaction.replace(R.id.nav_host_fragment_content_main, rentalCreateFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}