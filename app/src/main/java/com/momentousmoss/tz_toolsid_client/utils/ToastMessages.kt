package com.momentousmoss.tz_toolsid_client.utils

import android.content.Context
import android.widget.Toast

class ToastMessages(private val applicationContext: Context) {
    fun showMessage(textResource: Int) {
        Toast.makeText(
            applicationContext,
            applicationContext.resources.getString(textResource),
            Toast.LENGTH_SHORT
        ).show()
    }
}