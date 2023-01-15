package com.bss.carrent.ui.user

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.bss.carrent.R
import com.bss.carrent.databinding.PreferencesBinding
import java.util.*

class PreferencesFragment : Fragment() {

    var currentLanguage = ""

    private var _binding: PreferencesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentLanguage = Locale.getDefault().language

//        val view = inflater.inflate(R.layout.preferences, container, false)
        _binding = PreferencesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val languageTextView: TextView = binding.languageValue
        languageTextView.text = currentLanguage

        val spinner = view?.findViewById<Spinner>(R.id.spinner_language)
        // set up adapter for the spinner
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
                    // get the selected language
                    val selectedLanguage = parent?.getItemAtPosition(position).toString()
                    // check if the selected language is different from the current language
                    if (selectedLanguage != currentLanguage) {
                        // set the new locale
                        setNewLocale(requireContext(), selectedLanguage)
                        // update the current language
                        currentLanguage = selectedLanguage
                        // refresh the activity
                        //activity?.recreate()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // do nothing
                }
            }
        }

        val switchAutoTheme: Switch = binding.switchAutoTheme
        val switchDarkTheme = binding.switchDarkTheme


        switchAutoTheme.setOnClickListener() {
            switchDarkTheme.isEnabled = !switchAutoTheme.isChecked

            if (switchDarkTheme.isEnabled) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchDarkTheme.isChecked = false
            }
        }

        switchDarkTheme.setOnCheckedChangeListener { _, value ->
            if (!switchAutoTheme.isChecked) {
                switchDarkTheme.isClickable = true
                when (value) {
                    true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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