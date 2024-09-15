package com.momentousmoss.tz_toolsid_client.ui

import com.momentousmoss.tz_toolsid_client.ui.test_screen.TestViewModel
import com.momentousmoss.tz_toolsid_client.ui.login_screen.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { LoginViewModel(loginInterface = get()) }
    viewModel { TestViewModel(testInterface = get()) }

}