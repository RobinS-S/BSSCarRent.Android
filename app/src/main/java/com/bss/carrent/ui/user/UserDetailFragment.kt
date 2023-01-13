package com.bss.carrent.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bss.carrent.databinding.UserDetailFragmentBinding
import com.bss.carrent.misc.Helpers
import com.bss.carrent.ui.car.CarDetailViewModel
import com.bss.userrent.ui.user.UserDetailViewModel
import kotlinx.coroutines.launch

class UserDetailFragment : Fragment() {
    private var _binding: UserDetailFragmentBinding? = null

    private val args: UserDetailFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userDetailViewModel =
            ViewModelProvider(this)[UserDetailViewModel::class.java]

        _binding = UserDetailFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        
        userDetailViewModel.userDto.observe(viewLifecycleOwner) {
            binding.firstName.text = it.firstName
            binding.infix.text = it.infix ?: "-"
            binding.lastName.text = it.lastName
            binding.phoneNumber.text =
                Helpers.getFormattedPhone(it.phoneInternationalCode, it.phoneNumber)
        }

        lifecycleScope.launch {
            userDetailViewModel.getUser(args.userId)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}