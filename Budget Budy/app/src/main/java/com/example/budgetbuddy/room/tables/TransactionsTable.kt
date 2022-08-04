package com.example.budgetbuddy.room.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "transactions_table")
data class TransactionsTable(
    var amount: Double,
    var currencyID: Int,
    var currencyValue: String,
    var categoryID: Int,
    var categoryValue: String,
    var note: String,
    var date: String,
    var timeStamp: Date,
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}