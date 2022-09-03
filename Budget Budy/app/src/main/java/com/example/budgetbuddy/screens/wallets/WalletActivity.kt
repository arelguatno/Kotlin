package com.example.budgetbuddy.screens.wallets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.budgetbuddy.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
    }
}