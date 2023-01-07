package com.bss.carrent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
            ViewModelProvider(this)[HomeViewModel::class.java]

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
                carAdapter.notifyDataSetChanged()
                binding.carListSwipeRefresh.isRefreshing = false
            }
        }

        binding.carListSwipeRefresh.setOnRefreshListener {
            homeViewModel.getCars(requireContext())
        }

        homeViewModel.getCars(requireContext())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}