package com.example.budgetbuddy.screens.settings_screen

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.billingclient.api.*
import com.example.budgetbuddy.fragments.category.SimpleListObject
import com.example.budgetbuddy.fragments.currency.CurrencyList
import com.google.common.collect.ImmutableList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor() : ViewModel() {
    private val currency = MutableLiveData<SimpleListObject>()
    private val purchased = MutableLiveData(false)
    private lateinit var billingClient: BillingClient

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