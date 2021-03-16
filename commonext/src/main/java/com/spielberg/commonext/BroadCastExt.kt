package com.spielberg.commonext

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager

fun Context.getBroadcastManager(): LocalBroadcastManager {
    return LocalBroadcastManager.getInstance(this)
}

fun getIntentFilter(vararg actions: String?): IntentFilter? {
    var filter: IntentFilter? = null
    if (actions.isNotEmpty()) {
        filter = IntentFilter()
        for (action in actions) {
            filter.addAction(action)
        }
    }
    return filter
}

/**
 * @Desc 通过Action注册广播接收者
 * @Param [ctx, receiver, actions]
 */
fun Context?.registerReceiver(receiver: BroadcastReceiver, vararg actions: String) {
    val filter = getIntentFilter(*actions)
    if (filter != null) {
        this?.registerReceiver(receiver, filter)
    }
}

/**
 * @Desc 通过IntentFilter注册广播接收者
 * @Param [ctx, receiver, filter]
 */
fun Context?.registerReceiver(receiver: BroadcastReceiver, filter: IntentFilter) {
    this?.getBroadcastManager()?.registerReceiver(receiver, filter)
}

/**
 * @Desc 注销广播接收者
 * @Param [ctx, receiver]
 */
fun Context?.unRegisterReceiver(receiver: BroadcastReceiver) {
    this?.getBroadcastManager()?.unregisterReceiver(receiver)
}

/**
 * @Desc 通过Action发送广播
 * @Param [ctx, action]
 */
fun Context?.sendBroadcast(action: String?) {
    this?.sendBroadcast(Intent(action))
}

/**
 * @Desc 通过intent发送广播
 * @Param [ctx, intent]
 */
fun Context?.sendBroadcast(intent: Intent) {
    this?.getBroadcastManager()?.sendBroadcast(intent)
}

/**
 * @Desc 通过Action同步发送广播
 * @Param [ctx, action]
 */
fun Context?.sendBroadcastSync(action: String?) {
    this?.sendBroadcastSync(Intent(action))
}

/**
 * @Desc 通过Intent同步发送广播
 * @Param [ctx, intent]
 */
fun Context?.sendBroadcastSync(intent: Intent) {
    this?.getBroadcastManager()?.sendBroadcastSync(intent)
}