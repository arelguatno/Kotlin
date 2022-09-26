package com.example.noteapp.dawernavigation

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.noteapp.dawernavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavHost()
        initMenuDrawer()
        initOnBackPressed()
    }

    private fun initMenuDrawer() {
        toggle = ActionBarDrawerToggle(
            this@MainActivity,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener {
            handleMenuClick(it)
            true
        }
    }

    private fun handleMenuClick(it: MenuItem) {
        when (it.itemId) {
            R.id.homeItem -> {
                navigateToFragment(getString(R.string.home_item), R.id.homeFragment)
            }
            R.id.firstItem -> {
                navigateToFragment(getString(R.string.message), R.id.firstFragment)
            }
            R.id.secondItem -> {
                navigateToFragment(getString(R.string.snyc), R.id.secondFragment)
            }
            R.id.thirdItem -> {
                navigateToFragment(getString(R.string.settings), R.id.thirdFragment)
            }
            else -> {}
        }
    }

    private fun initNavHost() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        navigateToFragment(getString(R.string.home_item), R.id.homeFragment) // Default
    }

    private fun navigateToFragment(title: String, fragmentID: Int) {
        navController.popBackStack()  // Avoid going to prev fragment
        navController.navigate(fragmentID)
        binding.drawerLayout.closeDrawers()
        this@MainActivity.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initOnBackPressed() {
        onBackPressedDispatcher.addCallback(
            this@MainActivity,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.drawerLayout.isOpen) {
                        binding.drawerLayout.close()
                    } else {
                        finish()
                    }
                }
            })
    }
}