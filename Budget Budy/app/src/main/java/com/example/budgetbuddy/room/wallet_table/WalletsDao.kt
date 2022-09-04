package com.example.budgetbuddy.room.wallet_table

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

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

    @Query("select \n" +
            "wallets_table.id,\n" +
            "name,\n" +
            "primary_wallet,\n" +
            "(select sum(amount) from transactions_table where walletID == wallets_table.id AND incomeInflow = 1 AND date<=:date) as income ,\n" +
            " (select sum(amount) from transactions_table where walletID == wallets_table.id AND incomeInflow = 0 AND date<=:date) as expenses\n" +
            " from wallets_table  left join  transactions_table ON transactions_table.walletID == wallets_table.id GROUP by wallets_table.id")
    fun fetchWallets(date: Date = Date()): Flow<List<Wallets>>
}