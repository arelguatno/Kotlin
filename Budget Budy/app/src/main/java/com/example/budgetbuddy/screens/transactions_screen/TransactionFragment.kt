package com.example.budgetbuddy.screens.transactions_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.databinding.FragmentTransactionBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TransactionFragment : MainFragment() {
    private lateinit var binding: FragmentTransactionBinding

    companion object {

    }

    private val viewModel: TransactionViewModel by activityViewModels()
    private val myAdapterHeader: TransactionFragmentAdapterHeader by lazy { TransactionFragmentAdapterHeader() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        val date = viewModel.getDate().value
        cal.time = date
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

        viewModel.getTotalExpenses().observe(viewLifecycleOwner) {
            binding.txtTotalExpenses.text = String.format("-$ %.2f", it)
        }

        viewModel.getSumAmount().observe(viewLifecycleOwner) {
            binding.txtSum.text = String.format("-$ %.2f", it)
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
            if (list.isNotEmpty()) {
                myAdapterHeader.submitList(list)
                binding.txtNoRecordsFound.isVisible = false
                binding.homeFragmentRecyclerViewParent.isVisible = true
            } else {
                binding.txtNoRecordsFound.isVisible = true
                binding.homeFragmentRecyclerViewParent.isVisible = false
            }
        }
    }

}