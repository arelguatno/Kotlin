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

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        navigateToFragment("Home Fragment", R.id.homeFragment) // Default

        binding.apply {
            toggle = ActionBarDrawerToggle(
                this@MainActivity,
                drawerLayout,
                R.string.open,
                R.string.close
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.homeItem -> {
                        navigateToFragment("Home Fragment", R.id.homeFragment)
                    }
                    R.id.firstItem -> {
                        navigateToFragment("First Fragment", R.id.firstFragment)
                    }
                    R.id.secondItem -> {
                        navigateToFragment("Second Fragment", R.id.secondFragment)
                    }
                    R.id.thirdItem -> {
                        navigateToFragment("Third Fragment", R.id.thirdFragment)
                    }
                    else -> {}
                }
                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToFragment(title: String, fragmentID: Int) {
        navController.popBackStack()  // Avoid going to prev fragment
        navController.navigate(fragmentID)
        binding.drawerLayout.closeDrawers()
        this@MainActivity.title = title
    }
}