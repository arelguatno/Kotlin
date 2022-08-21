package com.example.budgetbuddy.screens.settings_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentSettingsBinding
import com.example.budgetbuddy.screens.transactions_screen.TransactionFragmentAdapterHeader
import com.example.budgetbuddy.screens.transactions_screen.TransactionViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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
        viewModel.setCurrency(getCurrency())
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