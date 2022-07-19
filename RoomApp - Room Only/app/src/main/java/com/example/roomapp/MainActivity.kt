package com.example.roomapp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class MainActivity : AppCompatActivity() {
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        setupActionBarWithNavController(findNavController(R.id.fragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        //menuInflater.inflate(R.menu.delete_menu,menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item != null) {
//            deleteAllUsers()
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes") { _, _ ->
            //mUserViewModel.deleteAllUsers()
            Toast.makeText(
                this,
                "Successfully removed everything",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete Users?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }
}