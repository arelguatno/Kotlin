package com.example.budgetbuddy.room.wallet_table

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "wallets_table")
data class Wallets(
    var name: String = "",
    var primary: Boolean? = false
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
