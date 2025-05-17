package com.hannoobz.internship.ui.secondscreen

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hannoobz.internship.R
import com.hannoobz.internship.databinding.FragmentSecondScreenBinding

class SecondScreenFragment : Fragment() {

    private var _binding: FragmentSecondScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SecondScreenViewModel by viewModels()
    private val DEFAULT_USERNAME = "Default Username"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = requireActivity().getSharedPreferences(getString(R.string.current_username), Context.MODE_PRIVATE)
        val currentUsername = sharedPref.getString(getString(R.string.current_username),DEFAULT_USERNAME)

        binding.secondScreenTopBar.topBarTitle.text = getString(R.string.second_screen)
        binding.tvUsername.text = if (currentUsername?.isNotBlank() == true) currentUsername else DEFAULT_USERNAME

        binding.secondScreenTopBar.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_secondScreenFragment_to_firstScreenFragment)
        }
    }
}