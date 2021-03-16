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
fun registerReceiver(ctx: Context?, receiver: BroadcastReceiver, vararg actions: String) {
    val filter = getIntentFilter(*actions)
    if (filter != null) {
        registerReceiver(ctx, receiver, filter)
    }
}

/**
 * @Desc 通过IntentFilter注册广播接收者
 * @Param [ctx, receiver, filter]
 */
fun registerReceiver(ctx: Context?, receiver: BroadcastReceiver, filter: IntentFilter) {
    ctx?.getBroadcastManager()?.registerReceiver(receiver, filter)
}

/**
 * @Desc 注销广播接收者
 * @Param [ctx, receiver]
 */
fun unRegisterReceiver(ctx: Context?, receiver: BroadcastReceiver) {
    ctx?.getBroadcastManager()?.unregisterReceiver(receiver)
}

/**
 * @Desc 通过Action发送广播
 * @Param [ctx, action]
 */
fun sendBroadcast(ctx: Context?, action: String?) {
    sendBroadcast(ctx, Intent(action))
}

/**
 * @Desc 通过intent发送广播
 * @Param [ctx, intent]
 */
fun sendBroadcast(ctx: Context?, intent: Intent) {
    ctx?.getBroadcastManager()?.sendBroadcast(intent)
}

/**
 * @Desc 通过Action同步发送广播
 * @Param [ctx, action]
 */
fun sendBroadcastSync(ctx: Context?, action: String?) {
    sendBroadcastSync(ctx, Intent(action))
}

/**
 * @Desc 通过Intent同步发送广播
 * @Param [ctx, intent]
 */
fun sendBroadcastSync(ctx: Context?, intent: Intent) {
    ctx?.getBroadcastManager()?.sendBroadcastSync(intent)
}