package com.example.budgetbuddy.screens.transactions_screen

import androidx.lifecycle.*
import com.example.budgetbuddy.room.TransactionsRepository
import com.example.budgetbuddy.room.tables.TransactionsTable
import com.example.budgetbuddy.room.tables.TransactionList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionsRepository
) : ViewModel() {

    val fetchTransactionsGroupByDate = repository.fetchTransactionsGroupByDate().asLiveData()

    val fetchTransactionsGroupByCategory = repository.fetchTransactionsGroupByCategory().asLiveData()

    fun insertProfileRecord(transactionsTable: TransactionsTable) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insertProfileRecord(transactionsTable)
            } catch (e: Exception) {
                println(e.toString())
            }
        }
    }

    fun transactionListToWithHeaderAndChild(param: List<TransactionsTable>): List<TransactionList> {
        var tempDate = Calendar.getInstance().time
        var newFormattedList = mutableListOf<TransactionList>()

        for (i in param) { // Loop through all data
            if (tempDate != i.date) { // found a unique date
                val childList = mutableListOf<TransactionsTable>()

                for (y in param) {   // loop again and find transaction with the same date
                    if (i.date == y.date) {
                        childList.add(y)
                    }
                }

                val row = TransactionList(header = i.date, child = childList)
                newFormattedList.add(row)

                //output is an array of header and transactiontable
                // 6/23/2022, {transaction1, transaction2, transaction3}
                // 6/24/2022, {transaction2, transaction2, transaction3}
            }
            tempDate = i.date
        }
        return newFormattedList
    }

//    fun transactionListToWithHeaderAndChild2(param: List<TransactionsTable>): List<TransactionList> {
//        var tempDate = ""
//        var newFormattedList = mutableListOf<TransactionList>()
//
//        for (i in param) { // Loop through all data
//            if (tempDate != i.category.rowValue) { // found a unique date
//                val childList = mutableListOf<TransactionsTable>()
//
//                for (y in param) {   // loop again and find transaction with the same date
//                    if (i.category.rowValue == y.category.rowValue) {
//                        childList.add(y)
//                    }
//                }
//
//                val row = TransactionList(header = i.category.rowValue, child = childList)
//                newFormattedList.add(row)
//
//                //output is an array of header and transactiontable
//                // 6/23/2022, {transaction1, transaction2, transaction3}
//                // 6/24/2022, {transaction2, transaction2, transaction3}
//            }
//            tempDate = i.category.rowValue
//        }
//        return newFormattedList
//    }
}