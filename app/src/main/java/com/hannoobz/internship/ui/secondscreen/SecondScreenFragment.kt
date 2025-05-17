package com.hannoobz.internship.ui.secondscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.hannoobz.internship.R
import com.hannoobz.internship.databinding.FragmentSecondScreenBinding
import com.hannoobz.internship.ui.firstscreen.FirstScreenViewModel
import com.hannoobz.internship.ui.thirdscreen.ThirdScreenViewModel

class SecondScreenFragment : Fragment() {

    private var _binding: FragmentSecondScreenBinding? = null
    private val binding get() = _binding!!
    private val firstScreenViewModel: FirstScreenViewModel by activityViewModels()
    private val thirdScreenViewModel: ThirdScreenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        binding.secondScreenTopBar.topBarTitle.text = getString(R.string.second_screen)
        val username = firstScreenViewModel.nameInput.value
        val selectedUser = thirdScreenViewModel.selected.value
        binding.tvUsername.text = if(username?.isNotBlank() == true) username else "Default Username"
        binding.tvSelectedUsername.text = if (selectedUser?.isNotBlank() == true) selectedUser else "Selected User"
    }

    private fun setupClickListeners() {
        binding.secondScreenTopBar.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_secondScreenFragment_to_firstScreenFragment)
        }

        binding.buttonChooseUser.setOnClickListener {
            findNavController().navigate(R.id.action_secondScreenFragment_to_thirdScreenFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}