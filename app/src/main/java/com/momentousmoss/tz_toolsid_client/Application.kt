package com.momentousmoss.tz_toolsid_client

import android.app.Application
import com.momentousmoss.tz_toolsid_client.api.repositoriesModule
import com.momentousmoss.tz_toolsid_client.ui.viewModelsModule
import com.momentousmoss.tz_toolsid_client.utils.DPCManager
import com.momentousmoss.tz_toolsid_client.utils.ToastMessages
import org.koin.core.context.startKoin
import org.koin.dsl.module

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            modules(
                viewModelsModule,
                repositoriesModule,
                module {
                    single { ToastMessages(applicationContext) }
                    single { DPCManager(applicationContext) }
                }
            )
        }
    }
}