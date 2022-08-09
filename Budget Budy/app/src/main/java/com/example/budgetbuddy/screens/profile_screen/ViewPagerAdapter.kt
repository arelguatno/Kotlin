package com.example.budgetbuddy.screens.profile_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.budgetbuddy.fragments.category.CategoryList
import com.example.budgetbuddy.screens.settings_screen.SettingsFragment
import com.example.budgetbuddy.screens.transactions_screen.TransactionFragment

class ViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return CategoryList.list.size
    }

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = SettingsFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(ARG_OBJECT, position + 1)
        }
        return fragment
    }

    companion object {
        private const val ARG_OBJECT = "object"
    }
}