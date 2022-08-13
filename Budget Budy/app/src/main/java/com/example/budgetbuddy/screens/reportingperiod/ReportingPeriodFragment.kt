package com.example.budgetbuddy.screens.reportingperiod

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.DateBottomSheetDialogBinding
import com.example.budgetbuddy.databinding.DateRangeBottomSheetDialogBinding
import com.example.budgetbuddy.databinding.FragmentReportinPeriodBinding
import com.example.budgetbuddy.screens.transactions_screen.TransactionViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ReportingPeriodFragment : MainFragment() {
    private lateinit var binding: FragmentReportinPeriodBinding
    private val viewModel: TransactionViewModel by activityViewModels()

    companion object {
        private lateinit var bottomSheetDialog: BottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportinPeriodBinding.inflate(layoutInflater)
        bottomSheetDialog = BottomSheetDialog(requireContext())

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        inflateMenu()
        initViewModel()
        initOnCLick()
    }

    private fun initViewModel() {
        viewModel.getDate().observe(viewLifecycleOwner) {
            cal.time = it
            val month = cal.get(Calendar.MONTH)
            val year = cal.get(Calendar.YEAR)
            val ff = viewModel.transformTextLayout(month, year)
            binding.calendarSelect.txtDate.text = ff
        }
    }

    private fun initOnCLick() {
        binding.calendarSelect.leftImage.setOnClickListener {
            cal.time = viewModel.getDate().value
            cal.add(Calendar.MONTH, -1)
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            viewModel.setDate(cal.time)
        }

        binding.calendarSelect.rightImage.setOnClickListener {
            cal.time = viewModel.getDate().value
            cal.add(Calendar.MONTH, +1)
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            viewModel.setDate(cal.time)
        }
    }

    private fun inflateMenu() {
        binding.appBar.inflateMenu(R.menu.reporting_menu)
        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_calendar -> selectTimeRange()
            }
            true
        }
        binding.appBar.setNavigationOnClickListener {
            activity?.finish()
        }
    }

    private fun selectTimeRange() {
        val b = DateRangeBottomSheetDialogBinding.inflate(layoutInflater)

        b.btnDay.setOnClickListener {

        }
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setContentView(b.root)
        bottomSheetDialog.show()
    }
}