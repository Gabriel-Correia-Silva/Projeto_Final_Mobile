package com.example.projeto_final_mobile.database.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projeto_final_mobile.database.daos.UserDao
import com.projeto_final_mobile.database.models.User
import kotlinx.coroutines.launch


class UserViewModel(private val userDao: UserDao? = null) : ViewModel() {

    private var authenticatedUser: User? = null

    fun setAuthenticatedUser(user: User) {
        authenticatedUser = user
    }

    fun getAuthenticatedUser(): User? {
        return authenticatedUser
    }

    fun updateAuthenticatedUser(user: User) {
        viewModelScope.launch {
            userDao?.updateUser(user)
        }
    }

    fun getUser(email: String): LiveData<User> {
        return userDao?.getUser(email) ?: MutableLiveData()
    }

    fun getUserByEmail(email: String): LiveData<User> {
        return userDao?.getUserByEmail(email) ?: MutableLiveData()
    }
}