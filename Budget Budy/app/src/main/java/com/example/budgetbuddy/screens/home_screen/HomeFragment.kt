package com.example.budgetbuddy.screens.home_screen

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentHomeBinding
import com.example.budgetbuddy.screens.transactions_screen.TransactionViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


@AndroidEntryPoint
class HomeFragment : MainFragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: TransactionViewModel by activityViewModels()
    private val recentAdapter: RecentTransactionAdapter by lazy { RecentTransactionAdapter() }
    private val spendingReports: RecentTransactionAdapter by lazy { RecentTransactionAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initSpendingReports()
        initRecentData()
        initBarChart()
    }

    private fun initSpendingReports() {
        binding.spendingReport.spendingReportRecycler.itemAnimator = null
        binding.spendingReport.spendingReportRecycler.adapter = spendingReports
        binding.spendingReport.spendingReportRecycler.layoutManager =
            LinearLayoutManager(requireContext())

        viewModel.fetchTopSpending.observe(viewLifecycleOwner) {
            spendingReports.submitList(it)
        }
    }

    private fun initRecentData() {
        binding.recentTransaction.recentTransactionRecycler.itemAnimator = null
        binding.recentTransaction.recentTransactionRecycler.adapter = recentAdapter
        binding.recentTransaction.recentTransactionRecycler.layoutManager =
            LinearLayoutManager(requireContext())

        viewModel.fetchRecentData.observe(viewLifecycleOwner) {
            recentAdapter.submitList(it)
        }
    }

    private fun initBarChart() {
        val xAxisLabels = arrayOf(getString(R.string.last_month), getString(R.string.this_month))
        var barChart: BarChart = binding.spendingReport.barChart
        var barDataSet = BarDataSet(getBarEntries(), "")
        barDataSet.color = Color.parseColor("#76A7FA")
        val data = BarData(barDataSet)
        barChart.data = data
        barChart.description.isEnabled = false
        val xAxis = barChart.xAxis

        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
        xAxis.setCenterAxisLabels(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        barChart.minOffset = 0f
        barChart.isDragEnabled = true
        barChart.xAxis.textColor = Color.WHITE
        barChart.axisLeft.textColor = Color.WHITE
        barChart.setVisibleXRangeMaximum(3f)
        data.barWidth = 0.40f
        barChart.animate()
        barChart.setTouchEnabled(false);
        barChart.legend.isEnabled = false
        barChart.xAxis.isEnabled = true;
        barChart.axisLeft.isEnabled = true;
        barChart.axisRight.isEnabled = false;
        barChart.axisLeft.setDrawGridLines(false);
        barChart.xAxis.setDrawGridLines(false);
        barChart.axisLeft.setStartAtZero(true);
        barChart.barData.setValueTextColor(Color.WHITE)
        barChart.barData.setValueFormatter(BarChartDataFormatter())
        barChart.barData.setValueTextSize(8f)
    }


    private fun getBarEntries(): ArrayList<BarEntry> {
        val barEntries = ArrayList<BarEntry>()
        barEntries.add(BarEntry(0f, 450f)) // last month
        barEntries.add(BarEntry(1f, 194f)) // this month
        return barEntries
    }

    class BarChartDataFormatter : ValueFormatter() {
        override fun getFormattedValue(
            value: Float
        ): String {
            return "$ ${value.toInt()}"
        }
    }
}