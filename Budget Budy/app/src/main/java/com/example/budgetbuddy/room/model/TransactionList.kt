package com.example.budgetbuddy.room.model

import com.example.budgetbuddy.room.transactions_table.TransactionsTable
import java.util.*

data class TransactionList(
    var header: Date,
    var child: List<TransactionsTable>
)
