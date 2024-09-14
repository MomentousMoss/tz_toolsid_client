package com.momentousmoss.tz_toolsid_client

import android.app.Application
import com.momentousmoss.tz_toolsid_client.ui.viewModelsModule
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            modules(viewModelsModule)
        }
    }
}