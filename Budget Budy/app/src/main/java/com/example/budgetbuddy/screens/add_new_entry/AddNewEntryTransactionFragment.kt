package com.example.budgetbuddy.screens.add_new_entry

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
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
import com.example.budgetbuddy.fragments.currency.CurrencyFragment
import com.example.budgetbuddy.room.transactions_table.DateRange
import com.example.budgetbuddy.room.transactions_table.TransactionsTable
import com.example.budgetbuddy.utils.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddNewEntryTransactionFragment : MainFragment() {
    private lateinit var binding: NewtransactionfragmentBinding
    private val viewModel: AddNewTransactionActivityViewModel by activityViewModels()
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var activityMain: AddNewTransactionActivity

    companion object {
        const val ADD_NEW_ENTRY = "com.example.room_aye.screens.add_new_entry"
        const val EDIT_EXISTING_ENTRY = "com.example.room_aye.screens.EDIT_EXISTING_ENTRY"
        var userClickedEditButton = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewtransactionfragmentBinding.inflate(layoutInflater)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAd()
    }

    private fun loadAd() {
        MobileAds.initialize(requireContext()) {}
        val mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityMain =
            context as AddNewTransactionActivity // so we can read intent data from activity
    }

    override fun onStart() {
        super.onStart()
        setUpScreen()
        inflateMenu()

        binding.adView.isVisible = showAds()
    }

    private fun inflateMenu() {
        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.saveMenu -> insertRecord()
            }
            true
        }
        binding.appBar.setNavigationOnClickListener {
            activity?.finish()
        }
    }

    private fun setUpScreen() {
        val editTransaction =
            activityMain.intent.getSerializableExtra(AddNewTransactionActivity.EDIT_INTENT)
        if (editTransaction == null) { // user wants to input new transaction
            userClickedEditButton = false
            if (viewModel.getDate().value == null) {
                viewModel.setDate(Calendar.getInstance().time) //set current date
            }

        } else { // user wants to edit transaction
            val transaction = editTransaction as TransactionsTable
            userClickedEditButton = true
            viewModel.setNote(transaction.note!!)
            viewModel.setDate(transaction.date!!)
            viewModel.setCurrency(transaction.currency!!.uniqueID)
            viewModel.setCategory(transaction.category!!)
            viewModel.setPrice(transaction.amount.toString())
        }

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

        sharedPref =
            activity?.getSharedPreferences(
                getString(R.string.global_currency_id),
                Context.MODE_PRIVATE
            )!!

        val userSelectsCurrency = sharedPref!!.getInt(getString(R.string.global_currency_id), 1)
        viewModel.setCurrency(userSelectsCurrency)

        //Currency Override Setting currency
        setFragmentResultListener(CurrencyFragment.RESULT_KEY) { _, bundle ->
            val result = bundle.getInt(CurrencyFragment.RESULT_KEY)
            viewModel.setCurrency(result)
        }
    }

    private fun setUpViewModelsListener() {
        viewModel.getNote().observe(viewLifecycleOwner) {
            binding.txtNote.text = it
            binding.txtNote.setTextColor(ContextCompat.getColor(requireContext(),R.color.text_color_white_black))
            binding.imgNote.setColorFilter(ContextCompat.getColor(requireContext(),R.color.text_color_white_black))
            binding.test.isFocusable = false
        }

        viewModel.getCategory().observe(viewLifecycleOwner) {
            binding.txtCategory.text = it.rowValue
            binding.imgCategory.setImageResource(it.imageID)

            binding.txtCategory.setTextColor(ContextCompat.getColor(requireContext(),R.color.text_color_white_black))
            binding.imgCategory.setColorFilter(ContextCompat.getColor(requireContext(),R.color.image_tint))
        }

        viewModel.getCurrency().observe(viewLifecycleOwner) {
            binding.imgCurrency.text = it.textIcon.substring(0, 3)
        }

        viewModel.getDate().observe(viewLifecycleOwner) {
            binding.txtDate.text = dateToNice(it)
        }

        viewModel.getPrice().observe(viewLifecycleOwner) {
            //binding.txtAmount.setText(String.format("%.2f", it.toDouble()))
            binding.txtAmount.setText(it)
        }
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

    private fun insertRecord() {
        if (binding.txtAmount.text.toString().isNotEmpty()) {
            viewModel.setPrice(binding.txtAmount.text.toString())
        }
        val amount = viewModel.getPrice().value?.toDouble() ?: 0.0
        val currency = viewModel.getCurrency().value
        val category = viewModel.getCategory().value
        val note = viewModel.getNote().value ?: ""
        val ddMMdyYYY = dateYyyyMmDd(viewModel.getDate().value!!)

        if (category != null && amount > 0) {

            val data = Intent()
            val timestamp = Date()

            var incomeFlow: Boolean = category.uniqueID == 11  //11 income see CategoryList

            val timeRange = DateRange(
                day = getDateDay(ddMMdyYYY),
                week = getDateWeek(ddMMdyYYY),
                month = getDateMonth(ddMMdyYYY),
                quarter = getDateQuarter(ddMMdyYYY),
                year = getDateYear(ddMMdyYYY)
            )

            if (!userClickedEditButton) {
                // New transaction
                var transaction = TransactionsTable(
                    amount = amount,
                    currency = currency!!,
                    category = category,
                    note = note,
                    date = Date(ddMMdyYYY),
                    timeStamp = timestamp,
                    time_range = timeRange,
                    incomeInflow = incomeFlow,
                    walletID = digitsConverter.getSharedPrefWalletID()
                )
                data.putExtra(ADD_NEW_ENTRY, transaction)
            } else {
                // Edit transaction
                val updateTransaction =
                    activityMain.intent.getSerializableExtra(AddNewTransactionActivity.EDIT_INTENT) as TransactionsTable

                updateTransaction.category = category
                updateTransaction.amount = amount
                updateTransaction.currency = currency!!
                updateTransaction.note = note
                updateTransaction.date = Date(ddMMdyYYY)
                updateTransaction.time_range = timeRange
                updateTransaction.incomeInflow = incomeFlow
                data.putExtra(EDIT_EXISTING_ENTRY, updateTransaction)
            }

            //(it as TransactionsTable)
            activity?.setResult(Activity.RESULT_OK, data)
            activity?.finish()


        } else {
            showShortToastMessage("Please input an Amount and select Category")
        }
    }
}