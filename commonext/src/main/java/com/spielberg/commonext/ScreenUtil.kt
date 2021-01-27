package com.spielberg.commonext

import android.content.Context


fun Context.getScreenWidthExt(): Int = resources.displayMetrics.widthPixels

fun Context.getScreenHeightExt(): Int = resources.displayMetrics.heightPixels

fun Context.getStatusBarHeightExt(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}