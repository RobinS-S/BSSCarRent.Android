package com.bss.carrent.ui.rental

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bss.carrent.R
import com.bss.carrent.data.car.CarDto
import com.bss.carrent.data.rental.RentalDto
import com.bss.carrent.databinding.RentalListFragmentBinding
import com.bss.carrent.ui.car.CarListAdapter
import com.bss.carrent.ui.car.CarListFragmentDirections
import kotlinx.coroutines.launch

class RentalListFragment : Fragment() {
    private lateinit var navController: NavController
    private var _binding: RentalListFragmentBinding? = null
    private lateinit var viewModel: RentalListViewModel
    private val binding get() = _binding!!
    private lateinit var rentalListAdapter: RentalListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RentalListFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[RentalListViewModel::class.java]
        val root: View = binding.root

        val rentalListSwipeRefresh: SwipeRefreshLayout = binding.rentalListSwipeRefresh
        rentalListSwipeRefresh.isRefreshing = true

        val layoutManager = LinearLayoutManager(context)
        binding.rentalListRecyclerView.layoutManager = layoutManager

        rentalListAdapter = RentalListAdapter()
        rentalListAdapter.setOnItemClickListener(object : RentalListAdapter.OnItemClickListener {
            override fun onItemClick(rentalDto: RentalDto) {
                var action = RentalListFragmentDirections.actionNavRentalsToNavRentalDetails(rentalDto)
                requireParentFragment().findNavController().navigate(action)
            }
        })

        binding.rentalListRecyclerView.adapter = rentalListAdapter

        viewModel.rentalDtoList.observe(viewLifecycleOwner) { rentalList ->
            rentalList?.let {
                rentalListAdapter.setRentalList(it)
                rentalListAdapter.notifyDataSetChanged()
                binding.rentalListSwipeRefresh.isRefreshing = false
            }
        }

        binding.rentalListRadiogroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rental_list_radio_button_mine -> {
                    viewModel.getRentals(requireContext(), "mine")
                }
                R.id.rental_list_radio_button_owned -> {
                    viewModel.getRentals(requireContext(), "owned")
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getRentals(requireContext(), "mine")
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}