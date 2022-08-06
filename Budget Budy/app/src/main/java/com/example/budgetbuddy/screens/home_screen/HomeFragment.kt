package com.example.budgetbuddy.screens.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.budgetbuddy.databinding.FragmentHomeBinding
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.screens.transactions_screen.TransactionFragmentAdapterParent
import com.example.budgetbuddy.screens.transactions_screen.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : MainFragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: TransactionViewModel by viewModels()
    private val myAdapterParent: TransactionFragmentAdapterParent by lazy { TransactionFragmentAdapterParent() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Budget Buddy"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}