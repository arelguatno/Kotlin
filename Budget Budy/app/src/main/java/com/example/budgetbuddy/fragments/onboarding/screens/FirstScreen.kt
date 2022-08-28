package com.example.budgetbuddy.fragments.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentFirstScreenBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstScreen : Fragment() {
    private lateinit var binding: FragmentFirstScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFirstScreenBinding.inflate(layoutInflater)

        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
        val actionBtn = requireActivity().findViewById<TextView>(R.id.actionBtn)
        actionBtn?.text = "Next"

        val currency = requireActivity().findViewById<LinearLayout>(R.id.currency)
        currency.isVisible = false

        actionBtn.setOnClickListener {
            viewPager.currentItem = 1
        }
        return binding.root
    }
}