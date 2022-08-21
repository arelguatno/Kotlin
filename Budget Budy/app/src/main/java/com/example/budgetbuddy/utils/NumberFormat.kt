package com.example.budgetbuddy.utils

import java.text.DecimalFormat

fun <b> numberFormat(value: b): String{
    val decimalFormat = DecimalFormat("#,##0.00")
    return "£ ${decimalFormat.format(value)}"
}