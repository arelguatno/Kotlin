package com.example.budgetbuddy.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.budgetbuddy.room.transactions_table.TransactionsTable
import com.example.budgetbuddy.room.wallet_table.Wallets
import com.example.budgetbuddy.room.transactions_table.TransactionsDao
import com.example.budgetbuddy.room.type_converter.DateConverter
import com.example.budgetbuddy.room.wallet_table.WalletsDao

@Database(
    entities = [TransactionsTable::class, Wallets::class],
    version = 1,
    exportSchema = false
)@TypeConverters(DateConverter::class)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun profileDao(): TransactionsDao
    abstract fun walletDao(): WalletsDao
}