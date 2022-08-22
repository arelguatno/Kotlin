package com.example.budgetbuddy.screens.settings_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : MainFragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
        initViewModelObserver()
        viewModel.setCurrency(numberFormat.getSavedCurrency())
    }

    private fun initViewModelObserver() {
        viewModel.getCurrency().observe(viewLifecycleOwner){
            binding.settingsDisplay.txtCurrency.text = it.textIcon.substring(0,3)
        }
    }

    private fun initViews() {
        binding.settingsDisplay.selectLanguage.setOnClickListener {
            showShortToastMessage("Soon to rise")
        }

        binding.settingsDisplay.currency.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_currencyFragment2)
        }
    }
}