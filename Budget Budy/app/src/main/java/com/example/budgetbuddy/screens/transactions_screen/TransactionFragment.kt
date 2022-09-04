package com.example.budgetbuddy.screens.transactions_screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentTransactionBinding
import com.example.budgetbuddy.enums.TimeRange
import com.example.budgetbuddy.screens.add_new_entry.AddNewEntryTransactionFragment
import com.example.budgetbuddy.screens.add_new_entry.AddNewTransactionActivity
import com.example.budgetbuddy.screens.reportingperiod.ReportingPeriodActivity
import com.example.budgetbuddy.screens.search_screen.SearchActivity
import com.example.budgetbuddy.utils.intMonthShortToString
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class TransactionFragment : MainFragment() {
    private lateinit var binding: FragmentTransactionBinding
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    companion object {

    }

    private val viewModel: TransactionViewModel by activityViewModels()
    private val myAdapterHeader: TransactionFragmentAdapterHeader by lazy { TransactionFragmentAdapterHeader() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun loadAd() {
        MobileAds.initialize(requireContext()) {}
        val mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }


    override fun onStart() {
        super.onStart()

        initOnCLick()
        initViewModel()
        initTransactionData()
        initRefreshToPull()


        viewModel.viewModelScope.launch {
            viewModel.getRefreshTransaction().observe(viewLifecycleOwner) {
                viewModel.setDate(viewModel.getDate().value!!)
            }
        }

    }

    private fun initRefreshToPull() {
//        binding.swiperefresh.setOnRefreshListener {
//            onStart()
//            binding.swiperefresh.isRefreshing = false
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menu()
        startForResult()
        loadAd()
    }

    private fun menu() {
        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search -> launchSearchActivity()
            }
            true
        }
    }

    private fun launchSearchActivity() {
        val intent = Intent(requireContext(), SearchActivity::class.java).apply {
            // Put extra
        }
        startForResult.launch(intent)
    }

    private fun startForResult() {
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

                if (result.resultCode == Activity.RESULT_OK) {
                    if (result.data?.getSerializableExtra(AddNewEntryTransactionFragment.EDIT_EXISTING_ENTRY) != null) {
                        //do anything
                    }
                }
            }
    }

    private fun initTransactionData() {
        binding.homeFragmentRecyclerViewParent.itemAnimator = null
        binding.homeFragmentRecyclerViewParent.adapter = myAdapterHeader
        binding.homeFragmentRecyclerViewParent.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initViewModel() {
        viewModel.getDate().observe(viewLifecycleOwner) {
            val cal = Calendar.getInstance()
            cal.time = it
            val currentMonth = cal.get(Calendar.MONTH)
            val year = cal.get(Calendar.YEAR)
            //LogStr()
            val ff = viewModel.transformDateToMonthAndYear(currentMonth, year)

            if (Date() >= it) {
                binding.calendarSelect.txtDate.text = ff
                queryData(currentMonth, year, timeRange = TimeRange.OTHERS)
            } else {
                binding.calendarSelect.txtDate.text = getString(R.string.future)
                queryData(timeRange = TimeRange.FUTURE)
            }
        }

        viewModel.getTotalExpensesLabel().observe(viewLifecycleOwner) {
            binding.txtTotalExpenses.text = it
        }

        viewModel.getSumAmountLabel().observe(viewLifecycleOwner) {
            binding.txtSum.text = it
        }

        viewModel.getTotalInflow().observe(viewLifecycleOwner) {
            binding.totalInflow.text = it
        }
    }

    private fun initOnCLick() {
        binding.calendarSelect.leftImage.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.time = viewModel.getDate().value
            cal.add(Calendar.MONTH, -1)
            viewModel.setDate(cal.time)
        }

        binding.calendarSelect.rightImage.setOnClickListener {
            val cal = Calendar.getInstance()
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
        timeRange: TimeRange
    ) {
        //LogStr("Aye $month $year $timeRange ${intMonthShortToString(month)}")
        viewModel.fetchReporting(
            month = month,
            year = year,
            time_range = timeRange
        )
            .observe(viewLifecycleOwner) {
                val list = viewModel.transactionListToWithHeaderAndChild(it)
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