package com.spielberg.commonext

import android.content.Context
import android.view.View


fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

val View.isGone: Boolean
    get() {
        return visibility == View.GONE
    }

val View.isVisible: Boolean
    get() {
        return visibility == View.VISIBLE
    }

val View.isInvisible: Boolean
    get() {
        return visibility == View.INVISIBLE
    }

fun View.toggleVisibility() {
    visibility = if (visibility == View.GONE) View.VISIBLE else View.GONE
}

fun View.getViewLocationArr(): IntArray {
    val viewLoc = intArrayOf(0, 0)
    getLocationOnScreen(viewLoc)
    return viewLoc
}

fun View.hasLeftSpace(popupContentView: View): Boolean {
    val viewLoc = getViewLocationArr()
    if (viewLoc[0] <= popupContentView.measuredWidth) {
        return false
    }
    return true
}

fun View.hasRightSpace(mCtx: Context, popupContentView: View): Boolean {
    val viewLoc = getViewLocationArr()
    if (getScreenWidth(mCtx) - viewLoc[0] - width <= popupContentView.measuredWidth) {
        return false
    }
    return true
}

fun View.hasTopSpace(popupContentView: View): Boolean {
    val viewLoc = getViewLocationArr()
    if (viewLoc[1] <= popupContentView.measuredHeight) {
        return false
    }
    return true
}

fun View.hasBottomSpace(mCtx: Context, popupContentView: View): Boolean {
    val viewLoc = getViewLocationArr()
    if (getScreenHeight(mCtx) - viewLoc[1] - height <= popupContentView.measuredHeight) {
        return false
    }
    return true
}
