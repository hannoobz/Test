package com.hannoobz.internship.ui.thirdscreen

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hannoobz.internship.R
import com.hannoobz.internship.data.model.User
import com.hannoobz.internship.databinding.FragmentThirdScreenBinding
import org.json.JSONException

class ThirdScreenFragment : Fragment() {

    private var _binding: FragmentThirdScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ThirdScreenViewModel by viewModels()
    private lateinit var rvUsers: RecyclerView
    private lateinit var isEmptyStatus: TextView
    private val list = ArrayList<User>()
    private lateinit var listUserAdapter: ListUserAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var page = 1
    private var perpage = 6
    private var totalPages = Int.MAX_VALUE
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPrefSelectedUser: SharedPreferences =
            requireActivity()
                .getSharedPreferences(getString(R.string.selected_username), Context.MODE_PRIVATE)


        swipeRefreshLayout = binding.srlUsers
        rvUsers = binding.rvUsers
        isEmptyStatus = binding.tvIsEmptyStatus

        binding.thirdScreenTopBar.topBarTitle.text = getString(R.string.third_screen)
        binding.thirdScreenTopBar.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_thirdScreenFragment_to_secondScreenFragment)
        }

        listUserAdapter = ListUserAdapter(requireContext(),list)
        rvUsers.layoutManager = LinearLayoutManager(requireContext())
        rvUsers.adapter = listUserAdapter
        rvUsers.setHasFixedSize(true)

        getDataFromAPI(page)
        showRecyclerList(sharedPrefSelectedUser)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            list.clear()
            page = 1
            getDataFromAPI(page)
        }

        rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && !isLoading && page<=totalPages) {
                    page++
                    getDataFromAPI(page)
                }
            }
        })
    }

    private fun showRecyclerList(sharedPreferences: SharedPreferences) {
        rvUsers.layoutManager = LinearLayoutManager(requireContext())
        listUserAdapter.setOnClickListener(object: ListUserAdapter.OnClickListener{
            override fun onClick(position: Int, user: User) {
                val username = user.firstName+" "+user.lastName
                with(sharedPreferences.edit()){
                    putString(getString(R.string.selected_username),username)
                    apply()
                }
                findNavController().navigate(R.id.action_thirdScreenFragment_to_secondScreenFragment)
            }
        })
        rvUsers.adapter = listUserAdapter
    }

    private fun getDataFromAPI(page: Int) {
        isLoading = true
        val url = "https://reqres.in/api/users?page=$page&per_page=$perpage"
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = object: JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                try {
                    totalPages = response.getInt("total_pages")
                    val dataArray = response.getJSONArray("data")
                    for (i in 0 until dataArray.length()) {
                        val jsonObject = dataArray.getJSONObject(i)
                        list.add(
                            User(
                                id = jsonObject.getInt("id"),
                                firstName = jsonObject.getString("first_name"),
                                lastName = jsonObject.getString("last_name"),
                                email = jsonObject.getString("email"),
                                avatar = jsonObject.getString("avatar")
                            )
                        )
                    }
                    if(list.isEmpty()){
                        isEmptyStatus.visibility = View.VISIBLE
                    }
                    else{
                        isEmptyStatus.visibility = View.GONE
                    }
                    listUserAdapter.notifyDataSetChanged()
                    isLoading = false
                } catch (e: JSONException) {
                    e.printStackTrace()
                    isLoading = false
                }
            }, {
                isLoading = false
            }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["x-api-key"] = "reqres-free-v1"
                return headers
            }
        }
        queue.add(jsonObjectRequest)
    }
}