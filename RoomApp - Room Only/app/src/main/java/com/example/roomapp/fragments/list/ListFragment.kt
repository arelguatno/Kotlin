package com.example.roomapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    companion object {
        lateinit var adapter: ListAdapter
        lateinit var adapterUserPaging: AdapterUserPaging
    }

    private lateinit var mUserViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


//        adapter = ListAdapter()
//        val recyclerView = view.recyclerview
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
//            adapter.setData(user)
//        })


        // Paging
        adapterUserPaging = AdapterUserPaging()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapterUserPaging
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch(Dispatchers.IO) {
            mUserViewModel.getAllPaged().collectLatest {
                adapterUserPaging.submitData(it)
            }
        }


        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        // Add menu
        setHasOptionsMenu(true)


        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? androidx.appcompat.widget.SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchDatabase(newText)
        }
        return true
    }


    private fun searchDatabase(param: String) {
        val searcQuery = "%$param%"

        mUserViewModel.searchDatabase(searcQuery).observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

    }
}

