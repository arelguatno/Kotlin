package com.example.budgetbuddy.screens.transactions_screen

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadhamwi.tabsync.TabbedListMediator
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentTransactionBinding
import com.example.budgetbuddy.room.tables.TransactionList
import com.example.budgetbuddy.utils.dateYyyyMmDd
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
                val list = viewModel.transactionListToWithHeaderAndChild(list)
                initTabLayout(list)
                initMediator(list)
                myAdapterParent.submitList(list)
            }
        }
    }

    private fun initMediator(list: List<TransactionList>) {
        TabbedListMediator(
            binding.homeFragmentRecyclerViewParent,
            binding.tabLayout,
            list.indices.toList()
        ).attach()
    }


    private fun initTabLayout(list: List<TransactionList>) {
        val tabLayout = binding.tabLayout
        tabLayout.removeAllTabs()
        for (v in list) {
            tabLayout.addTab(tabLayout.newTab().setText(dateYyyyMmDd(v.header)))
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