package com.example.budgetbuddy.screens.search_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.databinding.FragmentSearchTransactionBinding
import com.example.budgetbuddy.screens.transactions_screen.TransactionFragmentAdapterHeader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchTransactionFragment : MainFragment() {
    private lateinit var binding: FragmentSearchTransactionBinding
    private val viewModel: SearchFragmentViewModel by activityViewModels()
    private val adapter: SearchFragmentAdapterHeader by lazy { SearchFragmentAdapterHeader() }
    private val randomString = "Dx6&$93^O4rdgZR^RVMZ@wG*06gg@HTETxHTw6^c&n*d98&O&"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchTransactionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        menu()
        search()
        init()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.getSearch().observe(viewLifecycleOwner) {
            searchDatabase(it)
        }

        viewModel.getTextSearch().observe(viewLifecycleOwner) {
            binding.searchView.setQuery(it, false)
        }
    }

    private fun init() {
        binding.recycler.itemAnimator = null
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

        if (binding.searchView.isEmpty()) {
            searchDatabase(randomString)
        }
    }

    private fun search() {

        binding.searchView.onActionViewExpanded()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchText: String?): Boolean {
                if (searchText != null) {
                    viewModel.setSearch(searchText)
                }
                return false
            }

            override fun onQueryTextChange(searchText: String?): Boolean {
                if (searchText != null) {
                    viewModel.setSearch(searchText)
                    viewModel.setTextSearch(searchText)
                }

                if (searchText.isNullOrEmpty()) {
                    viewModel.setSearch(randomString)
                }
                return false
            }
        })
    }

    private fun searchDatabase(param: String) {
        val searchQuery = "%$param%"

        viewModel.searchFeature(searchQuery).observe(viewLifecycleOwner) {
            val new = viewModel.transactionListToWithHeaderAndChild(it)
            adapter.submitList(new)
        }
    }

    private fun menu() {
        binding.appBar.setNavigationOnClickListener {
            activity?.finish()
        }
    }
}

