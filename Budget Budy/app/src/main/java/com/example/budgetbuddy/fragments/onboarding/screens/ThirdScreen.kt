package com.example.budgetbuddy.fragments.onboarding.screens

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentSecondScreenBinding
import com.example.budgetbuddy.databinding.FragmentThirdScreenBinding
import com.example.budgetbuddy.fragments.DateFragment
import com.example.budgetbuddy.screens.settings_screen.SettingsFragmentDirections
import com.example.budgetbuddy.screens.settings_screen.SettingsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ThirdScreen : MainFragment() {
    private lateinit var binding: FragmentThirdScreenBinding
    private val viewModel: SettingsFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentThirdScreenBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initTxt()
//
        //viewModel.setCurrency(digitsConverter.getCurrencySettings())
    }

    private fun initTxt() {
//

    }
}