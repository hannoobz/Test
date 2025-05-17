package com.hannoobz.internship.ui.firstscreen

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
        binding.palindromeText.addTextChangedListener {
            viewModel.palindromeInput.value = it.toString()
        }

        binding.nameText.addTextChangedListener {
            viewModel.nameInput.value = it.toString()
        }

        binding.checkButton.setOnClickListener{
            viewModel.checkPalindromeShow(requireContext())
        }

        binding.nextButton.setOnClickListener{
            findNavController().navigate(R.id.action_firstScreenFragment_to_secondScreenFragment)
        }
    }
}