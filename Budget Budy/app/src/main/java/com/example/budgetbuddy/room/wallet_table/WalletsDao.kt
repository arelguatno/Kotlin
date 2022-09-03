package com.example.budgetbuddy.room.wallet_table

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletsDao {

    @Query("SELECT * FROM wallets_table WHERE id =:id")
    fun fetchWalletId(id: Int): Flow<List<Wallets>>

    @Query("SELECT * FROM wallets_table WHERE primary_wallet = 1")
    fun fetchPrimaryWallet(): Flow<List<Wallets>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(wallet: Wallets)

    @Delete
    suspend fun deleteWallet(wallet: Wallets)

    @Update
    suspend fun updateWallet(wallet: Wallets)

    @Query("DELETE FROM wallets_table WHERE id =:id")
    suspend fun deleteWalletID(id: Int)

    @Query("select \n" +
            "wallets_table.id,\n" +
            "name,\n" +
            "primary_wallet,\n" +
            "(select sum(amount) from transactions_table where walletID == wallets_table.id AND incomeInflow = 1) as income ,\n" +
            " (select sum(amount) from transactions_table where walletID == wallets_table.id AND incomeInflow = 0) as expenses\n" +
            " from wallets_table  left join  transactions_table ON transactions_table.walletID == wallets_table.id")
    fun fetchWallets(): Flow<List<Wallets>>
}