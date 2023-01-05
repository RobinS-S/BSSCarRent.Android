package com.bss.carrent.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bss.carrent.model.User
import com.bss.carrent.repository.ProfileRepository
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {
    private val _isError = MutableLiveData<Boolean>()
    private val _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = _user

    private fun setUser(value: User) {
        _user.value = value
    }

    val isError: LiveData<Boolean>
        get() = _isError

    fun setIsError(value: Boolean) {
        _isError.value = value
    }

    fun tryLogin(context: Context) {
        viewModelScope.launch {
            val repository = ProfileRepository()
            val retrievedUser = repository.attemptLogin(context)
            if(retrievedUser == null) {
                setIsError(true)
            } else {
                setIsError(false)
                setUser(retrievedUser)
            }
        }
    }
}