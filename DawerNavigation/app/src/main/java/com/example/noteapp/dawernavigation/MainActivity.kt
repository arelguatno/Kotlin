package com.example.noteapp.dawernavigation

import android.os.Bundle
import android.view.MenuItem
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
            when (it.itemId) {
                R.id.homeItem -> {
                    navigateToFragment("Home Fragment", R.id.homeFragment)
                }
                R.id.firstItem -> {
                    navigateToFragment("Message", R.id.firstFragment)
                }
                R.id.secondItem -> {
                    navigateToFragment("Sync", R.id.secondFragment)
                }
                R.id.thirdItem -> {
                    navigateToFragment("Settings", R.id.thirdFragment)
                }
                else -> {}
            }
            true
        }
    }

    private fun initNavHost() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        navigateToFragment("Home Fragment", R.id.homeFragment) // Default
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
}