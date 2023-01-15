package com.bss.carrent.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bss.carrent.R
import com.bss.carrent.databinding.PreferencesBinding
import com.bss.carrent.misc.PrefsHelper
import java.util.*

class PreferencesFragment : Fragment() {

    var currentLanguage = ""

    private var _binding: PreferencesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentLanguage = Locale.getDefault().language

        _binding = PreferencesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val languageTextView: TextView = binding.languageValue
        languageTextView.text = currentLanguage

        val spinner = view?.findViewById<Spinner>(R.id.spinner_language)
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.languages,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (spinner != null) {
            spinner.adapter = adapter
        }

        if (spinner != null) {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedLanguage = parent?.getItemAtPosition(position).toString()
                    if (selectedLanguage != currentLanguage) {
                        setNewLocale(requireContext(), selectedLanguage)
                        currentLanguage = selectedLanguage
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // do nothing
                }
            }
        }

        val switchAutoTheme: Switch = binding.switchAutoTheme
        val switchDarkTheme: Switch = binding.switchDarkTheme
        val prefsHelper = PrefsHelper(requireContext())

        when(prefsHelper.getTheme()) {
            "auto" -> {
                switchAutoTheme.isEnabled = true
                switchAutoTheme.isChecked = true
                switchDarkTheme.isChecked = false
                switchDarkTheme.isEnabled = false
            }
            "light" -> {
                switchAutoTheme.isChecked = false
                switchDarkTheme.isChecked = false
                switchDarkTheme.isEnabled = true
                switchAutoTheme.isEnabled = true
            }
            "dark" -> {
                switchAutoTheme.isChecked = false
                switchDarkTheme.isChecked = true
                switchDarkTheme.isEnabled = true
                switchAutoTheme.isEnabled = false
            }
        }

        switchAutoTheme.setOnCheckedChangeListener { _, value ->
            if (switchAutoTheme.isChecked) {
                prefsHelper.updateTheme("auto")
                switchDarkTheme.isEnabled = false
            } else {
                prefsHelper.updateTheme("light")
                switchDarkTheme.isEnabled = true
            }
        }

        switchDarkTheme.setOnCheckedChangeListener { _, value ->
            if (!switchAutoTheme.isChecked) {
                switchDarkTheme.isEnabled = true
                when (value) {
                    true -> prefsHelper.updateTheme("dark")
                    false -> prefsHelper.updateTheme("light")
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setNewLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}