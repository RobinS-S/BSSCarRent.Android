package com.bss.carrent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bss.carrent.databinding.FragmentCarImageSliderBinding
import com.bumptech.glide.Glide

class CarImageSliderFragment : Fragment() {

    private lateinit var binding: FragmentCarImageSliderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarImageSliderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUrl = requireArguments().getString("image_url")
        Glide.with(view)
            .load(imageUrl)
            .into(binding.imageView)
    }

    companion object {
        fun newInstance(imageUrl: String): CarImageSliderFragment {
            val fragment = CarImageSliderFragment()
            val args = Bundle()
            args.putString("image_url", imageUrl)
            fragment.arguments = args
            return fragment
        }
    }
}