package com.bss.carrent.ui.car

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CarImageSliderAdapter(fragment: Fragment, private val imageUrls: List<String>) :
    FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return CarImageSliderFragment.newInstance(imageUrls[position])
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }
}