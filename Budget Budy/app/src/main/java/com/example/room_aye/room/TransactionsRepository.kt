package com.example.room_aye.room

import com.example.room_aye.room.tables.TransactionsTable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionsRepository @Inject constructor(
    private val transactionsDao: TransactionsDao
) {
    fun fetchTransactionsGroupByDate(): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchTransactionsGroupByDate()
    }

    fun fetchTransactions(): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchTransactions()
    }

    suspend fun insertProfileRecord(transactionsTable: TransactionsTable) {
        transactionsDao.insertProfileRecord(transactionsTable)
    }
}
