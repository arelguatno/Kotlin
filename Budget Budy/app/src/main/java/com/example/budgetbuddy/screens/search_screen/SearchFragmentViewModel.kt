package com.example.budgetbuddy.screens.search_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.budgetbuddy.DigitsConverter
import com.example.budgetbuddy.room.transactions_table.TransactionsRepository
import com.example.budgetbuddy.room.model.TransactionList
import com.example.budgetbuddy.room.transactions_table.TransactionsTable
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val repository: TransactionsRepository,
    private val digitsConverter: DigitsConverter
) : ViewModel() {
    private val dbSearch = MutableLiveData<String>()
    private val textSearch = MutableLiveData<String>()

    fun setSearch(v: String) {
        dbSearch.value = v
    }

    fun getSearch(): MutableLiveData<String> {
        return dbSearch
    }

    fun setTextSearch(v: String) {
        textSearch.value = v
    }

    fun getTextSearch(): MutableLiveData<String> {
        return textSearch
    }

    fun searchFeature(query: String): LiveData<List<TransactionsTable>> {
        return repository.searchFeature(query).asLiveData()
    }

    fun transactionListToWithHeaderAndChild(
        param: List<TransactionsTable>
    ): List<TransactionList> {
        var tempDate = Calendar.getInstance().time
        var newFormattedList = mutableListOf<TransactionList>()
        for (i in param) {
            if (tempDate != i.date) {
                val childList = mutableListOf<TransactionsTable>()
                for (y in param) {
                    if (i.date == y.date) {
                        y.labels!!.amountLabel = digitsConverter.formatWithCurrency(y.amount)
                        childList.add(y)
                    }
                }
                val row = TransactionList(header = i.date!!, child = childList)
                newFormattedList.add(row)

            }
            tempDate = i.date
        }
        return newFormattedList
    }
}