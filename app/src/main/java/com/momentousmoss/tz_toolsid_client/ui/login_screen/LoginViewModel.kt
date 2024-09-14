package com.momentousmoss.tz_toolsid_client.ui.login_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.momentousmoss.tz_toolsid_client.utls.MutableSingleLiveEvent
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val navigateToDataFragment = MutableSingleLiveEvent<Unit>()

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loginClick() {
        _isLoading.value = true
        viewModelScope.launch {
            //login request
            navigateToDataFragment.call()
        }
        _isLoading.value = false
    }

}