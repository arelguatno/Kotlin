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
        list[10] = SimpleListObject(R.drawable.ic_baseline_medication_24, "Health", 10,"","")
        list[5] = SimpleListObject(R.drawable.ic_fashion, "Clothing", 5,"","")
        list[25] = SimpleListObject(R.drawable.ic_baseline_storefront_24, "Groceries", 25,"","")
        list[24] = SimpleListObject(R.drawable.ic_baseline_directions_car_24, "Vehicle", 24,"","")
        list[7] = SimpleListObject(R.drawable.ic_baseline_sports_esports_24, "Entertainment", 7,"","")
        list[22] = SimpleListObject(R.drawable.ic_baseline_shopping_cart_24, "Shopping", 22,"","")
        list[23] = SimpleListObject(R.drawable.ic_baseline_sell_24, "Online", 23,"","")
        list[8] = SimpleListObject(R.drawable.ic_baseline_apps_24, "Apps", 8,"","")
        list[9] = SimpleListObject(R.drawable.ic_baseline_handyman_24, "HouseWare", 9,"","")
        list[11] = SimpleListObject(R.drawable.ic_baseline_income_24, "Income", getIncomeID(),"","") // permanent ID, don't change it
        list[12] = SimpleListObject(R.drawable.ic_baseline_circle_24, "Other Expenses", 12,"","")
        list[13] = SimpleListObject(R.drawable.ic_baseline_import_export_24, "Money Transfer", 13,"","")//new
        list[14] = SimpleListObject(R.drawable.ic_baseline_show_investment_24, "Investment", 14,"","")
        list[15] = SimpleListObject(R.drawable.ic_baseline_card_giftcard_24, "Gifts & Donations", 15,"","")
        list[16] = SimpleListObject(R.drawable.ic_baseline_girl_24, "Beauty", 16,"","")//new
        list[17] = SimpleListObject(R.drawable.ic_baseline_fitness_center_24, "Fitness", 17,"","")
        list[18] = SimpleListObject(R.drawable.ic_baseline_pets_24, "Pets", 18,"","")
        list[6] = SimpleListObject(R.drawable.ic_baseline_subscriptions_24, "Subscription", 6,"","")
        list[19] = SimpleListObject(R.drawable.ic_baseline_soap_24, "Toiletries", 19,"","")
        list[20] = SimpleListObject(R.drawable.ic_baseline_account_balance_24, "Loan", 20,"","")//new
        list[21] = SimpleListObject(R.drawable.ic_baseline_school_24, "Education", 21,"","")


        return list.toList()
        //return list.toList().sortedBy { (key, value) -> value.rowValue }
    }

    fun getImageID(v: Int): Int?{
        return CategoryList.list[v]?.imageID
    }
}