package com.example.budgetbuddy.room

import androidx.room.*
import com.example.budgetbuddy.room.tables.TransactionsTable
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM transactions_table ORDER BY id")
    fun fetchTransactions(): Flow<List<TransactionsTable>>

    @Query("SELECT * FROM transactions_table ORDER BY date DESC")
    fun fetchTransactionsGroupByDate(): Flow<List<TransactionsTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfileRecord(transactionsTable: TransactionsTable)

    @Delete
    suspend fun deleteTransaction(tran: TransactionsTable)

    @Update
    suspend fun updateTransaction(tran: TransactionsTable)
}