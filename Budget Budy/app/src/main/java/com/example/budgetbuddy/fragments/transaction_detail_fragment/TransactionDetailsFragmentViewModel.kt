package com.example.budgetbuddy.fragments.transaction_detail_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.room.TransactionsRepository
import com.example.budgetbuddy.room.tables.TransactionsTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailsFragmentViewModel @Inject constructor(
    private val repository: TransactionsRepository
) : ViewModel() {
    private val table = MutableLiveData<TransactionsTable>()

    fun setTable(v: TransactionsTable) {
        table.value = v
    }

    fun getTable(): LiveData<TransactionsTable> {
        return table
    }

    fun deleteTransaction(tran: TransactionsTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTransaction(tran)
        }
    }

    fun updateTransaction(tran: TransactionsTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTransaction(tran)
        }
    }
}