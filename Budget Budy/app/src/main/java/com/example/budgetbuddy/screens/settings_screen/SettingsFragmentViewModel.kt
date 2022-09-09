package com.example.budgetbuddy.screens.settings_screen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.billingclient.api.*
import com.example.budgetbuddy.R
import com.example.budgetbuddy.fragments.category.SimpleListObject
import com.example.budgetbuddy.fragments.currency.CurrencyList
import com.example.budgetbuddy.fragments.themes.ThemesList
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(val context: Context) : ViewModel() {
    private val currency = MutableLiveData<SimpleListObject>()
    private val themes = MutableLiveData<SimpleListObject>()
    private val purchased = MutableLiveData(false)
    private val userTypeString = MutableLiveData<String>()
    private lateinit var billingClient: BillingClient
    private val dateLong = MutableLiveData(Date())

    fun setDateLong(v: Date){
        dateLong.value = v
    }

    fun getDateLong(): LiveData<Date> {
        return dateLong
    }

    fun setUserTypeString(v: String) {
        userTypeString.value = v
    }

    fun getUserTypeString(): LiveData<String> {
        return userTypeString
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

    fun getThemes(): LiveData<SimpleListObject> {
        return themes
    }

    fun setThemes(v: Int): LiveData<SimpleListObject> {
        val currentC = ThemesList.getCurrencyObj(v)
        if (currentC == null) {
            ThemesList.geItems()
            themes.value = ThemesList.getCurrencyObj(v)
        } else {
            themes.value = currentC!!
        }
        return themes
    }

    fun ifRestored(): Boolean {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.purchase_restored),
            Context.MODE_PRIVATE
        )
        return sharedPref!!.getBoolean(context.getString(R.string.purchase_restored), false)
    }

    fun setRestoredYes() {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.purchase_restored),
            Context.MODE_PRIVATE
        )
        with(sharedPref.edit()){
            putBoolean(context.getString(R.string.purchase_restored), true)
            apply()
        }
    }

    fun getPremiumUser(): Boolean {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.user_purchased_premium),
            Context.MODE_PRIVATE
        )
        return sharedPref.getBoolean(context.getString(R.string.user_purchased_premium), false)
    }

    fun setUserPurchasedPremiumTrue() {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.user_purchased_premium),
            Context.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            putBoolean(context.getString(R.string.user_purchased_premium), true)
            apply()
        }
    }

    fun setUserPurchasedPremiumFalse() {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.user_purchased_premium),
            Context.MODE_PRIVATE
        )
        with(sharedPref.edit()){
            putBoolean(context.getString(R.string.user_purchased_premium), false)
            apply()
        }
    }

    fun setRestoredFalse() {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.purchase_restored),
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putBoolean(context.getString(R.string.purchase_restored), false)
        editor.apply()
    }

}