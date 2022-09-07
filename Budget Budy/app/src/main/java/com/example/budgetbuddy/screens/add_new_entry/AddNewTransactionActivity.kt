package com.example.budgetbuddy.screens.add_new_entry

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.budgetbuddy.BaseActivity
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.ActivityAddNewTransactionBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class AddNewTransactionActivity : BaseActivity(), Serializable {
    private lateinit var binding: ActivityAddNewTransactionBinding
    val fragment = AddNewEntryTransactionFragment()

    companion object {
        const val EDIT_INTENT = "com.example.budgetbuddy.screens.add_new_entry.edit"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // getBundle()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}