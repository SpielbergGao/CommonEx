package com.spielberg.commonext

import android.content.Context


fun getScreenWidth(context: Context): Int = context.resources.displayMetrics.widthPixels

fun getScreenHeight(context: Context): Int = context.resources.displayMetrics.heightPixels

fun getStatusBarHeight(context: Context): Int {
    var result = 0
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = context.resources.getDimensionPixelSize(resourceId)
    }
    return result
}