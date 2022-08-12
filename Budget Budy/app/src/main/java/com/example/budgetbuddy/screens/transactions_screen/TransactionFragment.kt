package com.example.budgetbuddy.screens.transactions_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentTransactionBinding
import com.example.budgetbuddy.room.tables.DateMonth
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TransactionFragment : MainFragment() {

    companion object {
        val cal: Calendar = Calendar.getInstance()
    }

    private lateinit var binding: FragmentTransactionBinding

    private val viewModel: TransactionViewModel by activityViewModels()
    private val myAdapterHeader: TransactionFragmentAdapterHeader by lazy { TransactionFragmentAdapterHeader() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransactionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initOnCLick()
        initViewModel()
        initTransactionData()
    }

    private fun initTransactionData() {
        binding.homeFragmentRecyclerViewParent.itemAnimator = null
        binding.homeFragmentRecyclerViewParent.adapter = myAdapterHeader
        binding.homeFragmentRecyclerViewParent.layoutManager = LinearLayoutManager(requireContext())

        val gaga = viewModel.getDate().value
        cal.time = gaga
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)
        queryData(month, year)
    }

    private fun initViewModel() {
        viewModel.getDate().observe(viewLifecycleOwner) {
            cal.time = it
            val month = cal.get(Calendar.MONTH)
            val year = cal.get(Calendar.YEAR)
            val ff = viewModel.transformTextLayout(month, year)
            binding.txtDate.text = ff
            queryData(month, year)
        }
    }

    private fun initOnCLick() {
        binding.leftImage.setOnClickListener {
            cal.time = viewModel.getDate().value
            cal.add(Calendar.MONTH, -1)
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            viewModel.setDate(cal.time)
        }

        binding.rightImage.setOnClickListener {
            cal.time = viewModel.getDate().value
            cal.add(Calendar.MONTH, +1)
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            viewModel.setDate(cal.time)
        }
    }


    private fun queryData(month: Int, year: Int) {
        viewModel.fetchRecordByMonthAndYear(month, year).observe(viewLifecycleOwner) {
            val list = viewModel.transactionListToWithHeaderAndChild(it)
            myAdapterHeader.submitList(list)
        }
    }

}