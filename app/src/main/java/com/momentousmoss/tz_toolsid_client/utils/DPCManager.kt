package com.momentousmoss.tz_toolsid_client.utils

import android.app.Activity.DEVICE_POLICY_SERVICE
import android.app.ActivityManager
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.os.UserManager

class DPCManager(private val applicationContext: Context) {

    private val devicePolicyManager by lazy {
        applicationContext.getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
    }
    private val activityManager by lazy {
        applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    }
    private val adminComponent by lazy { DPCReceiver.getComponentName(applicationContext) }
    private val packagesArray = arrayOf(adminComponent.packageName)
    private val keyUserRestrictionVolume= UserManager.DISALLOW_ADJUST_VOLUME
    private val devicePolicyManagerFlags = DevicePolicyManager.LOCK_TASK_FEATURE_HOME or
            DevicePolicyManager.LOCK_TASK_FEATURE_OVERVIEW or
            DevicePolicyManager.LOCK_TASK_FEATURE_NOTIFICATIONS

    fun blockUI() {
        if (!isLocked() && isDeviceOwnerApp()) {
            devicePolicyManager.setLockTaskFeatures(adminComponent, devicePolicyManagerFlags)
            devicePolicyManager.addUserRestriction(adminComponent, keyUserRestrictionVolume)
            devicePolicyManager.setLockTaskPackages(adminComponent, packagesArray)
        }
    }

    fun unblockUI(stopLockTaskCallback: () -> Unit?) {
        if (isLocked() && isDeviceOwnerApp()) {
            devicePolicyManager.clearUserRestriction(adminComponent, keyUserRestrictionVolume)
            stopLockTaskCallback.invoke()
            devicePolicyManager.setLockTaskPackages(adminComponent, emptyArray())
        }
    }

    fun isLocked() = activityManager.lockTaskModeState == ActivityManager.LOCK_TASK_MODE_LOCKED

    private fun isDeviceOwnerApp() = devicePolicyManager.isDeviceOwnerApp(adminComponent.packageName)

}