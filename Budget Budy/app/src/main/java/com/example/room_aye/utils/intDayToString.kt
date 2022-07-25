package com.example.room_aye.utils

import java.util.*

fun intDayToString(param: Int): String {
    return when (param) {
        Calendar.MONDAY -> "Monday"
        Calendar.TUESDAY -> "Tuesday"
        Calendar.WEDNESDAY-> "Wednesday"
        Calendar.THURSDAY-> "Thursday"
        Calendar.FRIDAY -> "Friday"
        Calendar.SATURDAY -> "Saturday"
        Calendar.SUNDAY -> "Sunday"
        else -> "Sunday"
    }
}