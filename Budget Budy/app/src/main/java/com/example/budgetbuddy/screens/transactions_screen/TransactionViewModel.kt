package com.example.budgetbuddy.screens.transactions_screen

import androidx.lifecycle.*
import com.example.budgetbuddy.enums.TimeRange
import com.example.budgetbuddy.room.TransactionsRepository
import com.example.budgetbuddy.room.tables.TransactionList
import com.example.budgetbuddy.room.tables.TransactionsTable
import com.example.budgetbuddy.DigitsConverter
import com.example.budgetbuddy.utils.getCurrentMonth
import com.example.budgetbuddy.utils.getCurrentYear
import com.example.budgetbuddy.utils.getDateQuarter
import com.example.budgetbuddy.utils.intMonthLongToString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionsRepository,
    private val digitsConverter: DigitsConverter
) : ViewModel() {
    private val date = MutableLiveData(Date())
    private var totalExpenses = MutableLiveData(0.00)
    private var sumAmount = MutableLiveData(0.00)
    private var inflowAmount = MutableLiveData(0.00)
    private var dateAndTimeRange = MutableLiveData<DateAndTimeRange>()
    private var prevAndCurrentSpending = MutableLiveData<PrevAndCurrent>()
    private var totalExpensesLabel = MutableLiveData<String>()
    private var totalSumLabel = MutableLiveData("")
    private var totalInflow = MutableLiveData("")

    fun setPrevAndCurrentSpending(v: PrevAndCurrent) {
        prevAndCurrentSpending.value = v
    }

    fun getPrevAndCurrentSpending(): MutableLiveData<PrevAndCurrent> {
        return prevAndCurrentSpending
    }

    // Fetch Data
    fun fetchReporting(
        month: Int = 0,
        year: Int = 0,
        day: Int = 0,
        week: Int = 0,
        quarter: Int = 0,
        date: Long = Date().time,
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
            TimeRange.FUTURE -> repository.fetchRecordByMonthAndYearFuture(date).asLiveData()
            else -> repository.fetchRecordByMonthAndYear(month, year, date).asLiveData()
        }
    }

    val fetchRecentData = repository.fetchRecentTransaction().asLiveData()

    val fetchTopSpendingCurrentMonth =
        repository.fetchTopSpending().asLiveData()

    fun fetchTopSpentThisMonthAndPreviousMonth(
        prevMonth: Int,
        prevYear: Int
    ): LiveData<List<TransactionsTable>> {
        return repository.fetchTopSpentThisMonthAndPreviousMonth(prevMonth, prevYear).asLiveData()
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

    fun setDateAndTimeRange(v: DateAndTimeRange = DateAndTimeRange(Date(), TimeRange.MONTH)) {
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

    fun transactionListToWithHeaderAndChild(
        param: List<TransactionsTable>
    ): List<TransactionList> {
        var tempDate = Calendar.getInstance().time
        var newFormattedList = mutableListOf<TransactionList>()
        totalExpenses.value = 0.00
        for (i in param) { // Loop through all data
            if (tempDate != i.date) { // found a unique date
                val childList = mutableListOf<TransactionsTable>()
                var totalChild = 0.0
                for (y in param) {   // loop again and find transaction with the same date
                    if (i.date == y.date) {

                        totalExpenses.value = totalExpenses.value?.plus(y.amount)

                        //Transactions label for child
                        y.labels!!.catAmountLabel = digitsConverter.formatWithCurrency(y.catAmount)
                        y.labels!!.amountLabel = digitsConverter.formatWithCurrency(y.amount)

                        //Transactions label for header
                        totalChild = totalChild.plus(y.amount)
                        y.labels!!.headerLabel = digitsConverter.formatWithCurrency(totalChild)

                        childList.add(y)
                    }
                }
                val row = TransactionList(header = i.date!!, child = childList)
                newFormattedList.add(row)

                //output is an array of header and transactiontable
                // 6/23/2022, {transaction1, transaction2, transaction3}
                // 6/24/2022, {transaction2, transaction2, transaction3}
            }
            tempDate = i.date
        }
        sumAmount.value = totalExpenses.value!! - inflowAmount.value!!

        totalExpensesLabel.value = digitsConverter.formatWithCurrency(totalExpenses.value)
        totalSumLabel.value = digitsConverter.formatWithCurrency(sumAmount.value)
        totalInflow.value = digitsConverter.formatWithCurrency(0.00)
        return newFormattedList
    }

    fun getIncreaseOrDecrease(increment: Boolean, d: Date, range: TimeRange): Date {
        val cal: Calendar = Calendar.getInstance()
        cal.time = d
        return when (range) {
            TimeRange.DAY -> {
                if (increment) {
                    cal.add(Calendar.DAY_OF_MONTH, +1)
                } else {
                    cal.add(Calendar.DAY_OF_MONTH, -1)
                }
                cal.time
            }
            TimeRange.WEEK -> {
                if (increment) {
                    cal.add(Calendar.WEEK_OF_YEAR, +1)
                } else {
                    cal.add(Calendar.WEEK_OF_YEAR, -1)
                }
                cal.time
            }
            TimeRange.MONTH -> {
                if (increment) {
                    cal.add(Calendar.MONTH, +1)
                } else {
                    cal.add(Calendar.MONTH, -1)
                }
                cal.time
            }
            TimeRange.QUARTER -> {
                if (increment) {
                    cal.add(Calendar.MONTH, +3)
                } else {
                    cal.add(Calendar.MONTH, -3)
                }
                cal.time
            }
            TimeRange.YEAR -> {
                if (increment) {
                    cal.add(Calendar.YEAR, +1)
                } else {
                    cal.add(Calendar.YEAR, -1)
                }
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

    fun getSumAmountLabel(): MutableLiveData<String> {
        return totalSumLabel
    }

    fun getTotalExpensesLabel(): MutableLiveData<String> {
        return totalExpensesLabel
    }

    fun getTotalInflow(): MutableLiveData<String> {
        return totalInflow
    }

    fun processCategoryAmount(param: List<TransactionsTable>): List<TransactionsTable> {
        for (i in param) {
            i.labels!!.catAmountLabel = digitsConverter.formatWithCurrency(i.catAmount)
        }
        return param
    }

    fun processTransactionAmount(param: List<TransactionsTable>): List<TransactionsTable> {
        for (i in param) {
            i.labels!!.amountLabel = digitsConverter.formatWithCurrency(i.amount)
        }
        return param
    }
}
