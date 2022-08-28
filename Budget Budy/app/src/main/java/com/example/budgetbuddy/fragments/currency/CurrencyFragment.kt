package com.example.budgetbuddy.fragments.currency

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentCurrencyBinding
import com.example.budgetbuddy.fragments.CurrencyAdapter
import com.example.budgetbuddy.fragments.DateFragment
import com.example.budgetbuddy.fragments.category.SimpleListObject
import com.example.budgetbuddy.fragments.transaction_detail_fragment.TransactionDetailsFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyFragment : MainFragment() {
    private lateinit var binding: FragmentCurrencyBinding
    private val args: CurrencyFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCurrencyBinding.inflate(layoutInflater)
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
        var selectedItem = if (args.fromSettings) {
            digitsConverter.getCurrencySettings()
        } else {
            digitsConverter.getCurrencyNewEntry()
        }

        val items = CurrencyAdapter(CurrencyList.geItems(), selectedItem)
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
        sharedPref =
            activity?.getSharedPreferences(
                getString(R.string.global_currency_id),
                Context.MODE_PRIVATE
            )!!
        with(sharedPref!!.edit()) {
            if (args.fromSettings) {
                setFragmentResult("arelguatno", bundleOf("arelguatno" to uniqueID))
                putInt(getString(R.string.global_currency_id), uniqueID)
            } else {
                putInt(getString(R.string.currency_id), uniqueID)
            }

            apply()
        }
    }
}