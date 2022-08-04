package com.example.budgetbuddy.fragments.currency

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentCurrencyBinding
import com.example.budgetbuddy.fragments.CurrencyAdapter
import com.example.budgetbuddy.fragments.category.SimpleListObject


class CurrencyFragment : MainFragment() {
    private lateinit var binding: FragmentCurrencyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Currency"
        binding = FragmentCurrencyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        loadItems()
    }

    private fun loadItems() {
        val items = CurrencyAdapter(CurrencyList.geItems())
        binding.item.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.item.adapter = items

        items.setItemOnClickListener(object : CurrencyAdapter.onItemClickListener {
            override fun onItemClick(obj: SimpleListObject) {
                // save preferred currency
                savePreparedCurrency(obj.uniqueID)
                findNavController().navigateUp()
            }
        })
    }

    private fun savePreparedCurrency(uniqueID: Int) {
        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt(
                getString(R.string.PREFERENCE_CURRENCY_ID),
                uniqueID
            )  //#1 is USD in the CurrencyList
            apply()
        }
    }
}