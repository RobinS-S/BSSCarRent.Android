package com.bss.carrent.ui.car

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bss.carrent.data.car.CarDto
import com.bss.carrent.databinding.CarListFragmentBinding
import kotlinx.coroutines.launch

class CarListFragment : Fragment() {

    private var _binding: CarListFragmentBinding? = null
    private lateinit var carListAdapter: CarListAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val carListViewModel =
            ViewModelProvider(this)[CarListViewModel::class.java]

        _binding = CarListFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(context)
        binding.carListRecyclerView.layoutManager = layoutManager

        val carListSwipeRefresh: SwipeRefreshLayout = binding.carListSwipeRefresh
        carListSwipeRefresh.isRefreshing = true

        carListAdapter = CarListAdapter()
        carListAdapter.setOnItemClickListener(object : CarListAdapter.OnItemClickListener {
            override fun onItemClick(carDto: CarDto) {
                var action = CarListFragmentDirections.actionNavCarListToNavCarDetails(carDto.id)
                requireParentFragment().findNavController().navigate(action)
            }
        })

        binding.carListRecyclerView.adapter = carListAdapter

        carListViewModel.carDtoList.observe(viewLifecycleOwner) { carList ->
            carList?.let {
                carListAdapter.setCarList(it)
                carListAdapter.notifyDataSetChanged()
                binding.carListSwipeRefresh.isRefreshing = false
            }
        }

        carListSwipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                carListViewModel.getCars(requireContext())
            }
        }

        lifecycleScope.launch {
            carListViewModel.getCars(requireContext())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}