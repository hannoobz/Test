package com.hannoobz.internship.ui.thirdscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hannoobz.internship.data.model.User
import com.hannoobz.internship.data.repository.UserRepository

class ThirdScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application.applicationContext)

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _selected = MutableLiveData<String>()
    val selected: LiveData<String> =_selected

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData(true)
    val isEmpty: LiveData<Boolean> = _isEmpty

    private var currentPage = 1
    private var totalPages = Int.MAX_VALUE
    private val perPage = 6
    private val userList = mutableListOf<User>()

    init {
        loadUsers()
    }

    fun loadUsers() {
        if (currentPage <= totalPages && _isLoading.value == false) {
            _isLoading.postValue(true)
            repository.getUsers(
                page = currentPage,
                perPage = perPage,
                onSuccess = { users, pages ->
                    userList.addAll(users)
                    _users.postValue(userList.toList())
                    _isEmpty.postValue(userList.isEmpty())
                    totalPages = pages
                    currentPage++
                }
            )
            _isLoading.postValue(false)
        }
    }

    fun refreshUsers() {
        userList.clear()
        currentPage = 1
        totalPages = Int.MAX_VALUE
        loadUsers()
    }

    fun setSelectedUser(string: String){
        _selected.postValue(string)
    }
}