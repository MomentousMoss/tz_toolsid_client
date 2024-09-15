package com.momentousmoss.tz_toolsid_client.api

import com.momentousmoss.tz_toolsid_client.api.login.LoginImplementation
import com.momentousmoss.tz_toolsid_client.api.login.LoginInterface
import com.momentousmoss.tz_toolsid_client.api.test.TestImplementation
import com.momentousmoss.tz_toolsid_client.api.test.TestInterface
import org.koin.dsl.module

val repositoriesModule = module {

    single<LoginInterface> { LoginImplementation() }
    single<TestInterface> { TestImplementation() }

}