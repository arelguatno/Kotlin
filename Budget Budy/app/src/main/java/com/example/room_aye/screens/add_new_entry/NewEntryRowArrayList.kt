package com.example.room_aye.screens.add_new_entry

import com.example.room_aye.R

object NewEntryRowArrayList {
    fun getRowListItems(): ArrayList<NewEntryItemsUI> {
        val list = ArrayList<NewEntryItemsUI>()

        val item1 = NewEntryItemsUI(R.drawable.ic_baseline_circle_24, "Category")
        list.add(item1)

        val item2= NewEntryItemsUI(R.drawable.ic_baseline_notes_24, "Note")
        list.add(item2)

        val item3= NewEntryItemsUI(R.drawable.ic_baseline_calendar_month_24, "Today")
        list.add(item3)


        return list
    }
}