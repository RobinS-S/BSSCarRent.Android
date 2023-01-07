package com.bss.carrent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bss.carrent.data.Car
import com.bss.carrent.databinding.FragmentCarDetailBinding

class CarDetailFragment : Fragment() {
    private var _binding: FragmentCarDetailBinding? = null
    private lateinit var car: Car

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        car = arguments?.getSerializable("car") as Car

        val carDetailViewModel =
            ViewModelProvider(this)[CarDetailViewModel::class.java]

        _binding = FragmentCarDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val carModelName: TextView = binding.carDetailModelName
        carDetailViewModel.text.observe(viewLifecycleOwner) {
            carModelName.text = it
        }

        carDetailViewModel.setText(car.brand)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}