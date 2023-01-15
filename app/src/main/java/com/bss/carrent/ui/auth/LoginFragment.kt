package com.bss.carrent.ui.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bss.carrent.R
import com.bss.carrent.databinding.LoginFragmentBinding
import com.bss.carrent.misc.AuthHelper
import com.bss.carrent.misc.Helpers

class LoginFragment : Fragment() {
    private lateinit var navController: NavController
    private var _binding: LoginFragmentBinding? = null
    private lateinit var viewModel: LoginViewModel
    private var _busy = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]

        val authHelper = context?.let { AuthHelper(it) }
        if (authHelper != null && authHelper.areCredentialsFilled()) {
            binding.email.editText?.setText(authHelper.getUsername())
            binding.password.editText?.setText(authHelper.getPassword())
            Toast.makeText(context, "You already have credentials.", Toast.LENGTH_LONG).show()
        }

        viewModel.userDto.observe(viewLifecycleOwner) { user ->
            if (user != null && _busy) {
                Toast.makeText(
                    context,
                    "Logged in as ${Helpers.getFormattedName(user)}",
                    Toast.LENGTH_LONG
                ).show()
                navController.navigate(R.id.nav_car_list)
                _busy = false
            }

            if (user != null) {
                binding.loginButton.isVisible = false
                binding.resetLoginDetailsButton.isVisible = true
            } else {
                binding.resetLoginDetailsButton.isVisible = false
            }
        }

        viewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                Toast.makeText(
                    context,
                    "Logging in failed. Are you sure you've got the right credentials?",
                    Toast.LENGTH_LONG
                ).show()
                binding.email.error = "Invalid"
                binding.password.error = "Invalid"
                binding.loginButton.isClickable = true
            } else {
                binding.email.error = null
                binding.password.error = null
                binding.loginButton.isClickable = false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val gotoLogin: Button = binding.loginButton
        val resetLogin: Button = binding.resetLoginDetailsButton

        resetLogin.setOnClickListener {
            val authHelper = context?.let { AuthHelper(it) }
            authHelper?.reset()
            viewModel.setUser(null)
            binding.email.editText?.text?.clear()
            binding.password.editText?.text?.clear()
            binding.loginButton.isClickable = true

            Toast.makeText(
                context,
                "You have logged out.",
                Toast.LENGTH_LONG
            ).show()
            binding.resetLoginDetailsButton.isVisible = false
            binding.loginButton.isVisible = true
        }

        gotoLogin.setOnClickListener {
            val authHelper = context?.let { AuthHelper(it) }
            if (authHelper != null) {
                val textUsername = binding.email.editText?.text.toString()
                val textPassword = binding.password.editText?.text.toString()
                if (validateForm()) {
                    authHelper.update(textUsername, textPassword)
                    binding.loginButton.isClickable = false
                    _busy = true
                    viewModel.tryLogin(requireContext())
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateForm(): Boolean {
        var isValid = true

        val email = binding.email.editText?.text.toString().trim()
        if (email.isEmpty()) {
            binding.email.error = "Email is required"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.error = "Invalid email"
            isValid = false
        } else {
            binding.email.error = null
        }

        val password = binding.password.editText?.text.toString().trim()
        if (password.isEmpty()) {
            binding.password.error = "Password is required"
            isValid = false
        } else {
            binding.password.error = null
        }

        return isValid
    }
}