package com.example.budgetbuddy.room.tables

import androidx.room.*
import com.example.budgetbuddy.fragments.category.SimpleListObject
import java.io.Serializable
import java.time.temporal.TemporalAmount
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
    @Embedded(prefix = "time_range_")
    var time_range: DateRange,
    var catAmount: Double = 0.0,
    var percentage: Double = 0.0
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

data class DateRange(
    val day: Int,
    val week: Int,
    val month: Int,
    val quarter: Int,
    val year: Int
):Serializable
