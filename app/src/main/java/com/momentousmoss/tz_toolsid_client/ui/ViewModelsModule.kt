package com.momentousmoss.tz_toolsid_client.ui

import com.momentousmoss.tz_toolsid_client.ui.data_screen.DataViewModel
import com.momentousmoss.tz_toolsid_client.ui.login_screen.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { LoginViewModel() }
    viewModel { DataViewModel() }

}