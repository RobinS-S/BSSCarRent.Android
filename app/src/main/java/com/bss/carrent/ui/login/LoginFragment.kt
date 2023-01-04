package com.bss.carrent.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bss.carrent.R
import com.bss.carrent.api.ApiClient
import com.bss.carrent.api.PrefsHelper
import com.bss.carrent.api.UserApiService
import com.bss.carrent.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var navController: NavController
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val prefsHelper = context?.let { PrefsHelper(it) }
        if (prefsHelper != null && prefsHelper.areCredentialsFilled()) {
            binding.email.editText?.setText(prefsHelper.getUsername())
            binding.password.editText?.setText(prefsHelper.getPassword())
            Toast.makeText(context, "You already have credentials.", 5000).show()
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
            Toast.makeText(
                context,
                "Login details have been reset.",
                5000
            ).show()
            navController.navigate(R.id.nav_home)
        }
        gotoLogin.setOnClickListener {
            val prefsHelper = context?.let { PrefsHelper(it) }
            if(prefsHelper != null) {
                val originalUsername = prefsHelper.getUsername()
                val originalPassword = prefsHelper.getPassword()
                val textUsername = binding.email.editText?.text.toString()
                val textPassword = binding.password.editText?.text.toString()
                if(validateForm()) {
                    prefsHelper.update(textUsername, textPassword)
                    binding.loginButton.isClickable = false
                    lifecycleScope.launch {
                        if (isLoginWorking()) {
                            navController.navigate(R.id.nav_home)
                        }
                        else {
                            if(!originalUsername.isNullOrBlank() && !originalPassword.isNullOrBlank()) {
                                prefsHelper.update(originalUsername!!, originalPassword!!)
                            }
                            Toast.makeText(
                                context,
                                "Logging in failed. Are you sure you've got the right credentials?",
                                5000
                            ).show()
                            binding.email.error = "Invalid"
                            binding.password.error = "Invalid"
                        }
                        binding.loginButton.isClickable = true
                    }
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

    private suspend fun isLoginWorking(): Boolean {
        val apiClient = context?.let { ApiClient(it) }
        val profileApiService = apiClient?.createService(UserApiService::class.java, "users")

        val prefsHelper = context?.let { PrefsHelper(it) }
        if (prefsHelper != null && prefsHelper.areCredentialsFilled()) {
            val profile = profileApiService?.getProfile()
            if(profile != null) {
                if(profile.code() == 200) {
                    val res = profile.body()
                    Toast.makeText(context, "Logged in as ${res?.firstName} ${res?.infix ?: ""} ${res?.lastName}", 5000).show()
                    return true
                }
            }
        }
        return false
    }
}