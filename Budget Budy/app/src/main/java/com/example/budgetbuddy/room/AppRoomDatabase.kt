package com.example.budgetbuddy.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.budgetbuddy.room.tables.TransactionsTable
import com.example.budgetbuddy.room.type_converter.DateConverter

@Database(
    entities = [TransactionsTable::class],
    version = 1,
    exportSchema = false
)@TypeConverters(DateConverter::class)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun profileDao(): TransactionsDao

}