package com.example.budgetbuddy.screens.wallets

import android.content.Context
import androidx.lifecycle.*
import com.example.budgetbuddy.R
import com.example.budgetbuddy.room.wallet_table.WalletRepository
import com.example.budgetbuddy.room.wallet_table.Wallets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val walletDao: WalletRepository
) : ViewModel() {

    val fetchWallet = walletDao.fetchWallet().asLiveData()
    val fetchPrimaryWallet = walletDao.fetchPrimaryWallet().asLiveData()

    fun fetchWalletID(id: Int): LiveData<List<Wallets>> {
        return walletDao.fetchWalletID(id).asLiveData()
    }



    fun insertWallet(wallets: Wallets) {
        viewModelScope.launch(Dispatchers.IO) {
            walletDao.insertWallet(wallets)
        }
    }

    fun deleteWallet(wallets: Wallets) {
        viewModelScope.launch(Dispatchers.IO) {
            walletDao.deleteWallet(wallets)
        }
    }

    fun deleteWalletID(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            walletDao.deleteWalletID(id)
        }
    }

}