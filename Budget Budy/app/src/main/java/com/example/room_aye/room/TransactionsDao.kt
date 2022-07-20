package com.example.room_aye.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM transactions_table ORDER BY id ASC")
    fun fetchAllProfile(): Flow<List<TransactionsTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfileRecord(transactionsTable: TransactionsTable)
}