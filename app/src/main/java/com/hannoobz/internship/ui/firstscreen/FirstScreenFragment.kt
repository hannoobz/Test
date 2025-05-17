package com.hannoobz.internship.ui.firstscreen

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.hannoobz.internship.R
import com.hannoobz.internship.databinding.FragmentFirstScreenBinding

class FirstScreenFragment : Fragment() {
    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FirstScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = requireActivity().getSharedPreferences(getString(R.string.current_username), Context.MODE_PRIVATE)
        binding.nameText.setText(sharedPref.getString(getString(R.string.current_username),""))

        binding.palindromeText.addTextChangedListener {
            viewModel.palindromeInput.value = it.toString()
        }

        binding.nameText.addTextChangedListener {
            viewModel.nameInput.value = it.toString()
            with(sharedPref.edit()){
                putString(getString(R.string.current_username),it.toString())
                apply()
            }
        }

        binding.checkButton.setOnClickListener{
            viewModel.checkPalindromeShow(requireContext())

        }

        binding.nextButton.setOnClickListener{
            findNavController().navigate(R.id.action_firstScreenFragment_to_secondScreenFragment)
        }
    }
}