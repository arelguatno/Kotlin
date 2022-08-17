package com.example.budgetbuddy.screens.transactions_screen

import androidx.lifecycle.*
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.enums.TimeRange
import com.example.budgetbuddy.room.TransactionsRepository
import com.example.budgetbuddy.room.tables.TransactionList
import com.example.budgetbuddy.room.tables.TransactionsTable
import com.example.budgetbuddy.utils.getDateQuarter
import com.example.budgetbuddy.utils.intMonthLongToString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionsRepository
) : ViewModel() {

    private val date = MutableLiveData(Date())
    private var totalExpenses = MutableLiveData(0.00)
    private var sumAmount = MutableLiveData(0.00)
    private var inflowAmount = MutableLiveData(0.00)
    private var dateAndTimeRange = MutableLiveData<DateAndTimeRange>()
    private var increaseOrDecrease = MutableLiveData<Date>()
    private var operator = MutableLiveData<Boolean>()

    fun fetchRecordByMonthAndYear(month: Int, year: Int): LiveData<List<TransactionsTable>> {
        return repository.fetchRecordByMonthAndYear(month, year).asLiveData()
    }

    // Fetch Reporting
    fun fetchReporting(
        month: Int,
        year: Int,
        day: Int,
        week: Int,
        quarter: Int,
        time_range: TimeRange
    ): LiveData<List<TransactionsTable>> {
        return when (time_range) {
            TimeRange.MONTH -> repository.fetchReportingByMonthAndYear(month, year).asLiveData()
            TimeRange.DAY -> repository.fetchReportingByMonthAndYearAndDay(month, year, day)
                .asLiveData()
            TimeRange.YEAR -> repository.fetchReportingByYear(year).asLiveData()
            TimeRange.WEEK -> repository.fetchReportingByWeekAndYear(week, year).asLiveData()
            TimeRange.QUARTER -> repository.fetchReportingByQuarterAndYear(quarter, year)
                .asLiveData()
            TimeRange.ALL -> repository.fetchReportingAll().asLiveData()
            else -> repository.fetchReportingByMonthAndYear(month, year).asLiveData()
        }
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

    fun setDateAndTimeRange(v: DateAndTimeRange) {
        dateAndTimeRange.value = v
    }

    fun getDateAndTimeRange(): MutableLiveData<DateAndTimeRange> {
        return dateAndTimeRange
    }

    fun getDateLabel(v: TimeRange, d: Date): MutableLiveData<String> {
        val cal: Calendar = Calendar.getInstance()
        cal.time = d

        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val week = cal.get(Calendar.WEEK_OF_YEAR)

        return when (v) {
            TimeRange.DAY -> MutableLiveData(transformDateToMonthAndYearDay(month, year, day))
            TimeRange.WEEK -> MutableLiveData(transformDateToWeek(week))
            TimeRange.MONTH -> MutableLiveData(transformDateToMonthAndYear(month, year))
            TimeRange.QUARTER -> MutableLiveData(
                labelQuarterAndYear(
                    getDateQuarter(cal.time.toString()),
                    year
                )
            )
            TimeRange.YEAR -> MutableLiveData(labelYear(year))
            TimeRange.ALL -> MutableLiveData("All")
            else -> MutableLiveData(transformDateToMonthAndYear(month, year))
        }
    }

    fun transactionListToWithHeaderAndChild(param: List<TransactionsTable>): List<TransactionList> {
        var tempDate = Calendar.getInstance().time
        var newFormattedList = mutableListOf<TransactionList>()
        totalExpenses.value = 0.00
        for (i in param) { // Loop through all data

            if (tempDate != i.date) { // found a unique date
                val childList = mutableListOf<TransactionsTable>()
                for (y in param) {   // loop again and find transaction with the same date
                    if (i.date == y.date) {
                        childList.add(y)
                        totalExpenses.value = totalExpenses.value?.plus(y.amount)
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
        sumAmount.value = totalExpenses.value!! - inflowAmount.value!!
        return newFormattedList
    }

    fun setIncreaseOrDecrease(op: Boolean) {
        //increaseOrDecrease.value = v
        operator.value = op
    }

    fun getIncreaseOrDecrease(op: Boolean, d: Date, range: TimeRange): Date {
        val cal: Calendar = Calendar.getInstance()
        cal.time = d
        return when (range) {
            TimeRange.DAY -> {
                if (op) {
                    cal.add(Calendar.DAY_OF_MONTH, +1)
                } else {
                    cal.add(Calendar.DAY_OF_MONTH, -1)
                }
                cal.time
            }
            TimeRange.WEEK -> {
                if (op) {
                    cal.add(Calendar.WEEK_OF_YEAR, +1)
                } else {
                    cal.add(Calendar.WEEK_OF_YEAR, -1)
                }
                cal.time
            }
            TimeRange.MONTH -> {
                if (op) {
                    cal.add(Calendar.MONTH, +1)
                } else {
                    cal.add(Calendar.MONTH, -1)
                }
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
                cal.time
            }
            TimeRange.QUARTER -> {
                if (op) {
                    cal.add(Calendar.MONTH, +3)
                } else {
                    cal.add(Calendar.MONTH, -3)
                }
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
                cal.time
            }
            TimeRange.YEAR -> {
                if (op) {
                    cal.add(Calendar.YEAR, +1)
                } else {
                    cal.add(Calendar.YEAR, -1)
                }
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
                cal.time
            }
            TimeRange.ALL -> {
                cal.time
            }
            else -> {
                cal.time
            }
        }
    }

    fun transformDateToMonthAndYear(num: Int, year: Int): String {
        return "${intMonthLongToString(num)} $year"
    }

    private fun transformDateToMonthAndYearDay(num: Int, year: Int, day: Int): String {
        return "${intMonthLongToString(num)} $day, $year"
    }

    private fun transformDateToWeek(week: Int): String {
        return "Week $week"
    }

    private fun labelQuarterAndYear(quarter: Int, year: Int): String {
        return "Q$quarter $year"
    }

    private fun labelYear(year: Int): String {
        return "$year"
    }

    fun getTotalExpenses(): MutableLiveData<Double> {
        return totalExpenses
    }

    fun getSumAmount(): MutableLiveData<Double> {
        return sumAmount
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

}
