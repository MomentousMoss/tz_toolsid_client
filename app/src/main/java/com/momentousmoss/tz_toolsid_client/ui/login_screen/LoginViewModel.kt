package com.momentousmoss.tz_toolsid_client.ui.login_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.momentousmoss.tz_toolsid_client.R
import com.momentousmoss.tz_toolsid_client.api.login.LoginInterface
import com.momentousmoss.tz_toolsid_client.utils.MutableSingleLiveEvent
import kotlinx.coroutines.launch

class LoginViewModel(private val loginInterface: LoginInterface? = null) : ViewModel() {

    val navigateToTestFragment = MutableSingleLiveEvent<String>()
    val showToast = MutableSingleLiveEvent<Int>()

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        setDefaults()
    }

    private fun setDefaults() {
        email.value = "atlassianfortest1@gmail.com"
        password.value = "Test123Test"
    }

    fun loginClick() {
        _isLoading.value = true
        viewModelScope.launch {
            val email = email.value
            val password = password.value
            if (email != null && password != null) {
                loginInterface?.loginRequestDataResponse(email, password).let {
                    val token = it?.token ?: ""
                    if (token.isNotEmpty()) {
                        navigateToTestFragment.postValue(token)
                    } else {
                        showToast.postValue(R.string.login_toast_error_request)
                    }
                }
            }
            _isLoading.value = false
        }
    }

}