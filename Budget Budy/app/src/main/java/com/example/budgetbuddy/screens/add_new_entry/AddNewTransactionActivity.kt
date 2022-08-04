package com.example.budgetbuddy.screens.add_new_entry

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.ActivityAddNewTransactionBinding
import com.example.budgetbuddy.enums.Currency
import com.example.budgetbuddy.enums.ExpensesCategory
import com.example.budgetbuddy.room.tables.TransactionsTable
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import java.util.*

@AndroidEntryPoint
class AddNewTransactionActivity : AppCompatActivity(), Serializable {

    private lateinit var binding: ActivityAddNewTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}