package com.example.room_aye.room.tables


data class TransactionList(
    var header: String,
    var child: List<TransactionsTable>
)
