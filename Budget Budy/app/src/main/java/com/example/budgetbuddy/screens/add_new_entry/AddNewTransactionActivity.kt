package com.example.budgetbuddy.screens.add_new_entry

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.ActivityAddNewTransactionBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class AddNewTransactionActivity : AppCompatActivity(), Serializable {

    companion object{
        const val EDIT_INTENT = "com.example.budgetbuddy.screens.add_new_entry.edit"
    }

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

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }

}