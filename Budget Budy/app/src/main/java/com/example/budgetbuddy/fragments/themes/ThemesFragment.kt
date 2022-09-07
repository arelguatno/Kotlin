package com.example.budgetbuddy.fragments.themes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentCurrencyBinding
import com.example.budgetbuddy.databinding.FragmentThemesBinding
import com.example.budgetbuddy.fragments.CurrencyAdapter
import com.example.budgetbuddy.fragments.category.SimpleListObject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThemesFragment : MainFragment() {
    private lateinit var binding: FragmentThemesBinding

    companion object {
        fun setTheme(uniqueID: Int) {
            when (uniqueID) {
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Light
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // Dark
                }
                3 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); // System
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentThemesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        loadItems()
        menu()
    }

    private fun menu() {
        binding.appBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadItems() {
        var selectedItem = digitsConverter.getThemesID()

        val items = ThemesAdapter(ThemesList.geItems(), selectedItem)
        binding.item.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.item.adapter = items

        items.setItemOnClickListener(object : ThemesAdapter.onItemClickListener {
            override fun onItemClick(obj: SimpleListObject) {
                saveThemeID(obj.uniqueID)
                setTheme(obj.uniqueID)
                findNavController().navigateUp()
            }
        })
    }

    private fun saveThemeID(uniqueID: Int) {
        sharedPref =
            activity?.getSharedPreferences(
                getString(R.string.global_themes_id),
                Context.MODE_PRIVATE
            )!!
        with(sharedPref!!.edit()) {
            putInt(getString(R.string.global_themes_id), uniqueID)
            apply()
        }
    }


}