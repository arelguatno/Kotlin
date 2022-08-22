package com.example.budgetbuddy.room

import com.example.budgetbuddy.room.tables.TransactionsTable
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class TransactionsRepository @Inject constructor(
    private val transactionsDao: TransactionsDao
) {

    fun fetchRecordByMonthAndYear(
        month: Int,
        year: Int,
        date: Long
    ): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchRecordByMonthAndYear(month, year, date)
    }

    fun fetchRecordByMonthAndYearFuture(date: Long): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchRecordByMonthAndYearFuture(date)
    }

    fun fetchReportingByWeekAndYear(week: Int, year: Int): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchReportingByWeekAndYear(week, year)
    }

    fun fetchReportingByQuarterAndYear(quarter: Int, year: Int): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchReportingByQuarterAndYear(quarter, year)
    }

    fun fetchRecentTransaction(): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchRecentTransaction()
    }

    fun fetchTopSpending(): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchTopSpending()
    }

    fun fetchTopSpentThisMonthAndPreviousMonth(
        prevMonth: Int,
        prevYear: Int
    ): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchTopSpentThisMonthAndPreviousMonth(
            prevMonth = prevMonth,
            prevYear = prevYear
        )
    }

    fun fetchReportingByMonthAndYear(month: Int, year: Int): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchReportingByMonthAndYear(month, year)
    }

    fun fetchReportingByMonthAndYearAndDay(
        month: Int,
        year: Int,
        day: Int
    ): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchReportingByMonthAndYearAndDay(month, year, day)
    }

    fun fetchReportingByYear(year: Int): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchReportingByYear(year)
    }

    fun fetchReportingAll(): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchReportingAll()
    }

    fun fetchTransactionsGroupByCategory(): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchTransactionsGroupByCategory()
    }

    fun fetchTransactions(): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchTransactions()
    }

    suspend fun insertProfileRecord(transactionsTable: TransactionsTable) {
        transactionsDao.insertProfileRecord(transactionsTable)
    }

    suspend fun deleteTransaction(transactionsTable: TransactionsTable) {
        transactionsDao.deleteTransaction(transactionsTable)
    }

    suspend fun updateTransaction(transactionsTable: TransactionsTable) {
        transactionsDao.updateTransaction(transactionsTable)
    }
}
