package com.bss.userrent.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bss.carrent.data.user.UserDto
import com.bss.carrent.repository.ProfileRepository
import kotlinx.coroutines.launch

class UserDetailViewModel : ViewModel() {
    private val _user = MutableLiveData<UserDto>()
    private val _isError = MutableLiveData<Boolean>()

    val isError: LiveData<Boolean>
        get() = _isError

    private fun setIsError(value: Boolean) {
        _isError.value = value
    }

    val userDto: LiveData<UserDto>
        get() = _user

    private fun setUser(value: UserDto) {
        _user.value = value
    }

    fun getUser(id: Long) {
        viewModelScope.launch {
            val profileRepository = ProfileRepository()
            val retrievedUser = profileRepository.getProfileForUser(id)
            if (retrievedUser == null) {
                setIsError(true)
            } else {
                setIsError(false)
                setUser(retrievedUser)
            }
        }
    }
}