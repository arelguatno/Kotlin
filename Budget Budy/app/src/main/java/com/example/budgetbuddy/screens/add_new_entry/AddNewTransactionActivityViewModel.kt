package com.example.budgetbuddy.screens.add_new_entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.fragments.category.SimpleListObject
import com.example.budgetbuddy.fragments.currency.CurrencyList
import com.example.budgetbuddy.room.transactions_table.TransactionsTable
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddNewTransactionActivityViewModel @Inject constructor() : ViewModel() {

    private val category = MutableLiveData<SimpleListObject>()
    private val note = MutableLiveData<String>()
    private val currency = MutableLiveData<SimpleListObject>()
    private val date = MutableLiveData<Date>()
    private val price = MutableLiveData<String>()
    private val table = MutableLiveData<TransactionsTable>()
    private val dateLong = MutableLiveData(Date().time)

    fun setDateLong(v: Long){
        dateLong.value = v
    }

    fun getDateLong(): LiveData<Long> {
        return dateLong
    }

    fun setNote(v: String) {
        note.value = v
    }

    fun getNote(): LiveData<String> {
        if(!note.value.isNullOrEmpty()) {
            note.value = note.value.toString()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        }
        return note
    }

    fun getCategory(): LiveData<SimpleListObject> {
        return category
    }

    fun setCategory(v: SimpleListObject) {
        category.value = v
    }

    fun setDate(v: Date) {
        date.value = v
    }

    fun getDate(): LiveData<Date> {
        return date
    }

    fun setPrice(v: String) {
        price.value = v
    }

    fun getPrice(): LiveData<String> {
        return price
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

    fun setTable(v: TransactionsTable) {
        table.value = v
    }

    fun getTable(): LiveData<TransactionsTable> {
        return table
    }
}