package com.example.room_aye.screens.transactions_screen

import androidx.lifecycle.*
import com.example.room_aye.room.TransactionsRepository
import com.example.room_aye.room.tables.TransactionsTable
import com.example.room_aye.room.tables.TransactionList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionsRepository
) : ViewModel() {

    val fetchTransactionsGroupByDate = repository.fetchTransactionsGroupByDate().asLiveData()

    val fetchTransactions = repository.fetchTransactions().asLiveData()

    fun insertProfileRecord(transactionsTable: TransactionsTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertProfileRecord(transactionsTable)
        }
    }

    fun transactionListToWithHeaderAndChild(param: List<TransactionsTable>): List<TransactionList> {
        var tempDate = ""
        var header = mutableListOf<TransactionsTable>()
        var newFormattedList = mutableListOf<TransactionList>()

        //Get unique dates
        for (i in param) {
            if (tempDate != i.date) {
                header.add(i)
            }
            tempDate = i.date
        }


        // Combine Header and Childs
        for (y in header) {
            val childList = mutableListOf<TransactionsTable>()
            for (i in param) {
                if (i.date == y.date) {
                    childList.add(i)
                }
            }
            val que = TransactionList(
                header = y.date,
                child = childList
            )
            newFormattedList.add(que)
        }
        return newFormattedList
    }
}