package com.example.roomapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.databinding.FragmentUpdateBinding
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>() // set javasersion 1.8 see gradle
    fun getData() {
        Log.d(
            "Test",
            args.currentUser.firstName
        ) // currentUser is the name of the argument in the nav controller
    }

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var binding: FragmentUpdateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(layoutInflater)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.updateFirstNameEt.setText(args.currentUser.firstName)
        binding.updateLastNameEt.setText(args.currentUser.lastName)
        binding.updateAgeEt.setText(args.currentUser.age.toString())

        binding.updateBtn.setOnClickListener {
            updateItem()
        }

        // Add menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun updateItem() {
        val firstName = updateFirstName_et.text.toString()
        val lastName = updateLastName_et.text.toString()
        val age = Integer.parseInt(updateAge_et.text.toString())

        if (inputCheck(firstName, lastName, updateAge_et.text)) {
            // Create User Object
            val updatedUser = User(
                args.currentUser.id, firstName, lastName, age
            )
            // Update Current User
            mUserViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(),
                "Deleted ${args.currentUser.firstName} ${args.currentUser.lastName}",
                Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentUser.firstName}")
        builder.setMessage("Are you sure want to delete current ${args.currentUser.firstName}")
        builder.show()

    }

}