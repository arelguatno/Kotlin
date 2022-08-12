package com.example.budgetbuddy.screens.transactions_screen

import androidx.lifecycle.*
import com.example.budgetbuddy.room.TransactionsRepository
import com.example.budgetbuddy.room.tables.MonthlyData
import com.example.budgetbuddy.room.tables.TransactionList
import com.example.budgetbuddy.room.tables.TransactionsTable
import com.example.budgetbuddy.utils.intMonthLongToString
import com.example.budgetbuddy.utils.intMonthShortToString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionsRepository
) : ViewModel() {

    private val date = MutableLiveData(Date())

    val fetchTransactionsGroupByDate = repository.fetchTransactionsGroupByDate().asLiveData()

    fun fetchRecordByMonthAndYear(month: Int, year: Int): LiveData<List<TransactionsTable>> {
        return repository.fetchRecordByMonthAndYear(month, year).asLiveData()
    }

    val fetchTransactionsGroupByCategory =
        repository.fetchTransactionsGroupByCategory().asLiveData()

    fun insertProfileRecord(transactionsTable: TransactionsTable) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insertProfileRecord(transactionsTable)
            } catch (e: Exception) {
                println(e.toString())
            }
        }
    }

    fun setDate(d: Date) {
        date.value = d
    }

    fun getDate(): MutableLiveData<Date> {
        return date
    }

//    private fun transactionListToWithHeaderAndChild(
//        param: List<TransactionsTable>,
//        month: Int
//    ): List<TransactionList> {
//        var tempDate = Calendar.getInstance().time
//        var newFormattedList = mutableListOf<TransactionList>()
//
//        for (i in param) { // Loop through all data
//            if (month != i.date.month) continue
//
//            if (tempDate != i.date) { // found a unique date
//                val childList = mutableListOf<TransactionsTable>()
//
//                for (y in param) {   // loop again and find transaction with the same date
//                    if (i.date == y.date) {
//                        childList.add(y)
//                    }
//                }
//
//                val row = TransactionList(header = i.date, child = childList)
//                newFormattedList.add(row)
//            }
//            tempDate = i.date
//        }
//        return newFormattedList
//    }
//
//    fun monthlyData(param: List<TransactionsTable>): List<MonthlyData> {
//        var newFormattedList = mutableListOf<MonthlyData>()
//        var monthlyDate = -1
//
//        for (i in param) {
//            if (monthlyDate != i.month && monthlyDate >= 0) {
//                val tran = transactionListToWithHeaderAndChild(param, monthlyDate)
//                val new = MonthlyData(intMonthLongToString(monthlyDate), tran)
//                newFormattedList.add(new)
//            }
//            monthlyDate = i.month
//        }
//        return newFormattedList
//    }


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

    fun transformTextLayout(num: Int, year: Int): String {
        val kk = Calendar.getInstance()
        val prev = kk.get(Calendar.MONTH) - 1
        println()
        return when {
            (kk.get(Calendar.MONTH) == num && kk.get(Calendar.YEAR) == year) -> {
                "This Month"
            }
            (prev == num && kk.get(Calendar.YEAR) == year) -> {
                "Last Month"
            }
            else -> {
                "${intMonthLongToString(num)} $year"
            }
        }
    }
}