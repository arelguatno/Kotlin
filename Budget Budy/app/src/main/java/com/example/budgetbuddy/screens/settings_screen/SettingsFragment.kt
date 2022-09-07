package com.example.budgetbuddy.screens.settings_screen

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.android.billingclient.api.QueryProductDetailsParams.Product
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentSettingsBinding
import com.google.common.collect.ImmutableList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : MainFragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsFragmentViewModel by activityViewModels()
    private lateinit var billingClient: BillingClient

    companion object {
        lateinit var txtPremiumLabel: TextView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        checkIfPremium()
        initBilling()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
        initViewModelObserver()
        viewModel.setCurrency(digitsConverter.getCurrencySettings())
        viewModel.setThemes(digitsConverter.getThemesID())

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

        viewModel.viewModelScope.launch {
            delay(1500)
            checkIfPremium()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtPremiumLabel = requireActivity().findViewById(R.id.txtPremium)
    }

    private fun checkIfPremium() {
        if (viewModel.getPremiumUser()) {
            viewModel.setUserTypeString("Premium")
        } else {
            viewModel.setUserTypeString("Get Premium")
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
                } else if (billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
                    if (!viewModel.ifRestored()) {
                        viewModel.setRestoredYes()
                        viewModel.setUserPurchasedPremiumTrue()
                        showShortToastMessage("Premium has been restored. Thank you.")
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
                LogStr("Purchase Complete. Thank you!")
                viewModel.setRestoredYes()
                viewModel.setUserPurchasedPremiumTrue()
            }
        }
    }

    private fun initViewModelObserver() {
        viewModel.getCurrency().observe(viewLifecycleOwner) {
            binding.settingsDisplay.txtCurrency.text = it.textIcon.substring(0, 3)
        }

        viewModel.getThemes().observe(viewLifecycleOwner){
            binding.settingsDisplay.txtTheme.text = it.currencySign
        }

        //TODO error
        viewModel.getUserTypeString().observe(viewLifecycleOwner) {
            txtPremiumLabel.text = it
        }
    }

    private fun initViews() {
        binding.settingsDisplay.selectLanguage.setOnClickListener {
            showShortToastMessage("More languages soon")
//            viewModel.setRestoredFalse()
//            viewModel.setUserPurchasedPremiumFalse()
//            checkIfPremium()
        }

        binding.settingsDisplay.currency.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToCurrencyFragment()
            findNavController().navigate(action)
        }

        binding.settingsAbout.about.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
        }

        binding.settingsAbout.feedback.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingsFragment_to_feedbackFragment)
        }

        binding.settingsDisplay.themes.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingsFragment_to_themesFragment)
        }
    }
}