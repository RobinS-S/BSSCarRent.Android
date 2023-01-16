package com.bss.carrent.ui.auth

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bss.carrent.data.user.UserDto
import com.bss.carrent.data.user.UserRegisterDto
import com.bss.carrent.repository.ProfileRepository
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _registered = MutableLiveData<UserDto>()

    val registered: LiveData<UserDto>
        get() = _registered

    fun register(registerDto: UserRegisterDto, context: Context) {
        viewModelScope.launch {
            val repository = ProfileRepository()
            val profile = repository.register(registerDto, context)
            if (profile != null) {
                _registered.value = profile!!
            }
        }
    }
}