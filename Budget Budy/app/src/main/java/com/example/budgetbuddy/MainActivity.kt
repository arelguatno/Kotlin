package com.example.budgetbuddy

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.budgetbuddy.databinding.ActivityMainBinding
import com.example.budgetbuddy.room.tables.TransactionsTable
import com.example.budgetbuddy.screens.add_new_entry.AddNewEntryTransactionFragment
import com.example.budgetbuddy.screens.transactions_screen.TransactionViewModel
import com.example.budgetbuddy.screens.add_new_entry.AddNewTransactionActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Serializable {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Bottom Navigation
        setupBottomNav()
        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

                if ((result.resultCode == Activity.RESULT_OK) &&
                    result.data?.getSerializableExtra(AddNewEntryTransactionFragment.ADD_NEW_ENTRY) != null
                ) {
                    saveNewEntry(result.data)
                }
            }
        binding.floatingActionButton.setOnClickListener {
            startForResult.launch(Intent(this, AddNewTransactionActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavigationView, navController)
    }

    private fun saveNewEntry(data: Intent?) {
        data?.getSerializableExtra(AddNewEntryTransactionFragment.ADD_NEW_ENTRY)?.let {
            viewModel.insertProfileRecord(it as TransactionsTable)
            Toast.makeText(this, "New Entry Added", Toast.LENGTH_SHORT).show()
        }
    }
}