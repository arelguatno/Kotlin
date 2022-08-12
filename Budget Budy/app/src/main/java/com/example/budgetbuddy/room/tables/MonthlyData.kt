package com.example.budgetbuddy.room.tables

import java.util.*

data class MonthlyData(
    var monthy: String,
    var child: List<TransactionList>
)
