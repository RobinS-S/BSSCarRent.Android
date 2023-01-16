package com.bss.carrent.ui.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bss.carrent.data.user.UserRegisterDto
import com.bss.carrent.databinding.RegisterFragmentBinding
import com.bss.carrent.misc.Helpers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private lateinit var navController: NavController
    private var _binding: RegisterFragmentBinding? = null
    private lateinit var viewModel: RegisterViewModel
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]
        viewModel.registered.observe(viewLifecycleOwner) { user ->
            requireParentFragment().findNavController().popBackStack()
            Toast.makeText(requireContext(), "You successfully registered, now log in!", Toast.LENGTH_LONG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.registerButton.setOnClickListener {
            var validated = true
            val email = binding.email.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailLayout.error = "Invalid email address"
                validated = false
            } else {
                binding.emailLayout.error = null
            }

            val password = binding.password.text.toString()
            if (password.length < 6) {
                binding.passwordLayout.error = "Password must be at least 6 characters"
                validated = false
            } else {
                binding.passwordLayout.error = null
            }

            val firstName = binding.firstName.text.toString()
            if (firstName.isEmpty()) {
                binding.firstNameLayout.error = "First name is required"
                validated = false
            } else {
                binding.firstNameLayout.error = null
            }

            val infix = binding.infix.text.toString()
            if (infix.isEmpty()) {
                binding.infixLayout.error = "Infix is required"
                validated = false
            } else {
                binding.infixLayout.error = null
            }

            val lastName = binding.lastName.text.toString()
            if (lastName.isEmpty()) {
                binding.lastNameLayout.error = "Last name is required"
                validated = false
            } else {
                binding.lastNameLayout.error = null
            }

            val intCode = binding.phoneInternationalCode.text.toString()
            if (intCode.isEmpty()) {
                binding.phoneInternationalCodeLayout.error = "Int code is required (+31)"
                validated = false
            } else {
                binding.phoneInternationalCodeLayout.error = null
            }

            val phone = binding.phoneNumber.text.toString()
            if (phone.isEmpty()) {
                binding.phoneNumberLayout.error = "Phone is required (612345678)"
                validated = false
            } else {
                binding.phoneNumberLayout.error = null
            }

            val birthdate = binding.birthDate.text.toString()
            if (birthdate.isEmpty() || Helpers.parseShortDate(birthdate) == null) {
                binding.birthDateLayout.error = "Date is invalid (01-12-2022)"
                validated = false
            } else {
                binding.birthDateLayout.error = null
            }

            if(validated) viewModel.register(UserRegisterDto(email, password, firstName, infix, lastName, intCode, phone, Helpers.parseShortDate(birthdate)!!), requireContext())
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}