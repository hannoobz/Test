package com.hannoobz.internship.ui.thirdscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannoobz.internship.R
import com.hannoobz.internship.data.model.User
import com.hannoobz.internship.databinding.FragmentThirdScreenBinding

class ThirdScreenFragment : Fragment() {

    private var _binding: FragmentThirdScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ThirdScreenViewModel by activityViewModels()
    private lateinit var listUserAdapter: ListUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
        setupListeners()
    }

    private fun setupUI() {
        binding.thirdScreenTopBar.topBarTitle.text = getString(R.string.third_screen)
        listUserAdapter = ListUserAdapter(requireContext(), ArrayList())
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listUserAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            listUserAdapter.updateList(users)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.srlUsers.isRefreshing = isLoading
        }

        viewModel.isEmpty.observe(viewLifecycleOwner) { isEmpty ->
            binding.tvIsEmptyStatus.visibility = if (isEmpty) View.VISIBLE else View.GONE
        }
    }

    private fun setupListeners() {
        binding.thirdScreenTopBar.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_thirdScreenFragment_to_secondScreenFragment)
        }

        binding.srlUsers.setOnRefreshListener {
            viewModel.refreshUsers()
        }

        binding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && viewModel.isLoading.value == false) {
                    viewModel.loadUsers()
                }
            }
        })

        listUserAdapter.setOnClickListener(object : ListUserAdapter.OnClickListener {
            override fun onClick(position: Int, user: User) {
                val username = user.firstName + " " + user.lastName
                viewModel.setSelectedUser(username)
                findNavController().navigate(R.id.action_thirdScreenFragment_to_secondScreenFragment)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}