package com.example.room_aye.screens.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.room_aye.databinding.FragmentHomeBinding
import com.example.room_aye.MainFragment
import com.example.room_aye.screens.transactions_screen.TransactionFragmentAdapterParent
import com.example.room_aye.screens.transactions_screen.TransactionViewModel
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
        return binding.root
    }
}