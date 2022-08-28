package com.example.budgetbuddy.fragments.onboarding.viewpager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.budgetbuddy.MainActivity
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentViewPagerBinding
import com.example.budgetbuddy.fragments.category.CategoryList
import com.example.budgetbuddy.fragments.currency.CurrencyList
import com.example.budgetbuddy.fragments.onboarding.screens.FirstScreen
import com.example.budgetbuddy.fragments.onboarding.screens.SecondScreen
import com.example.budgetbuddy.fragments.onboarding.screens.ThirdScreen
import com.example.budgetbuddy.screens.settings_screen.SettingsFragmentViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text

@AndroidEntryPoint
class ViewPagerFragment : MainFragment() {
    private lateinit var binding: FragmentViewPagerBinding
    private val viewModel: SettingsFragmentViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(layoutInflater)

        val fragmentList = arrayListOf(FirstScreen(), SecondScreen(), ThirdScreen())

        val adapter =
            ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.actionBtn.text = "Next"
                        binding.currency.isVisible = false
                        binding.actionBtn.setOnClickListener {
                            binding.viewPager.currentItem = 1
                        }
                    }
                    1 -> {
                        binding.actionBtn.text = "Next"
                        binding.currency.isVisible = false
                        binding.actionBtn.setOnClickListener {
                            binding.viewPager.currentItem = 2
                        }
                    }
                    2 -> {
                        binding.actionBtn.text = "Launch App"
                        binding.currency.isVisible = true
                        binding.actionBtn.setOnClickListener {

                            onBoardingFinished()
                            findNavController().navigate(R.id.action_viewPagerFragment2_to_mainActivity)
                            activity?.finish()
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                println()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                println()
            }

        })
        return binding.root
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.on_boarding_shared_pref),
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putBoolean(getString(R.string.on_boarding_finised), true)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        binding.currency.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment2_to_currencyFragment2)
        }
        viewModel.setCurrency(digitsConverter.getCurrencySettings())
        viewModel.getCurrency().observe(viewLifecycleOwner) {
            binding.txtCurrency.text = it.textIcon.substring(0, 3)
        }
    }

}