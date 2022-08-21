package com.example.budgetbuddy.screens.settings_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.fragments.category.SimpleListObject
import com.example.budgetbuddy.fragments.currency.CurrencyList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(): ViewModel() {
    private val currency = MutableLiveData<SimpleListObject>()

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
}