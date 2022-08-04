package com.example.budgetbuddy.screens.add_new_entry

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.DateBottomSheetDialogBinding
import com.example.budgetbuddy.databinding.NewtransactionfragmentBinding
import com.example.budgetbuddy.fragments.DateFragment
import com.example.budgetbuddy.fragments.NoteFragment
import com.example.budgetbuddy.fragments.category.CategoryFragment
import com.example.budgetbuddy.fragments.category.SimpleListObject
import com.example.budgetbuddy.room.tables.TransactionsTable
import com.example.budgetbuddy.utils.dateToNice
import com.example.budgetbuddy.utils.dateYyyyMmDd
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*


class AddNewEntryTransactionFragment : MainFragment() {
    private lateinit var binding: NewtransactionfragmentBinding
    private val viewModel: AddNewTransactionActivityViewModel by viewModels()
    private lateinit var bottomSheetDialog: BottomSheetDialog


    companion object {
        const val ADD_NEW_ENTRY = "com.example.room_aye.screens.add_new_entry"
        private lateinit var menuitem: MenuItem
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewtransactionfragmentBinding.inflate(layoutInflater)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Add new entry"
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        viewModel.setDate(Calendar.getInstance().time) //set current date

        setOnClickListener()
        setUpFragmentNavResultListener()
        setUpViewModelsListener()
    }

    private fun setOnClickListener() {

        binding.categoryLinear.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_addNewEntryTransactionFragment_to_categoryFragment)
        }

        binding.noteLinear.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_addNewEntryTransactionFragment_to_noteFragment)
        }

        binding.dateLinear.setOnClickListener {
            showBottomSheetDialog()
        }

        binding.imgCurrency.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_addNewEntryTransactionFragment_to_currencyFragment)
        }
    }

    private fun setUpFragmentNavResultListener() {
        // Category
        setFragmentResultListener(CategoryFragment.RESULT_KEY) { _, bundle ->
            val result = bundle.getSerializable(CategoryFragment.RESULT_KEY)
            viewModel.setCategory(result as SimpleListObject)
        }

        // Note
        setFragmentResultListener(NoteFragment.RESULT_KEY) { _, bundle ->
            val result = bundle.getString(NoteFragment.RESULT_KEY)
            result?.let { viewModel.setNote(it) }
        }

        // Date
        setFragmentResultListener(DateFragment.RESULT_KEY) { _, bundle ->
            val result = bundle.getSerializable(DateFragment.RESULT_KEY)
            viewModel.setDate(result as Date)
        }

        //Load previous selected currency. Default currencyID is 1 for USD
        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val userSelectsCurrency = sharedPref.getInt(getString(R.string.PREFERENCE_CURRENCY_ID), 1)
        viewModel.setCurrency(userSelectsCurrency)
    }

    private fun setUpViewModelsListener() {
        viewModel.getNote().observe(viewLifecycleOwner, Observer {
            binding.txtNote.text = it
            binding.txtNote.setTextColor(Color.WHITE)
        })

        viewModel.getCategory().observe(viewLifecycleOwner, Observer {
            binding.txtCategory.text = it.rowValue
            binding.imgCategory.setImageResource(it.imageID)
            binding.txtCategory.setTextColor(Color.WHITE)
        })

        viewModel.getCurrency().observe(viewLifecycleOwner, Observer {
            binding.imgCurrency.setImageResource(it.imageID)
        })

        viewModel.getDate().observe(viewLifecycleOwner, Observer {
            binding.txtDate.text = dateToNice(it)
        })
    }

    private fun showBottomSheetDialog() {
        val b = DateBottomSheetDialogBinding.inflate(layoutInflater)
        b.btnToday.setOnClickListener {
            val calendar = Calendar.getInstance()
            viewModel.setDate(calendar.time)
            bottomSheetDialog.dismiss()
        }

        b.btnYesterday.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -1)
            viewModel.setDate(calendar.time)
            bottomSheetDialog.dismiss()
        }

        b.btnCustom.setOnClickListener {
            bottomSheetDialog.dismiss()
            findNavController()
                .navigate(R.id.action_addNewEntryTransactionFragment_to_dateFragment)
        }

        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setContentView(b.root)
        bottomSheetDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_transaction, menu)
        menuitem = menu.findItem(R.id.saveMenu)
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saveMenu -> insertRecord()
            android.R.id.home -> {
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertRecord() {
        var data = Intent()
        val timestamp = Date()

        var amount: Double = 0.0
        if (binding.txtAmount.text.toString().isNotEmpty()) {
            amount = binding.txtAmount.text.toString().toDouble()
        }

        val currency = viewModel.getCurrency().value
        val category = viewModel.getCategory().value
        val note = viewModel.getNote().value ?: ""
        val ddMMdyYYY = dateYyyyMmDd(viewModel.getDate().value!!)
        if (category != null && amount > 0) {

            var transaction = TransactionsTable(
                amount = amount,
                currencyID = currency!!.uniqueID,
                currencyValue = currency.rowValue,
                categoryID = category.uniqueID,
                categoryValue = category.rowValue,
                note = note,
                date = ddMMdyYYY,
                timeStamp = timestamp,
            )
            data.putExtra(ADD_NEW_ENTRY, transaction)
            activity?.setResult(Activity.RESULT_OK, data)
            activity?.finish()

        } else {
            showShortToastMessage("Please input an Amount and select Category")
        }

    }
}