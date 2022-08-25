package com.example.budgetbuddy.fragments.category

import com.example.budgetbuddy.R
import com.example.budgetbuddy.fragments.currency.CurrencyList
import com.example.budgetbuddy.utils.getIncomeID

object CategoryList {

    val list = HashMap<Int, SimpleListObject>()

    fun geItems(): List<Pair<Int, SimpleListObject>> {
        // 1 is the constant and unique value for Category items and so on
        list[1] = SimpleListObject(R.drawable.ic_category_personal, "Personal", 1,"","")
        list[2] = SimpleListObject(R.drawable.ic_baseline_directions_bus_24, "Transportation", 2,"","")
        list[3] = SimpleListObject(R.drawable.ic_baseline_food, "Food & Beverages", 3,"","")
        list[4] = SimpleListObject(R.drawable.ic_baseline_home_24, "Bills", 4,"","")
        list[5] = SimpleListObject(R.drawable.ic_fashion, "Clothing", 5,"","")
        list[6] = SimpleListObject(R.drawable.ic_baseline_subscriptions_24, "Subscription", 6,"","")
        list[7] = SimpleListObject(R.drawable.ic_baseline_sports_esports_24, "Entertainment", 7,"","")
        list[8] = SimpleListObject(R.drawable.ic_baseline_apps_24, "Apps", 8,"","")
        list[9] = SimpleListObject(R.drawable.ic_baseline_handyman_24, "HouseWare", 9,"","")
        list[10] = SimpleListObject(R.drawable.ic_baseline_medication_24, "Health", 10,"","")
        list[11] = SimpleListObject(R.drawable.ic_baseline_income_24, "Income", getIncomeID(),"","") // permanent ID, don't change it
        list[12] = SimpleListObject(R.drawable.ic_baseline_circle_24, "Other Expenses", 12,"","")

        return list.toList().sortedBy { (key, value) -> value.rowValue }
    }

    fun getImageID(v: Int): Int?{
        return CategoryList.list[v]?.imageID
    }
}