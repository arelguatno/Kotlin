package com.example.budgetbuddy.room.tables

import java.util.*

data class TransactionList(
    var header: Date,
    var child: List<TransactionsTable>
)
