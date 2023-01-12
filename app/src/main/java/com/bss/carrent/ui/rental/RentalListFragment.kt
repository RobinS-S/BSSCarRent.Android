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
import com.bss.carrent.databinding.RentalListFragmentBinding
import kotlinx.coroutines.launch

class RentalListFragment : Fragment() {
    private lateinit var navController: NavController
    private var _binding: RentalListFragmentBinding? = null
    private lateinit var viewModel: RentalListViewModel
    private val binding get() = _binding!!

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