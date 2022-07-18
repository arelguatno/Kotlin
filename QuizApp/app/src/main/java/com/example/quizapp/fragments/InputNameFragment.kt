package com.example.quizapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quizapp.app_compat.MainFragment
import com.example.quizapp.databinding.FragmentInputNameBinding


class InputName : MainFragment() {
    private lateinit var bing: FragmentInputNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bing = FragmentInputNameBinding.inflate(layoutInflater)
        return bing.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bing.btnStart.setOnClickListener {
            if (bing.etName.text.isNullOrEmpty()) {
                showToastMessageShort("Please enter your name")
            } else {
                findNavController().navigate(R.id.action_inputName_to_gameBoardFragment)
            }
        }
    }

}