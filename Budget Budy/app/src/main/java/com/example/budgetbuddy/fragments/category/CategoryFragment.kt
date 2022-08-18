package com.example.budgetbuddy.fragments.category

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentCategoryBinding
import com.example.budgetbuddy.fragments.CategoryAdapter


class CategoryFragment : MainFragment() {
    private lateinit var binding: FragmentCategoryBinding

    companion object {
        const val RESULT_KEY = "com.example.budgetbuddy.fragments.category.category_id"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        loadItems()

        menu()
    }

    private fun menu() {
        binding.appBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadItems() {
        val items = CategoryAdapter(CategoryList.geItems())
        binding.item.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.item.adapter = items

        items.setItemOnClickListener(object : CategoryAdapter.onItemClickListener {
            override fun onItemClick(categoryObj: SimpleListObject) {
                setFragmentResult(RESULT_KEY, bundleOf(RESULT_KEY to categoryObj))
                findNavController().navigateUp()
            }
        })
    }
}