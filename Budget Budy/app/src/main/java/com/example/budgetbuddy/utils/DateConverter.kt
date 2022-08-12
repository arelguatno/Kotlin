package com.example.budgetbuddy.utils

import java.text.DateFormat
import java.time.LocalDate
import java.util.*

fun dateToNice(v: Date): String {
    val calendar = Calendar.getInstance()
    calendar.time = v

    val dayOfWeek: String = intDayToString(calendar.get(Calendar.DAY_OF_WEEK))
    val monthDay: String = calendar.get(Calendar.DAY_OF_MONTH).toString()
    val month: String = intMonthLongToString(calendar.get(Calendar.MONTH))
    val year: String = calendar.get(Calendar.YEAR).toString()

    return "$dayOfWeek, $monthDay $month $year"
}

fun dateYyyyMmDd(v: Date): String {
    var cal: Calendar = Calendar.getInstance()
    cal.time = v

    return "${cal.get(Calendar.YEAR)}/${cal.get(Calendar.MONTH) + 1}/${cal.get(Calendar.DAY_OF_MONTH)}"
}

fun getDateMonth(v: String): Int{
    var cal: Calendar = Calendar.getInstance()
    cal.time = Date(v)
    return cal.get(Calendar.MONTH)
}

fun getDateYear(v: String): Int{
    var cal: Calendar = Calendar.getInstance()
    cal.time = Date(v)
    return cal.get(Calendar.YEAR)
}
