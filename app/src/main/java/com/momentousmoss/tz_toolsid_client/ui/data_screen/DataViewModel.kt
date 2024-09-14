package com.momentousmoss.tz_toolsid_client.ui.data_screen

import androidx.lifecycle.ViewModel
import com.momentousmoss.tz_toolsid_client.utls.MutableSingleLiveEvent

class DataViewModel : ViewModel() {

    val navigateToLoginFragment = MutableSingleLiveEvent<Unit>()

}