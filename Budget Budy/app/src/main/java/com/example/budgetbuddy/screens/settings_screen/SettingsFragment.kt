package com.example.budgetbuddy.screens.settings_screen

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.android.billingclient.api.QueryProductDetailsParams.Product
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentSettingsBinding
import com.google.common.collect.ImmutableList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : MainFragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsFragmentViewModel by activityViewModels()
    private lateinit var billingClient: BillingClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        initBilling()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
        initViewModelObserver()
        viewModel.setCurrency(digitsConverter.getCurrencySettings())

        binding.settingsAppName.versionNumber.text = VERSION_NAME

        binding.settingsPremium.removeAds.setOnClickListener {
            connectToGooglePlayBilling()
        }
    }

    override fun onResume() {
        super.onResume()
        billingClient.queryPurchasesAsync(
            BillingClient.ProductType.INAPP
        ) { billingResult, list ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                for (purchase in list) {
                    if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged) {
                        verifyPurchase(purchase)
                    }
                }
            }
        }
    }

    private fun connectToGooglePlayBilling() {
        billingClient.startConnection(
            object : BillingClientStateListener {
                override fun onBillingServiceDisconnected() {
                    connectToGooglePlayBilling()
                }

                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        getProductDetails()
                    }
                }
            }
        )
    }

    private fun getProductDetails() {
        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                ImmutableList.of(
                    Product.newBuilder()
                        .setProductId(getString(R.string.in_app_product_id))
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build()
                )
            )
            .build()
        val activity: Activity = requireActivity()
        billingClient.queryProductDetailsAsync(
            queryProductDetailsParams
        ) { billingResult, list ->

            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val itemInfo = list[0]
                val productDetailsParamsList = listOf(
                    ProductDetailsParams.newBuilder().setProductDetails(itemInfo).build()
                )
                val billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(productDetailsParamsList)
                    .build()
                billingClient.launchBillingFlow(activity, billingFlowParams)
            }
        }
    }

    private fun initBilling() {
        billingClient = BillingClient.newBuilder(requireContext())
            .enablePendingPurchases()
            .setListener { billingResult, list ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && list != null) {
                    for (purchase in list) {
                        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged) {
                            verifyPurchase(purchase)
                        }
                    }
                }
            }.build()
    }

    private fun verifyPurchase(purchase: Purchase) {
        val acknowledgePurchaseParams =
            AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()
        billingClient.acknowledgePurchase(
            acknowledgePurchaseParams
        ) { billingResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                showShortToastMessage("Thank you")
            }
        }
    }

    private fun initViewModelObserver() {
        viewModel.getCurrency().observe(viewLifecycleOwner) {
            binding.settingsDisplay.txtCurrency.text = it.textIcon.substring(0, 3)
        }
    }

    private fun initViews() {
        binding.settingsDisplay.selectLanguage.setOnClickListener {
            showShortToastMessage("Soon")
        }

        binding.settingsDisplay.currency.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToCurrencyFragment()
            findNavController().navigate(action)
        }
    }
}