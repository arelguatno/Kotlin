package com.example.budgetbuddy.fragments.transaction_detail_fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentTransactionDetailsBinding
import com.example.budgetbuddy.fragments.category.CategoryList
import com.example.budgetbuddy.room.tables.TransactionsTable
import com.example.budgetbuddy.screens.add_new_entry.AddNewEntryTransactionFragment
import com.example.budgetbuddy.screens.add_new_entry.AddNewTransactionActivity
import com.example.budgetbuddy.screens.transactions_screen.TransactionViewModel
import com.example.budgetbuddy.utils.dateToNice
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionDetailsFragment : MainFragment() {
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var binding: FragmentTransactionDetailsBinding
    private val viewModel: TransactionViewModel by viewModels()
    private val args: TransactionDetailsFragmentArgs by navArgs() // read passed data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransactionDetailsBinding.inflate(layoutInflater)
        val toolbar = binding.appBar
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

                if (result.resultCode == Activity.RESULT_OK) {
                    if (result.data?.getSerializableExtra(AddNewEntryTransactionFragment.EDIT_EXISTING_ENTRY) != null) editTransaction(
                        result.data
                    )
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDelete.setOnClickListener {
            viewModel.deleteTransaction(args.transaction)
            it.findNavController().navigateUp()
        }

        binding.txtCategory.text = args.transaction.category.rowValue
        if (args.transaction.note.isEmpty()) {
            binding.txtNote.isVisible = false

            binding.imgCalendar.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                setMargins(0, 110, 0, 0)
            }
        } else {
            binding.txtNote.text = args.transaction.note
        }

        binding.imgCategory.setImageResource(args.transaction.category.imageID)

        binding.txtPrice.text =
            String.format("-${args.transaction.currency.textIcon} %.2f", args.transaction.amount)
        binding.txtDate.text = dateToNice(args.transaction.date)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_transaction, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
            }
            R.id.menu_edit -> {
                editTransaction()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editTransaction() {
        val intent = Intent(requireContext(), AddNewTransactionActivity::class.java).apply {
            putExtra(AddNewTransactionActivity.EDIT_INTENT, args.transaction)
        }
        startForResult.launch(intent)

    }

    private fun editTransaction(data: Intent?) {
        data?.getSerializableExtra(AddNewEntryTransactionFragment.EDIT_EXISTING_ENTRY)?.let {
            viewModel.updateTransaction(it as TransactionsTable)
            showShortToastMessage("Updated")
        }
    }
}