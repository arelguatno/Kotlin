package com.example.budgetbuddy.fragments.transaction_detail_fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentTransactionDetailsBinding
import com.example.budgetbuddy.room.transactions_table.TransactionsTable
import com.example.budgetbuddy.screens.add_new_entry.AddNewEntryTransactionFragment
import com.example.budgetbuddy.screens.add_new_entry.AddNewTransactionActivity
import com.example.budgetbuddy.utils.dateToNice
import com.example.budgetbuddy.utils.getIncomeID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionDetailsFragment : MainFragment() {
    private lateinit var binding: FragmentTransactionDetailsBinding

    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private val viewModel: TransactionDetailsFragmentViewModel by viewModels()
    private val args: TransactionDetailsFragmentArgs by navArgs() // read passed data


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTransactionDetailsBinding.inflate(layoutInflater)
        inflateMenu()
        return binding.root
    }

    private fun inflateMenu() {
        binding.appBar.inflateMenu(R.menu.edit_transaction)
        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_edit -> editTransaction()
            }
            true
        }
        binding.appBar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewInit()
        startForResult()
        clickListener()
    }

    private fun clickListener() {
        binding.btnDelete.setOnClickListener {
            viewModel.deleteTransaction(viewModel.getTable().value!!)
            it.findNavController().navigateUp()
        }
    }

    private fun startForResult() {
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

                if (result.resultCode == Activity.RESULT_OK) {
                    if (result.data?.getSerializableExtra(AddNewEntryTransactionFragment.EDIT_EXISTING_ENTRY) != null) {
                        editTransaction(result.data)
                    }
                }
            }
    }

    private fun editTransaction(data: Intent?) {
        data?.getSerializableExtra(AddNewEntryTransactionFragment.EDIT_EXISTING_ENTRY)?.let {
            viewModel.setTable(it as TransactionsTable)
            viewModel.updateTransaction(it)
            showShortToastMessage("Updated")
        }
    }

    private fun viewInit() {
        viewModel.setTable(args.transaction)
        viewModel.getTable().observe(viewLifecycleOwner) {
            binding.txtCategory.text = it.category?.rowValue

            if (it.note!!.isEmpty()) {
                binding.txtNote.isVisible = false

                binding.imgCalendar.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    setMargins(0, 110, 0, 0)
                }
            } else {
                binding.txtNote.isVisible = true
                binding.txtNote.text = it.note
            }

            binding.imgCategory.setImageResource(it.category!!.imageID)
            binding.txtPrice.text = digitsConverter.formatWithCurrency(it.amount)
            binding.txtDate.text = dateToNice(it.date!!)

            if (it.category!!.uniqueID == getIncomeID()) {
                binding.txtPrice.setTextColor(Color.parseColor(getString(R.string.holo_light_blue)))
                binding.txtPrice.text = digitsConverter.formatWithCurrency(it.amount)
            }
        }
    }

    private fun editTransaction() {
        val intent = Intent(requireContext(), AddNewTransactionActivity::class.java).apply {
            putExtra(AddNewTransactionActivity.EDIT_INTENT, args.transaction)
        }
        startForResult.launch(intent)
    }
}