package com.example.budgetbuddy.screens.add_new_entry

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.ActivityAddNewTransactionBinding
import com.example.budgetbuddy.room.tables.TransactionsTable
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class AddNewTransactionActivity : AppCompatActivity(), Serializable {
    private lateinit var binding: ActivityAddNewTransactionBinding

    companion object {
        const val EDIT_INTENT = "com.example.budgetbuddy.screens.add_new_entry.edit"
    }

    private val viewModel: AddNewTransactionActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()

        val tran = intent.getSerializableExtra(EDIT_INTENT)
        if (tran != null) {
            viewModel.setTable(tran as TransactionsTable)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}