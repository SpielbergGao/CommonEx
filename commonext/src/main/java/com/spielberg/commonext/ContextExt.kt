package com.spielberg.commonext

import android.app.Application
import android.content.Context
import android.provider.Settings
import android.text.TextUtils

fun getApplicationByReflect(): Application? {
    val thread: Any? = getActivityThread()
    val app = "android.app.ActivityThread".getClassInvokeMethodExt("getApplication")?.invoke(thread)
        ?: return null
    return app as Application
}

private fun getActivityThread(): Any? {
    val activityThread: Any? = getActivityThreadInActivityThreadStaticField()
    return activityThread ?: getActivityThreadInActivityThreadStaticMethod()
}

private fun getActivityThreadInActivityThreadStaticField(): Any? {
    val field =
        "android.app.ActivityThread".getClassInvokeDeclaredFieldExt("sCurrentActivityThread")
    field?.isAccessible = true
    return if (field != null) field[null] else null
}

private fun getActivityThreadInActivityThreadStaticMethod(): Any? {
    return "android.app.ActivityThread".getClassInvokeMethodExt("currentActivityThread")
        ?.invoke(null)
}

fun Context.checkAccessibilityServiceEnabled(serviceName: String): Boolean {
    val settingValue =
        Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
    var result = false
    val splitter = TextUtils.SimpleStringSplitter(':')
    while (splitter.hasNext()) {
        if (splitter.next().equals(serviceName, true)) {
            result = true
            break
        }
    }
    return result
}

fun Context.isRunningService(className: String, maxNum: Int = 1000): Boolean {
    val runningServices = this.activityManager?.getRunningServices(maxNum)
    if (runningServices.isNullOrEmpty()) return false
    for (runningServiceInfo in runningServices) {
        val service = runningServiceInfo.service
        if (className == service.className) {
            return true
        }
    }
    return false
}