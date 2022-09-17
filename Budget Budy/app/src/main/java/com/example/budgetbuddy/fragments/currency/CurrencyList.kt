package com.example.budgetbuddy.fragments.currency

import com.example.budgetbuddy.R
import com.example.budgetbuddy.fragments.category.SimpleListObject


object CurrencyList {

    val list = HashMap<Int, SimpleListObject>()

    // Note - Symbol must always 3 letters

    fun geItems(): List<Pair<Int, SimpleListObject>> {
        // 1 is the constant and unique value for Currency items and so on
        list[1] = SimpleListObject(R.drawable.united_states, "United States Dollar", 1, "USD-$","$")
        list[2] = SimpleListObject(R.drawable.philippines, "Philippines Pesos", 2, "PHP-₱","₱")
        list[3] = SimpleListObject(R.drawable.united_kingdom, "UK Pound", 3, "GBP-£","£")
        list[4] = SimpleListObject(R.drawable.european_union, "Euro", 4, "EUR-€","€")
        list[5] = SimpleListObject(R.drawable.india, "India Rupee", 5, "INR-₹","₹")
        list[6] = SimpleListObject(R.drawable.saudi, "Saudi Arabian Riyal", 6, "SAR-SR","SR")
        return list.toList().sortedBy { (key, value) -> value.rowValue }
    }

    fun getCurrencyObj(v: Int): SimpleListObject?{
         return list[v]
    }
}