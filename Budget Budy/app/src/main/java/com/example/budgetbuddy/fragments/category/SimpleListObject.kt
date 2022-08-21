package com.example.budgetbuddy.fragments.category

import java.io.Serializable

data class SimpleListObject(
    val imageID: Int,
    val rowValue: String,
    val uniqueID: Int,
    val textIcon: String,
    val currencySign: String,
):Serializable