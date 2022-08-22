package com.example.budgetbuddy

import android.content.Context
import com.example.budgetbuddy.fragments.currency.CurrencyList
import java.text.DecimalFormat

class DigitsConverter(val context: Context) {
    private val numberFormat = DecimalFormat("#,##0.00")
    private val sharedPref =
        context.getSharedPreferences(
            context.getString(R.string.global_currency_id),
            Context.MODE_PRIVATE
        )!!

    // Global Digits Format
    fun <b> formatWithCurrency(value: b): String {
        return "${currencySign()} ${decimalFormat(value)} "
    }

    private fun <c> decimalFormat(value: c): String {
        return numberFormat.format(value)
    }

    private fun currencySign(): String {
        val currentC = CurrencyList.getCurrencyObj(getCurrencySettings())
        return currentC!!.currencySign
    }

    fun getCurrencySettings(): Int {
        return sharedPref!!.getInt(context.getString(R.string.global_currency_id), 1)
    }

    fun getCurrencyNewEntry(): Int {
        return sharedPref!!.getInt(context.getString(R.string.currency_id), 1)
    }
}