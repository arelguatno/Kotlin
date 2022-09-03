package com.example.budgetbuddy.room.transactions_table

import com.example.budgetbuddy.DigitsConverter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionsRepository @Inject constructor(
    private val transactionsDao: TransactionsDao,
    private val digitsConverter: DigitsConverter
) {

    fun fetchRecordByMonthAndYear(
        month: Int,
        year: Int,
        date: Long
    ): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchRecordByMonthAndYear(
            month,
            year,
            date,
            walletID = digitsConverter.getSharedPrefWalletID()
        )
    }

    fun fetchRecordByMonthAndYearFuture(date: Long): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchRecordByMonthAndYearFuture(
            date,
            walletID = digitsConverter.getSharedPrefWalletID()
        )
    }

    fun fetchReportingByWeekAndYear(week: Int, year: Int): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchReportingByWeekAndYear(week, year ,walletID = digitsConverter.getSharedPrefWalletID())
    }

    fun fetchReportingByQuarterAndYear(quarter: Int, year: Int): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchReportingByQuarterAndYear(quarter, year,walletID = digitsConverter.getSharedPrefWalletID())
    }

    fun fetchRecentTransaction(): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchRecentTransaction(walletID = digitsConverter.getSharedPrefWalletID())
    }

    fun fetchTopSpending(): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchTopSpending(walletID = digitsConverter.getSharedPrefWalletID())
    }

    fun fetchTopSpentThisMonthAndPreviousMonth(
        prevMonth: Int,
        prevYear: Int
    ): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchTopSpentThisMonthAndPreviousMonth(
            prevMonth = prevMonth,
            prevYear = prevYear,
            walletID = digitsConverter.getSharedPrefWalletID()
        )
    }

    fun fetchReportingByMonthAndYear(month: Int, year: Int): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchReportingByMonthAndYear(month, year,walletID = digitsConverter.getSharedPrefWalletID())
    }

    fun searchFeature(query: String): Flow<List<TransactionsTable>> {
        return transactionsDao.searchFeature(
            query,
            walletID = digitsConverter.getSharedPrefWalletID()
        )
    }

    fun fetchMyWallet(): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchMyWallet(walletID = digitsConverter.getSharedPrefWalletID())
    }

    fun fetchReportingByMonthAndYearAndDay(
        month: Int,
        year: Int,
        day: Int
    ): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchReportingByMonthAndYearAndDay(month, year, day,walletID = digitsConverter.getSharedPrefWalletID())
    }

    fun fetchReportingByYear(year: Int): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchReportingByYear(year,walletID = digitsConverter.getSharedPrefWalletID())
    }

    fun fetchReportingAll(): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchReportingAll(walletID = digitsConverter.getSharedPrefWalletID())
    }

    fun fetchTransactionsGroupByCategory(): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchTransactionsGroupByCategory()
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
