package com.example.budgetbuddy.room.tables

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.budgetbuddy.fragments.category.SimpleListObject
import java.io.Serializable
import java.util.*

@Entity(tableName = "transactions_table")
data class TransactionsTable(
    var amount: Double,
    var note: String,
    var date: Date,
    var timeStamp: Date,
    @Embedded(prefix = "currency")
    var currency: SimpleListObject,
    @Embedded(prefix = "category")
    var category: SimpleListObject,
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
