package com.example.budgetbuddy

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.budgetbuddy.screens.settings_screen.SettingsFragmentViewModel
import com.google.android.gms.ads.AdView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
abstract class MainFragment : Fragment() {
    @Inject lateinit var digitsConverter: DigitsConverter
     internal val settingsViewModel: SettingsFragmentViewModel by viewModels()

    companion object {
        internal lateinit var sharedPref: SharedPreferences
        const val TAG = "MainFragment"
        val cal: Calendar = Calendar.getInstance()
        const val VERSION_NAME = BuildConfig.VERSION_NAME
        const val VERSION_CODE = BuildConfig.VERSION_CODE
    }
    fun <B> LogStr(param: B) {
        Log.d(TAG, param.toString())
    }

    fun <T> showShortToastMessage(param: T) {
        Toast.makeText(context, param.toString(), Toast.LENGTH_SHORT).show()
    }

    fun <A> showLongToastMessage(param: A) {
        Toast.makeText(context, param.toString(), Toast.LENGTH_LONG).show()
    }

    fun showAds(): Boolean{
        return !settingsViewModel.getPremiumUser()
    }
}