package com.bss.carrent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bss.carrent.R
import com.bss.carrent.data.Car
import com.bss.carrent.data.CarType
import com.bss.carrent.data.CombustionFuelType
import com.bss.carrent.databinding.FragmentHomeBinding
import java.time.LocalDate

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var carAdapter: CarAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(context)
        binding.carListRecyclerView.layoutManager = layoutManager

        carAdapter = CarAdapter()
        carAdapter.setOnItemClickListener(object: CarAdapter.OnItemClickListener {
            override fun onItemClick(car: Car) {
                val fragmentTransaction = parentFragmentManager.beginTransaction()

                val carDetailFragment = CarDetailFragment()
                val args = Bundle()
                args.putSerializable("car", car)
                carDetailFragment.arguments = args

                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, carDetailFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        })

        binding.carListRecyclerView.adapter = carAdapter

        homeViewModel.carList.observe(viewLifecycleOwner) { carList ->
            carList?.let {
                carAdapter.setCarList(it)
            }
        }

        homeViewModel.carList.value = listOf(
            Car(1, 1, "Toyota", "Camry", "Blue", 100000, 25.0, 0.25, "ABC-123", LocalDate.now(), LocalDate.now().plusYears(1), CarType.COMBUSTION, CombustionFuelType.GASOLINE, 10000.0, 51.5, 0.1),
            Car(2, 1, "Honda", "Civic", "Red", 80000, 20.0, 0.20, "DEF-456", LocalDate.now(), LocalDate.now().plusYears(1), CarType.COMBUSTION, CombustionFuelType.GASOLINE, 8000.0, 51.5, 0.1),
            Car(3, 2, "Tesla", "Model 3", "White", 60000, 30.0, 0.30, "GHI-789", LocalDate.now(), LocalDate.now().plusYears(1), CarType.BATTERY_ELECTRIC, null, 30000.0, 51.5, 0.1)
        )

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}