package com.example.budgetbuddy.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentNoteBinding


class NoteFragment : MainFragment() {

    companion object {
        const val RESULT_KEY = "com.example.budgetbuddy.fragments.NOTE_KEY"
    }

    private lateinit var binding: FragmentNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setTextView()

        menu()

    }
    private fun menu() {
        binding.appBar.setNavigationOnClickListener {
            val inputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
            findNavController().navigateUp()
        }
    }
    private fun setTextView() {
        binding.textView.requestFocus()
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.textView, InputMethodManager.SHOW_FORCED)


        binding.textView.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                setFragmentResult(RESULT_KEY, bundleOf(RESULT_KEY to v.text.toString()))
                findNavController().navigateUp()
            }
            handled
        })
    }
}