package com.hannoobz.internship.ui.firstscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.hannoobz.internship.R
import com.hannoobz.internship.databinding.FragmentFirstScreenBinding

class FirstScreenFragment : Fragment() {
    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FirstScreenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupListeners()
    }

    private fun setupUI(){
        if(binding.nameText.text?.isEmpty() == true){
            binding.nameText.setText(viewModel.nameInput.value)
        }
    }

    private fun setupListeners() {
        binding.palindromeText.addTextChangedListener {
            viewModel.updatePalindromeInput(it.toString())
        }

        binding.nameText.addTextChangedListener {
            viewModel.updateNameInput(it.toString())
        }

        binding.checkButton.setOnClickListener {
            viewModel.checkPalindromeShow(requireContext())
        }

        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_firstScreenFragment_to_secondScreenFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
