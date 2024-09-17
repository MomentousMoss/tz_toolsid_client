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

    val navigateToLoginFragment = MutableSingleLiveEvent<Unit>()

    val showToast = MutableSingleLiveEvent<Int>()
    val blockUI = MutableSingleLiveEvent<Unit>()
    val unblockUI = MutableSingleLiveEvent<Unit>()
    val fillTestData = MutableSingleLiveEvent<JsonService.TestData?>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun init(token: String) {
        testLoading(token)
    }

    private fun testLoading(token: String) {
        _isLoading.value = true
        viewModelScope.launch {
            testInterface?.testRequest(token).let {
                if (it != null) {
                    fillTestData.postValue(it)
                    if (it.is_blocked == true) {
                        blockUI.call()
                    } else {
                        unblockUI.call()
                    }
                } else {
                    showToast.postValue(R.string.test_toast_error_request)
                }
            }
            _isLoading.value = false
        }
    }

    fun logoutClick() {
        unblockUI.call()
        navigateToLoginFragment.call()
    }

}