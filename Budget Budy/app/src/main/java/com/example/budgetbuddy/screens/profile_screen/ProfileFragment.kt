package com.example.budgetbuddy.screens.profile_screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.databinding.FragmentProfileBinding
import com.example.budgetbuddy.screens.reportingperiod.ReportingPeriodActivity
import com.example.budgetbuddy.screens.wallets.WalletActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProfileFragment : MainFragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        seeMyWallet()
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initViews()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAd()
    }

    private fun loadAd() {
        MobileAds.initialize(requireContext()) {}
        val mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    private fun initViews() {
        binding.walletDisplay.profileReport.setOnClickListener {
            val intent = Intent(requireContext(), ReportingPeriodActivity::class.java).apply {
                putExtra(ReportingPeriodActivity.DATE_DATA, Date()) // Current Month
            }
            startActivity(intent)
        }
    }

    private fun seeMyWallet() {
        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

                if (result.resultCode == Activity.RESULT_OK) {
                    readSelectedWallet()
                }
            }

        binding.walletDisplay.profileMyWallets.setOnClickListener {
            val intent = Intent(requireContext(), WalletActivity::class.java)
            startForResult.launch(intent)
        }
    }

    private fun readSelectedWallet() {
        showShortToastMessage("Wallet Loaded Successfully")
    }

}