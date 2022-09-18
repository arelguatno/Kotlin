package com.example.budgetbuddy.utils

import java.text.SimpleDateFormat
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

fun getDateMonth(v: String): Int {
    var cal: Calendar = Calendar.getInstance()
    cal.time = Date(v)
    return cal.get(Calendar.MONTH)
}

fun getDateYear(v: String): Int {
    var cal: Calendar = Calendar.getInstance()
    cal.time = Date(v)
    return cal.get(Calendar.YEAR)
}

fun getDateWeek(v: String): Int {
    var cal: Calendar = Calendar.getInstance()
    cal.time = Date(v)
    return cal.get(Calendar.WEEK_OF_YEAR)
}

fun getDateDay(v: String): Int {
    var cal: Calendar = Calendar.getInstance()
    cal.time = Date(v)
    return cal.get(Calendar.DAY_OF_MONTH)
}

fun getDateQuarter(v: String): Int {
    var cal: Calendar = Calendar.getInstance()
    cal.time = Date(v)
    return when (cal.get(Calendar.MONTH)) {
        in 1..3 -> 1
        in 4..6 -> 2
        in 7..9 -> 3
        in 10..12 -> 4
        else -> {
            1
        }
    }
}

fun getCurrentMonth(): Int {
    var cal = Calendar.getInstance()
    cal.time = Date()
    return cal.get(Calendar.MONTH)
}

fun getCurrentYear(): Int {
    var cal = Calendar.getInstance()
    cal.time = Date()
    return cal.get(Calendar.YEAR)
}
