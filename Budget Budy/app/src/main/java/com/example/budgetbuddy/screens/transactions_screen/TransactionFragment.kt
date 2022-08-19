package com.example.budgetbuddy.screens.transactions_screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentTransactionBinding
import com.example.budgetbuddy.enums.TimeRange
import com.example.budgetbuddy.screens.reportingperiod.ReportingPeriodActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TransactionFragment() : MainFragment() {
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
        if (date != null) {
            viewModel.setDate(date)
        }
    }

    private fun initViewModel() {
        viewModel.getDate().observe(viewLifecycleOwner) {
            cal.time = it
            val currentMonth = cal.get(Calendar.MONTH)
            val year = cal.get(Calendar.YEAR)
            val ff = viewModel.transformDateToMonthAndYear(currentMonth, year)

            if (Date() >= it) {
                binding.calendarSelect.txtDate.text = ff
                queryData(currentMonth, year, timeRange = TimeRange.OTHERS)
            } else {
                val c = Calendar.getInstance()
                binding.calendarSelect.txtDate.text = getString(R.string.future)
                queryData(
                    month = c.get(Calendar.MONTH),
                    year = c.get(Calendar.YEAR),
                    future = true,
                    timeRange = TimeRange.FUTURE
                )
            }
        }

        viewModel.getTotalExpenses().observe(viewLifecycleOwner) {
            binding.txtTotalExpenses.text = String.format("-$ %.2f", it)
        }

        viewModel.getSumAmount().observe(viewLifecycleOwner) {
            binding.txtSum.text = String.format("-$ %.2f", it)
        }
    }

    private fun initOnCLick() {
        binding.calendarSelect.leftImage.setOnClickListener {
            cal.time = viewModel.getDate().value
            cal.add(Calendar.MONTH, -1)
            viewModel.setDate(cal.time)
        }

        binding.calendarSelect.rightImage.setOnClickListener {
            cal.time = viewModel.getDate().value
            if (Date() >= cal.time) {
                cal.add(Calendar.MONTH, +1)
                viewModel.setDate(cal.time)
            }
        }

        binding.txtViewReport.setOnClickListener {
            val intent = Intent(requireContext(), ReportingPeriodActivity::class.java).apply {
                putExtra(ReportingPeriodActivity.DATE_DATA, viewModel.getDate().value)
            }
            startActivity(intent)
        }
    }

    private fun queryData(
        month: Int = 0,
        year: Int = 0,
        future: Boolean = false,
        timeRange: TimeRange
    ) {
        viewModel.fetchReporting(
            month = month,
            year = year,
            time_range = timeRange
        )
            .observe(viewLifecycleOwner) {
                val list = viewModel.transactionListToWithHeaderAndChild(it, future)
                if (list.isNotEmpty()) {
                    myAdapterHeader.submitList(list)
                    binding.txtNoRecordsFound.isVisible = false
                    binding.nestedView.isVisible = true
                } else {
                    binding.txtNoRecordsFound.isVisible = true
                    binding.nestedView.isVisible = false
                }
            }
    }

}