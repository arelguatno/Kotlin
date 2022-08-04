package com.example.budgetbuddy.utils

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

fun transformSingleDigitToTwoDigit(num: Int): String {
    return if(num <= 9){
        return "0$num"
    }else{
        return "$num"
    }
}