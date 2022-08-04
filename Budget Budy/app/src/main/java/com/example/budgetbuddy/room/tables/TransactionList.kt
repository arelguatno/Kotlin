package com.example.budgetbuddy.room.tables


data class TransactionList(
    var header: String,
    var child: List<TransactionsTable>
)
