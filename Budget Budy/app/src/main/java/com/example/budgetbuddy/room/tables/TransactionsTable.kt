package com.example.budgetbuddy.room.tables

import androidx.room.*
import com.example.budgetbuddy.fragments.category.SimpleListObject
import java.io.Serializable
import java.time.temporal.TemporalAmount
import java.util.*

@Entity(tableName = "transactions_table")
data class TransactionsTable(
    var amount: Double,
    var note: String? = "",
    var date: Date? = Date(),
    var timeStamp: Date? = Date(),
    @Embedded(prefix = "currency_")
    var currency: SimpleListObject?,
    @Embedded(prefix = "category_")
    var category: SimpleListObject?,
    @Embedded(prefix = "time_range_")
    var time_range: DateRange?,
    var catAmount: Double = 0.00,
    var percentage: Double = 0.00
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

data class DateRange(
    val day: Int = 0,
    val week: Int = 0,
    val month: Int = 0,
    val quarter: Int = 0,
    val year: Int = 0
) : Serializable
