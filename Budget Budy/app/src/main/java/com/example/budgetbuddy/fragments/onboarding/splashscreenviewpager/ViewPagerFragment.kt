package com.example.budgetbuddy.fragments.onboarding.splashscreenviewpager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentViewPagerBinding
import com.example.budgetbuddy.fragments.onboarding.screens.FirstScreen
import com.example.budgetbuddy.fragments.onboarding.screens.SecondScreen
import com.example.budgetbuddy.fragments.onboarding.screens.ThirdScreen
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(layoutInflater)

        val fragmentList = arrayListOf(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen(),
        )

        val adapter =
            ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, list ->
        }.attach()


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.actionBtn.text = "Next"
                        binding.actionBtn.setOnClickListener {
                            binding.viewPager.currentItem = 1
                        }
                    }
                    1 -> {
                        binding.actionBtn.text = "Next"
                        binding.actionBtn.setOnClickListener {
                            binding.viewPager.currentItem = 2
                        }
                    }
                    2 -> {
                        binding.actionBtn.text = "Launch App"
                        binding.actionBtn.setOnClickListener {
                            findNavController().navigate(R.id.action_viewPagerFragment_to_mainActivity)
                            onBoardingFinished()
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
}