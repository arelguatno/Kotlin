package com.example.room_aye.room

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionsRepository @Inject constructor(
    private val transactionsDao: TransactionsDao
) {
    fun fetchAllProfile(): Flow<List<TransactionsTable>> {
        return transactionsDao.fetchAllProfile()
    }

    suspend fun insertProfileRecord(transactionsTable: TransactionsTable) {
        transactionsDao.insertProfileRecord(transactionsTable)
    }
}
