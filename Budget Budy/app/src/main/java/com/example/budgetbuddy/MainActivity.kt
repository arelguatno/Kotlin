package com.example.budgetbuddy

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.budgetbuddy.databinding.ActivityMainBinding
import com.example.budgetbuddy.fragments.category.CategoryList
import com.example.budgetbuddy.fragments.currency.CurrencyList
import com.example.budgetbuddy.fragments.onboarding.viewpager.ViewPagerActivity
import com.example.budgetbuddy.fragments.themes.ThemesAdapter
import com.example.budgetbuddy.fragments.themes.ThemesFragment
import com.example.budgetbuddy.fragments.themes.ThemesList
import com.example.budgetbuddy.room.transactions_table.TransactionsTable
import com.example.budgetbuddy.room.wallet_table.Wallets
import com.example.budgetbuddy.screens.add_new_entry.AddNewEntryTransactionFragment
import com.example.budgetbuddy.screens.add_new_entry.AddNewTransactionActivity
import com.example.budgetbuddy.screens.transactions_screen.TransactionViewModel
import com.example.budgetbuddy.screens.wallets.WalletViewModel
import com.example.budgetbuddy.utils.ContextUtils
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity(), Serializable {
    @Inject lateinit var digitsConverter: DigitsConverter
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: TransactionViewModel by viewModels()
    private val walletViewModel: WalletViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNav()
        onFloatingAction()
        loadCategoryCurrency()
        createDefaultWallet()
        loadTheme()
    }

    private fun loadTheme() {
        ThemesFragment.setTheme(digitsConverter.getThemesID())
    }

    private fun loadCategoryCurrency() {
        //Load Category,Currency, Theme
        CategoryList.geItems()
        CurrencyList.geItems()
        ThemesList.geItems()
    }

    private fun createDefaultWallet(){
        walletViewModel.fetchPrimaryWallet.observe(this, Observer {
            if (it.isEmpty()) {
                val wallet = Wallets(name = "Personal", primary_wallet = true)  //Fresh installed, always creates Personal wallet with ID of 1
                walletViewModel.insertWallet(wallet)
            }
        })
    }

    private fun onFloatingAction() {
        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

                if (result.resultCode == Activity.RESULT_OK) {
                    if (result.data?.getSerializableExtra(AddNewEntryTransactionFragment.ADD_NEW_ENTRY) != null) saveNewEntry(
                        result.data
                    )
                }
            }

        binding.floatingActionButton.setOnClickListener {
            startForResult.launch(Intent(this, AddNewTransactionActivity::class.java).apply {
                putExtra(AddNewEntryTransactionFragment.ADD_NEW_ENTRY, viewModel.getUserDate().value)
            })
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavigationView, navController)

//        binding.bottomNavigationView.setOnItemSelectedListener {
//            val navController = findNavController(R.id.nav_host_fragment)
//
//           // NavigationUI.onNavDestinationSelected(it,navController)
//            when (it.itemId) {
//
////                R.id.transactionFragment -> {
////                    navController.navigate(R.id.transactionFragment)
////                  //  NavigationUI.onNavDestinationSelected(it,navController)
////                    return@setOnItemSelectedListener true
////                }
//                R.id.homeFragment -> {
//
//                    navController.navigate(R.id.homeFragment)
//                    return@setOnItemSelectedListener true
//                }
////                R.id.profileFragment -> {
////                    navController.navigate(R.id.profileFragment)
////                    return@setOnItemSelectedListener true
////                }
////                R.id.settingsFragment -> {
////                    navController.navigate(R.id.settingsFragment)
////                    return@setOnItemSelectedListener true
////                }
//            }
//            false
//        }
    }

    private fun saveNewEntry(data: Intent?) {
        data?.getSerializableExtra(AddNewEntryTransactionFragment.ADD_NEW_ENTRY)?.let {
            viewModel.setUserDate((it as TransactionsTable).date!!)
            viewModel.insertProfileRecord(it as TransactionsTable)
            viewModel.setRefreshTransaction(true)
        }
    }

    override fun onStart() {
        super.onStart()

        if (!onBoardingFinished()) {
            startActivity(Intent(this, ViewPagerActivity::class.java))
            finish()
        }
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = this.getSharedPreferences(
            getString(R.string.on_boarding_shared_pref),
            Context.MODE_PRIVATE
        )
        return sharedPref.getBoolean(getString(R.string.on_boarding_finised), false)
    }
}

