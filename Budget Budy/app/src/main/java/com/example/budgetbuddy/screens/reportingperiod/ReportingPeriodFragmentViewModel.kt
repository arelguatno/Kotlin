package com.example.budgetbuddy.screens.reportingperiod

import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.NumberFormatOrigin
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportingPeriodFragmentViewModel @Inject constructor(
    private val numberFormatOrigin: NumberFormatOrigin
): ViewModel() {
}