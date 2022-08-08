package com.example.budgetbuddy.screens.transactions_screen

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
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
        inflateMenu()
        return binding.root
    }

    private fun inflateMenu() {
        binding.appBar.inflateMenu(R.menu.home_transactions_menu)
        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.group_by_date -> {
                    groupByDate()
                }
                R.id.group_by_cat -> {
                    groupByCat()
                }
            }
            true
        }
    }

    private fun groupByCat() {
//        viewModel.fetchTransactionsGroupByCategory.observe(viewLifecycleOwner) { list ->
//            list.let {
//                myAdapterParent.submitList(viewModel.transactionListToWithHeaderAndChild2(list))
//            }
//        }
    }

    private fun groupByDate() {
        viewModel.fetchTransactionsGroupByDate.observe(viewLifecycleOwner) { list ->
            list.let {
                myAdapterParent.submitList(viewModel.transactionListToWithHeaderAndChild(list))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeFragmentRecyclerViewParent.itemAnimator = null
        binding.homeFragmentRecyclerViewParent.adapter = myAdapterParent
        binding.homeFragmentRecyclerViewParent.layoutManager = LinearLayoutManager(requireContext())

        groupByDate()
    }
}