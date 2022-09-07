package com.example.budgetbuddy.screens.reportingperiod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.budgetbuddy.BaseActivity
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.ActivityReportingPeriodBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportingPeriodActivity : BaseActivity() {
    private lateinit var binding: ActivityReportingPeriodBinding

    companion object{
        const val DATE_DATA = "com.example.budgetbuddy.screens.reportingperiod.date_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportingPeriodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}