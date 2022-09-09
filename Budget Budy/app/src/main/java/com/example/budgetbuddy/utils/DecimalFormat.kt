package com.example.budgetbuddy.utils

import java.text.DecimalFormat

open class DecimalFormat {
    internal val numberFormat = DecimalFormat("#,##0.00")
    internal val plainFormat = DecimalFormat("####.##")
}