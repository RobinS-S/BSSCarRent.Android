package com.bss.carrent.ui.car

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bss.carrent.data.car.CarDto
import com.bss.carrent.databinding.CarOwnListFragmentBinding

class CarOwnListFragment : Fragment() {

    private var _binding: CarOwnListFragmentBinding? = null
    private lateinit var carListAdapter: CarListAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val carOwnViewModel =
            ViewModelProvider(this)[CarOwnViewModel::class.java]

        _binding = CarOwnListFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(context)
        binding.carListRecyclerView.layoutManager = layoutManager

        val carListSwipeRefresh: SwipeRefreshLayout = binding.carListSwipeRefresh
        carListSwipeRefresh.isRefreshing = true

        carListAdapter = CarListAdapter()
        carListAdapter.setOnItemClickListener(object : CarListAdapter.OnItemClickListener {
            override fun onItemClick(carDto: CarDto) {
                var action = CarOwnListFragmentDirections.actionNavOwnCarListToNavCarEdit(carDto)
                requireParentFragment().findNavController().navigate(action)
            }
        })

        binding.carListRecyclerView.adapter = carListAdapter

        carOwnViewModel.carDtoList.observe(viewLifecycleOwner) { carList ->
            carList?.let {
                carListAdapter.setCarList(it)
                carListAdapter.notifyDataSetChanged()
                binding.carListSwipeRefresh.isRefreshing = false
            }
        }

        carListSwipeRefresh.setOnRefreshListener {
            carOwnViewModel.getMyCars(requireContext())
        }

        carOwnViewModel.getMyCars(requireContext())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}