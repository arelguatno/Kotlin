package com.example.room_aye.room.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.room_aye.enums.Currency
import com.example.room_aye.enums.ExpensesCategory
import java.io.Serializable
import java.util.*

@Entity(tableName = "transactions_table")
data class TransactionsTable(
    var amount: Double,
    var currency: Currency,
    var category: ExpensesCategory,
    var note: String,
    var date: String,
    var timeStamp: Date,
):Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}