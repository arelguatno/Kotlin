package com.example.budgetbuddy.room.wallet_table

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletsDao {

    @Query("SELECT * FROM wallets_table ORDER BY id")
    fun fetchWallets(): Flow<List<Wallets>>

    @Query("SELECT * FROM wallets_table WHERE id =:id")
    fun fetchWalletId(id: Int): Flow<List<Wallets>>

    @Query("SELECT * FROM wallets_table WHERE `primary` = 1")
    fun fetchPrimaryWallet(): Flow<List<Wallets>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(wallet: Wallets)

    @Delete
    suspend fun deleteWallet(wallet: Wallets)

    @Update
    suspend fun updateWallet(wallet: Wallets)

    @Query("DELETE FROM wallets_table WHERE id =:id")
    suspend fun deleteWalletID(id: Int)
}