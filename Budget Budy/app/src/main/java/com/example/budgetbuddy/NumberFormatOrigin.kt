package com.example.budgetbuddy

import android.content.Context
import com.example.budgetbuddy.fragments.currency.CurrencyList
import java.text.DecimalFormat

class NumberFormatOrigin(val context: Context) {
    private val numberFormat = DecimalFormat("#,##0.00")

    fun getSavedCurrency(): Int {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.PREFERENCE_CURRENCY_ID),
            Context.MODE_PRIVATE
        )!!
        return sharedPref!!.getInt(context.getString(R.string.PREFERENCE_CURRENCY_ID), 1)
    }

    fun <b> format(value: b): String {
        return "${currencySign()} ${decimalFormat(value)} "
    }

    private fun <c> decimalFormat(value: c): String {
        return numberFormat.format(value)
    }

    private fun currencySign(): String {
        val currentC = CurrencyList.getCurrencyObj(getSavedCurrency())
        return currentC!!.currencySign
    }
}