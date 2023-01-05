package com.bss.carrent.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bss.carrent.R
import com.bss.carrent.api.PrefsHelper
import com.bss.carrent.databinding.FragmentLoginBinding
import com.bss.carrent.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var navController: NavController
    private var _binding: FragmentLoginBinding? = null
    private lateinit var viewModel: LoginViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]

        val prefsHelper = context?.let { PrefsHelper(it) }
        if (prefsHelper != null && prefsHelper.areCredentialsFilled()) {
            binding.email.editText?.setText(prefsHelper.getUsername())
            binding.password.editText?.setText(prefsHelper.getPassword())
            Toast.makeText(context, "You already have credentials.", 5000).show()
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            if(user != null) {
                binding.loginButton.isClickable = true
                Toast.makeText(
                    context,
                    "Logged in as ${user.firstName} ${user.infix  ?: ""} ${user.lastName}",
                    5000
                ).show()
                navController.navigate(R.id.nav_home)
            }
        }

        viewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                Toast.makeText(
                    context,
                    "Logging in failed. Are you sure you've got the right credentials?",
                    5000
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

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val gotoLogin: Button = binding.loginButton
        val resetLogin: Button = binding.resetLoginDetailsButton

        resetLogin.setOnClickListener {
            val prefsHelper = context?.let { PrefsHelper(it) }
            prefsHelper?.reset()
            binding.email.editText?.text?.clear()
            binding.password.editText?.text?.clear()
            binding.loginButton.isClickable = true

            Toast.makeText(
                context,
                "Login details have been reset.",
                5000
            ).show()
        }

        gotoLogin.setOnClickListener {
            val prefsHelper = context?.let { PrefsHelper(it) }
            if(prefsHelper != null) {
                val textUsername = binding.email.editText?.text.toString()
                val textPassword = binding.password.editText?.text.toString()
                if(validateForm()) {
                    prefsHelper.update(textUsername, textPassword)
                    binding.loginButton.isClickable = false
                    viewModel.tryLogin(context!!)
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        viewModel.setIsError(false)
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