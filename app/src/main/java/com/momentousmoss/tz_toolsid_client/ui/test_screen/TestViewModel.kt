package com.momentousmoss.tz_toolsid_client.ui.test_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.momentousmoss.tz_toolsid_client.R
import com.momentousmoss.tz_toolsid_client.api.JsonService
import com.momentousmoss.tz_toolsid_client.api.test.TestInterface
import com.momentousmoss.tz_toolsid_client.utils.MutableSingleLiveEvent
import kotlinx.coroutines.launch

class TestViewModel(private val testInterface: TestInterface? = null) : ViewModel() {

    val logout = MutableSingleLiveEvent<Unit>()

    val showToast = MutableSingleLiveEvent<Int>()
    val changeBlockUi = MutableSingleLiveEvent<Boolean?>()
    val fillTestData = MutableSingleLiveEvent<JsonService.TestData?>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var token : String? = null

    fun init(token: String) {
        this.token = token
        testLoading(token)
    }

    private fun testLoading(token: String) {
        _isLoading.value = true
        viewModelScope.launch {
            testInterface?.testRequestDataResponse(token).let {
                if (it != null) {
                    fillTestData.postValue(it)
                    changeBlockUi.postValue(it.is_blocked)
                } else {
                    showToast.postValue(R.string.test_toast_error_request)
                }
            }
            _isLoading.value = false
        }
    }

    fun checkBlock() {
        viewModelScope.launch {
            val token = this@TestViewModel.token
            if (token == null) {
                logout()
            } else {
                testInterface?.testRequestDataResponse(token).let {
                    changeBlockUi.postValue(it?.is_blocked)
                }
            }
        }
    }

    fun logout() {
        logout.call()
    }

}