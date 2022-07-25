package com.example.room_aye.screens.add_new_entry

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.room_aye.R
import com.example.room_aye.databinding.ActivityAddNewTransactionBinding
import com.example.room_aye.enums.Currency
import com.example.room_aye.enums.ExpensesCategory
import com.example.room_aye.room.tables.TransactionsTable
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import java.util.*

@AndroidEntryPoint
class AddNewTransactionActivity : AppCompatActivity(), Serializable {

    private lateinit var binding: ActivityAddNewTransactionBinding
    private val viewModel: AddNewTransactionActivityViewModel by viewModels()

    companion object {
        const val ADD_NEW_ENTRY = "com.example.room_aye.screens.add_new_entry"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun insertRecord() {
        var data = Intent()
        val date = Date()

        var cal: Calendar = Calendar.getInstance()
        cal.time = date
        val formattedDate =
            "${cal.get(Calendar.MONTH)}/${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.YEAR)}"

        var transaction = TransactionsTable(
            amount = 80.0,
            currency = Currency.USD,
            category = ExpensesCategory.FOOD_BEVERAGES,
            note = "Rent for this month",
            date = formattedDate,
            timeStamp = date,
        )
        data.putExtra(ADD_NEW_ENTRY, transaction)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saveMenu -> insertRecord()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_transaction, menu)
        return true
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}