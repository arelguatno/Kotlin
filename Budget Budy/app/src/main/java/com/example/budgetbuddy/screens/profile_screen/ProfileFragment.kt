package com.example.budgetbuddy.screens.profile_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.budgetbuddy.R
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.databinding.FragmentProfileBinding
import com.example.budgetbuddy.fragments.category.CategoryList
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : MainFragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var demoCollectionAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        demoCollectionAdapter = ViewPagerAdapter(this)
        viewPager = binding.pager
        viewPager.adapter = demoCollectionAdapter

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
           tab.text = "OBJECT ${(position + 1)}"
        }.attach()


        for (i in CategoryList.geItems()) {

            tabLayout.addTab(tabLayout.newTab().setText(i.second.rowValue))
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                println(tab?.text)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}