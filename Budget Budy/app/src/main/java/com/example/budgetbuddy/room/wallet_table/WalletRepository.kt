package com.example.budgetbuddy.room.wallet_table

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WalletRepository @Inject constructor(
    private val walletsDao: WalletsDao
) {
    suspend fun insertWallet(wallets: Wallets) {
        walletsDao.insertWallet(wallets)
    }

    suspend fun deleteWallet(wallets: Wallets) {
        walletsDao.deleteWallet(wallets)
    }

    fun fetchWallet(): Flow<List<Wallets>> {
        return walletsDao.fetchWallets()
    }

    fun fetchWalletID(id: Int): Flow<List<Wallets>> {
        return walletsDao.fetchWalletId(id)
    }

    fun fetchPrimaryWallet(): Flow<List<Wallets>> {
        return walletsDao.fetchPrimaryWallet()
    }
}