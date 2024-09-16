package com.momentousmoss.tz_toolsid_client.utils

import android.app.admin.DeviceAdminReceiver
import android.content.ComponentName
import android.content.Context

class DPCReceiver : DeviceAdminReceiver() {
    companion object {
        fun getComponentName(context: Context): ComponentName {
            return ComponentName(context.applicationContext, DPCReceiver::class.java)
        }
    }
}