package com.example.budgetbuddy.fragments.themes

import com.example.budgetbuddy.R
import com.example.budgetbuddy.fragments.category.SimpleListObject


object ThemesList {

    val list = HashMap<Int, SimpleListObject>()

    fun geItems(): List<Pair<Int, SimpleListObject>> {
        // 1 is the constant and unique value for Currency items and so on
        list[1] = SimpleListObject(R.drawable.ic_baseline_highlight_24, "Light", 1, "","Light")
        list[2] = SimpleListObject(R.drawable.ic_baseline_dark_mode_24, "Dark", 2, "","Dark")
        list[3] = SimpleListObject(R.drawable.ic_baseline_app_settings_alt_24, "System Settings", 3, "","System")
        return list.toList()
    }

    fun getCurrencyObj(v: Int): SimpleListObject?{
         return list[v]
    }
}