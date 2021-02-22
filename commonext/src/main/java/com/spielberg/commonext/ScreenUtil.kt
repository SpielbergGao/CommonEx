package com.spielberg.commonext

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView


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


fun Context.dp2px(dpValue: Float): Int {
    return (dpValue * resources.displayMetrics.density + 0.5f).toInt()
}

fun Context.px2dp(pxValue: Float): Int {
    return (pxValue / (resources.displayMetrics.density + 0.5f)).toInt()
}

fun Context.sp2px(spValue: Float): Int {
    return (spValue * resources.displayMetrics.scaledDensity + 0.5f).toInt()
}

fun Context.px2sp(pxValue: Float): Int {
    return (pxValue / resources.displayMetrics.scaledDensity + 0.5f).toInt()
}

fun Fragment.dp2px(dpValue: Float): Int {
    return getApplicationByReflect()?.dp2px(dpValue) ?: 0
}

fun Fragment.px2dp(pxValue: Float): Int {
    return getApplicationByReflect()?.px2dp(pxValue)?: 0
}

fun Fragment.sp2px(dpValue: Float): Int {
    return getApplicationByReflect()?.sp2px(dpValue)?: 0
}

fun Fragment.px2sp(pxValue: Float): Int {
    return getApplicationByReflect()?.px2sp(pxValue)?: 0
}


fun View.px2dp(pxValue: Float): Int {
    return getApplicationByReflect()?.px2dp(pxValue)?: 0
}

fun View.dp2px(dpValue: Float): Int {
    return getApplicationByReflect()?.dp2px(dpValue)?: 0
}

fun View.sp2px(dpValue: Float): Int {
    return getApplicationByReflect()?.sp2px(dpValue)?: 0
}

fun View.px2sp(pxValue: Float): Int {
    return getApplicationByReflect()?.px2sp(pxValue) ?: 0
}

fun RecyclerView.ViewHolder.px2dp(pxValue: Float): Int {
    return itemView.px2dp(pxValue)
}

fun RecyclerView.ViewHolder.dp2px(dpValue: Float): Int {
    return itemView.dp2px(dpValue)
}

fun RecyclerView.ViewHolder.sp2px(dpValue: Float): Int {
    return itemView.sp2px(dpValue)
}

fun RecyclerView.ViewHolder.px2sp(pxValue: Float): Int {
    return itemView.px2sp(pxValue)
}