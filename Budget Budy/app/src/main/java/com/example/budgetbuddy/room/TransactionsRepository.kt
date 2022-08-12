package com.example.budgetbuddy.room

import com.example.budgetbuddy.room.tables.DateMonth
import com.example.budgetbuddy.room.tables.TransactionsTable
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class TransactionsRepository @Inject constructor(
    private val transactionsDao: TransactionsDao
) {
    fun fetchTransactionsGroupByDate(): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchTransactionsGroupByDate()
    }

    fun fetchRecordByMonthAndYear(month: Int, year: Int): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchRecordByMonthAndYear(month, year)
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
