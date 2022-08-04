package com.example.budgetbuddy.fragments.currency

import com.example.budgetbuddy.R
import com.example.budgetbuddy.fragments.category.CategoryList
import com.example.budgetbuddy.fragments.category.SimpleListObject

object CurrencyList {

    val list = HashMap<Int, SimpleListObject>()

    fun geItems(): List<Pair<Int, SimpleListObject>> {
        // 1 is the constant and unique value for Currency items and so on
        list[1] = SimpleListObject(R.drawable.ic_baseline_currency_bitcoin_24, "USD",1)
        list[2] = SimpleListObject(R.drawable.ic_baseline_currency_pound_24, "GBP",2)
        list[3] = SimpleListObject(R.drawable.ic_baseline_currency_yuan_24, "YEN",3)
        list[4] = SimpleListObject(R.drawable.ic_baseline_currency_ruble_24, "PHP",4)

        return list.toList()
    }

    fun getRowValue(v: Int): String {
        return list[v]!!.rowValue
    }

    fun getImageSrc(v: Int): Int {
        return list[v]!!.imageID
    }

    fun getCurrencyObj(v: Int): SimpleListObject?{
         return list.get(v)
    }
}