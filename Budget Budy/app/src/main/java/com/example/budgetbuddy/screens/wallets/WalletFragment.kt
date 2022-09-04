package com.example.budgetbuddy.screens.wallets

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.DialogNewCategoryBinding
import com.example.budgetbuddy.databinding.FragmentWalletBinding
import com.example.budgetbuddy.room.wallet_table.Wallets
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class WalletFragment : MainFragment() {
    private lateinit var binding: FragmentWalletBinding
    private val viewModel: WalletViewModel by activityViewModels()
    private val myAdapterHeader: WalletFragmentAdapter by lazy { WalletFragmentAdapter() }
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWalletBinding.inflate(layoutInflater)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        initMenu()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = myAdapterHeader
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        myAdapterHeader.setDefaultID(digitsConverter.getSharedPrefWalletID())

        viewModel.fetchWallet.observe(viewLifecycleOwner) {
            val gg = viewModel.processTransactionAmount(it)
            myAdapterHeader.submitList(gg)
        }

        myAdapterHeader.setItemOnClickListener(object : WalletFragmentAdapter.onItemClickListener {
            override fun onItemClick(wallet: Wallets) {
                setGlobalWalletId(wallet.id)
                activity?.setResult(Activity.RESULT_OK)
                activity?.finish()
            }

            override fun onItemLongClicked(wallet: Wallets) {
                deleteDialog(wallet)
            }
        })

    }

    private fun deleteDialog(wallet: Wallets) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Are sure you want to delete this wallet?")
            builder.setMessage("${wallet.name}")
            builder.apply {
                setPositiveButton(R.string.delete,
                    DialogInterface.OnClickListener { _, _ ->
                        if (wallet.primary_wallet == true && wallet.id == 1) {
                            showShortToastMessage("Personal wallet cannot be deleted")
                        } else {
                            if(wallet.id == digitsConverter.getSharedPrefWalletID()){
                                setGlobalWalletId(1) // set to default
                                initViews()
                            }
                            viewModel.deleteWallet(wallet)
                        }
                    })
                setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    })
            }
            builder.create()
        }
        alertDialog?.show()
    }

    private fun initMenu() {
        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.plusMenu -> addWallet()
            }
            true
        }
        binding.appBar.setNavigationOnClickListener {
            activity?.finish()
        }
    }

    private fun addWallet() {
        showBottomSheetDialog()
    }

    private fun showBottomSheetDialog() {
        val b = DialogNewCategoryBinding.inflate(layoutInflater)
        b.btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        b.btnCreate.setOnClickListener {
            val inputText = b.categoryEditText.text
            if (validateText(inputText.toString(), b.inputTxt)) {
                insertWallet(
                    inputText.toString()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                bottomSheetDialog.dismiss()
            }
        }

        b.categoryEditText.addTextChangedListener {
            b.inputTxt.error = null
        }
        bottomSheetDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setContentView(b.root)
        bottomSheetDialog.show()
    }

    private fun validateText(text: String, inputTxt: TextInputLayout): Boolean {
        var clear = true

        if (text.isEmpty()) {
            clear = false
            inputTxt.error = "Input wallet name"
        }

        if (text.length >= 15) {
            clear = false
            inputTxt.error = "Max 15 characters"
        }

        if (clear) {

        }
        return clear
    }

    private fun insertWallet(toString: String) {
        val wallet = Wallets(toString)
        viewModel.insertWallet(wallet)
    }

    fun setGlobalWalletId(input: Int) {
        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.global_wallet_id),
            Context.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            putInt(getString(com.example.budgetbuddy.R.string.global_wallet_id), input)
            apply()
        }
    }
}