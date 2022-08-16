package com.example.budgetbuddy.room

import com.example.budgetbuddy.room.tables.TransactionsTable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionsRepository @Inject constructor(
    private val transactionsDao: TransactionsDao
) {

    fun fetchRecordByMonthAndYear(month: Int, year: Int): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchRecordByMonthAndYear(month, year)
    }

    fun fetchReportingByMonthAndYear(month: Int, year: Int): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchReportingByMonthAndYear(month, year)
    }

    fun fetchReportingByMonthAndYearAndDay(month: Int, year: Int, day: Int): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchReportingByMonthAndYearAndDay(month, year, day)
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

    suspend fun deleteTransaction(transactionsTable: TransactionsTable){
        transactionsDao.deleteTransaction(transactionsTable)
    }

    suspend fun updateTransaction(transactionsTable: TransactionsTable){
        transactionsDao.updateTransaction(transactionsTable)
    }
}
