package com.example.budgetbuddy.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker.OnDateChangedListener
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.databinding.FragmentDateBinding
import com.example.budgetbuddy.screens.add_new_entry.AddNewTransactionActivityViewModel
import java.time.DayOfWeek
import java.util.*


class DateFragment : MainFragment() {
    private lateinit var binding: FragmentDateBinding
    private val viewModel: AddNewTransactionActivityViewModel by activityViewModels()

    companion object {
        const val RESULT_KEY = "com.example.budgetbuddy.fragments.DATE_ID"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Date"
        binding = FragmentDateBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun menu() {
        binding.appBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O) //TODO check this
    override fun onStart() {
        super.onStart()

        val calendar = Calendar.getInstance()
        calendar.time = viewModel.getDate().value
        binding.datePickerActions.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        menu()
        binding.datePickerActions.setOnDateChangedListener(OnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dayOfMonth)

            setFragmentResult(RESULT_KEY, bundleOf(RESULT_KEY to calendar.time))
            findNavController().navigateUp()
        })
    }
}
