package com.bss.carrent.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bss.carrent.databinding.UserDetailFragmentBinding
import com.bss.carrent.misc.Helpers

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

        _binding = UserDetailFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.firstName.text = args.user.firstName
        binding.infix.text = args.user.infix
        binding.lastName.text = args.user.lastName
        binding.phoneNumber.text =
            Helpers.getFormattedPhone(args.user.phoneInternationalCode, args.user.phoneNumber)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}