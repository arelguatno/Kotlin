package com.example.budgetbuddy.screens.home_screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.DigitsConverter
import com.example.budgetbuddy.MainActivity
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentHomeBinding
import com.example.budgetbuddy.screens.add_new_entry.AddNewEntryTransactionFragment
import com.example.budgetbuddy.screens.add_new_entry.AddNewTransactionActivity
import com.example.budgetbuddy.screens.reportingperiod.ReportingPeriodActivity
import com.example.budgetbuddy.screens.settings_screen.SettingsFragmentViewModel
import com.example.budgetbuddy.screens.transactions_screen.PrevAndCurrent
import com.example.budgetbuddy.screens.transactions_screen.TransactionViewModel
import com.example.budgetbuddy.screens.wallets.WalletActivity
import com.example.budgetbuddy.screens.wallets.WalletViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class HomeFragment : MainFragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: TransactionViewModel by activityViewModels()
    private val walletViewModel: WalletViewModel by activityViewModels()
    private val settingsViewModel: SettingsFragmentViewModel by activityViewModels()
    private val recentAdapter: RecentTransactionAdapter by lazy { RecentTransactionAdapter() }
    private val topSpending: RecentTransactionAdapter by lazy { RecentTransactionAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        seeMyWallet()
        return binding.root
    }

    private fun seeMyWallet() {
        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

                if (result.resultCode == Activity.RESULT_OK) {
                    readSelectedWallet()
                }
            }

        binding.myWallet.txtSeeAllWallet.setOnClickListener {
            val intent = Intent(requireContext(), WalletActivity::class.java)
            startForResult.launch(intent)
        }
    }

    private fun readSelectedWallet() {
        showShortToastMessage("Wallet Loaded Successfully")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAd()
    }

    private fun loadAd() {
        MobileAds.initialize(requireContext()) {}
        val mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    override fun onStart() {
        super.onStart()

        initSpendingReports()
        initRecentData()
        initTotalSpentThisMonth()
        initWallet()
        initButtons()
    }

    private fun initButtons() {
        //Check if user premium
        binding.adView.isVisible = !settingsViewModel.getPremiumUser()

        binding.recentTransaction.txtRecentReports.setOnClickListener {
            val navView =
                requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            navView?.menu?.findItem(R.id.transactionFragment)!!.isChecked = true
            navView?.menu?.performIdentifierAction(R.id.transactionFragment, 0)
        }

        binding.spendingReport.txtSeeReports.setOnClickListener {
            val intent = Intent(requireContext(), ReportingPeriodActivity::class.java).apply {
                putExtra(ReportingPeriodActivity.DATE_DATA, Date()) // Current Month
            }
            startActivity(intent)
        }
    }

    private fun initWallet() {
        viewModel.fetchMyWalletBalance().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val totalExpenses = it[0].catAmount
                val totalInflow = it[0].percentage
                binding.myWallet.txtWallet.text =
                    digitsConverter.formatCurrencyPositiveOrNegative(totalInflow, totalExpenses)
            }
        }

        walletViewModel.fetchWalletID(digitsConverter.getSharedPrefWalletID())
            .observe(viewLifecycleOwner) {
                if (it.isEmpty()) {

                } else {
                    binding.myWallet.txtMyWallet.text = it[0].name
                }
            }
    }

    private fun initTotalSpentThisMonth() {
        cal.time = Date()
        val prev = Calendar.getInstance()
        prev.time = cal.time
        prev.add(Calendar.MONTH, -1)

        viewModel.fetchTopSpentThisMonthAndPreviousMonth(
            prev.get(Calendar.MONTH), prev.get(Calendar.YEAR)
        )
            .observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    viewModel.setPrevAndCurrentSpending(
                        PrevAndCurrent(
                            it[0].percentage!!,
                            it[0].catAmount!!
                        )
                    )
                }
            }

        viewModel.getPrevAndCurrentSpending().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.spendingReport.txtTotalSpent.text =
                    digitsConverter.formatWithCurrency(it.current)
                var barEntries = ArrayList<BarEntry>()

                barEntries.add(BarEntry(0f, it.prev.toFloat())) // last month
                barEntries.add(BarEntry(1f, it.current.toFloat())) // this month
                initBarChart(barEntries)
            }
        }
    }

    private fun initSpendingReports() {
        binding.spendingReport.spendingReportRecycler.itemAnimator = null
        binding.spendingReport.spendingReportRecycler.adapter = topSpending
        binding.spendingReport.spendingReportRecycler.layoutManager =
            LinearLayoutManager(requireContext())

        viewModel.fetchTopSpendingCurrentMonth().observe(viewLifecycleOwner) {
            val transform = viewModel.processCategoryAmount(it)
            topSpending.submitList(transform)

            binding.spendingReport.txtNoData.isVisible = false
            if (it.isEmpty()) {
                binding.spendingReport.txtNoData.isVisible = true
            }
        }
    }

    private fun initRecentData() {
        binding.recentTransaction.recentTransactionRecycler.itemAnimator = null
        binding.recentTransaction.recentTransactionRecycler.adapter = recentAdapter
        binding.recentTransaction.recentTransactionRecycler.layoutManager =
            LinearLayoutManager(requireContext())

        viewModel.fetchRecentTransaction().observe(viewLifecycleOwner) {
            val transform = viewModel.processTransactionAmount(it)
            recentAdapter.submitList(transform)
            binding.recentTransaction.txtNoData.isVisible = false
            if (it.isEmpty()) {
                binding.recentTransaction.txtNoData.isVisible = true
            }
        }
    }

    private fun initBarChart(barEntries: ArrayList<BarEntry>) {
        val xAxisLabels = arrayOf(getString(R.string.last_month), getString(R.string.this_month))
        var barChart: BarChart = binding.spendingReport.barChart
        var barDataSet = BarDataSet(barEntries, "")
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
        barChart.axisLeft.isEnabled = false;  // left axis label
        barChart.axisRight.isEnabled = false;
        barChart.axisLeft.setDrawGridLines(false);
        barChart.xAxis.setDrawGridLines(false);
        barChart.axisLeft.setStartAtZero(true);
        barChart.barData.setValueTextColor(Color.WHITE)
        barChart.barData.setValueFormatter(BarChartDataFormatter(digitsConverter))
        barChart.barData.setValueTextSize(8f)
        barChart.setExtraOffsets(0f, 0f, 0f, 0.5f)  // bottom padding

        val y: YAxis = barChart.axisLeft
        val max = (barDataSet.yMax / 11)  // adding few extra space to make the data value visible
        println(barDataSet.yMax + max)
        y.axisMaximum = barDataSet.yMax + max

        barChart.invalidate()
        barChart.notifyDataSetChanged()


    }

    class BarChartDataFormatter(val numberFormat: DigitsConverter) : ValueFormatter() {

        override fun getFormattedValue(
            value: Float
        ): String {
            return "${numberFormat.formatWithCurrency(value.toDouble())}"
        }
    }
}