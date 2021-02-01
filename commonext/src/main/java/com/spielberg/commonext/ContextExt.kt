package com.spielberg.commonext

import android.app.Application

fun getApplicationByReflect(): Application? {
    val thread: Any? = getActivityThread()
    val app = "android.app.ActivityThread".getClassInvokeMethodExt("getApplication")?.invoke(thread) ?: return null
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