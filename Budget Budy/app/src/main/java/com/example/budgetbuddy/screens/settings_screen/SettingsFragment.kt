package com.example.budgetbuddy.screens.settings_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentSettingsBinding
import com.example.budgetbuddy.screens.profile_screen.ProfileFragment
import com.example.budgetbuddy.screens.profile_screen.ViewPagerAdapter


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey("object") }?.apply {
           // println(getInt("object").toString())
            binding.textView.text = getInt("object").toString()
        }
    }

    companion object {

    }
}