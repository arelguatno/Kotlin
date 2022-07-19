package com.example.room_aye.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.material.timepicker.TimeFormat
import java.util.*

@Entity(tableName = "transaction_table")
data class TransactionsTable(
    var amount: Double,
    var cateogry: String,
    var note: String,
    var date: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}