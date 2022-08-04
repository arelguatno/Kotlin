package com.example.budgetbuddy.fragments.category

import com.example.budgetbuddy.R

object CategoryList {

    val list = HashMap<Int, SimpleListObject>()

    fun geItems(): List<Pair<Int, SimpleListObject>> {
        // 1 is the constant and unique value for Category items and so on
        list[1] = SimpleListObject(R.drawable.ic_category_personal, "Personal", 1)
        list[2] = SimpleListObject(R.drawable.ic_baseline_directions_bus_24, "Transportation", 2)
        list[3] = SimpleListObject(R.drawable.ic_baseline_local_dining_24, "Food & Beverages", 3)
        list[4] = SimpleListObject(R.drawable.ic_baseline_home_24, "Bills", 4)
        list[5] = SimpleListObject(R.drawable.ic_baseline_calendar_month_24, "Clothing", 5)
        list[6] = SimpleListObject(R.drawable.ic_baseline_subscriptions_24, "Subscription", 6)
        list[7] = SimpleListObject(R.drawable.ic_baseline_sports_esports_24, "Entertainment", 7)
        list[8] = SimpleListObject(R.drawable.ic_baseline_apps_24, "Apps", 8)
        list[9] = SimpleListObject(R.drawable.ic_baseline_assignment_24, "Other Expenses", 8)
        list[9] = SimpleListObject(R.drawable.ic_baseline_microwave_24, "HouseWare", 9)

        return list.toList()
    }

    fun getRowValue(v: Int): String {
        return list[v]!!.rowValue
    }

    fun getImageSrc(v: Int): Int {
        return list[v]!!.imageID
    }


}