package com.example.budgetbuddy.screens.transactions_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.databinding.FragmentTransactionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionFragment : MainFragment() {

    companion object {

    }

    private lateinit var binding: FragmentTransactionBinding

    private val viewModel: TransactionViewModel by viewModels()
    private val myAdapterParent: TransactionFragmentAdapterParent by lazy { TransactionFragmentAdapterParent() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransactionBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeFragmentRecyclerViewParent.itemAnimator = null
        binding.homeFragmentRecyclerViewParent.adapter = myAdapterParent
        binding.homeFragmentRecyclerViewParent.layoutManager = LinearLayoutManager(requireContext())

        viewModel.fetchTransactionsGroupByDate.observe(viewLifecycleOwner) { list ->
            list.let {
                myAdapterParent.submitList(viewModel.transactionListToWithHeaderAndChild(list))
            }
        }
    }
}