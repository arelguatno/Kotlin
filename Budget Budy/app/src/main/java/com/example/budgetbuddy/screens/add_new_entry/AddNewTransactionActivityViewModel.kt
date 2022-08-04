package com.example.budgetbuddy.screens.add_new_entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.fragments.category.CategoryList
import com.example.budgetbuddy.fragments.category.SimpleListObject
import com.example.budgetbuddy.fragments.currency.CurrencyList
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddNewTransactionActivityViewModel @Inject constructor() : ViewModel() {

    internal val category = MutableLiveData<SimpleListObject>()
    internal val note = MutableLiveData<String>()
    internal val currency = MutableLiveData<SimpleListObject>()
    internal val date = MutableLiveData<Date>()

    fun setNote(v: String) {
        note.value = v
    }

    fun getNote(): LiveData<String> {
        return note
    }

    fun getCategory(): LiveData<SimpleListObject> {
        return category
    }

    fun setCategory(v: SimpleListObject) {
        category.value = v
    }

    fun getCurrency(): LiveData<SimpleListObject> {
        return currency
    }

    fun setCurrency(v: Int): LiveData<SimpleListObject> {
        val currentC = CurrencyList.getCurrencyObj(v)
        if (currentC == null) {
            CurrencyList.geItems()
            currency.value = CurrencyList.getCurrencyObj(v)
        } else {
            currency.value = currentC!!
        }
        return currency
    }

    fun setDate(v: Date) {
        date.value = v
    }

    fun getDate(): LiveData<Date> {
        return date
    }


}