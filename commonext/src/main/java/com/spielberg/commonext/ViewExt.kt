package com.spielberg.commonext

import android.content.Context
import android.view.View


fun View.goneExt() {
    visibility = View.GONE
}

fun View.visibleExt() {
    visibility = View.VISIBLE
}

fun View.invisibleExt() {
    visibility = View.INVISIBLE
}

val View.isGoneExt: Boolean
    get() {
        return visibility == View.GONE
    }

val View.isVisibleExt: Boolean
    get() {
        return visibility == View.VISIBLE
    }

val View.isInvisibleExt: Boolean
    get() {
        return visibility == View.INVISIBLE
    }

fun View.toggleVisibilityExt() {
    visibility = if (visibility == View.GONE) View.VISIBLE else View.GONE
}

fun View.getViewLocationArrExt(): IntArray {
    val viewLoc = intArrayOf(0, 0)
    getLocationOnScreen(viewLoc)
    return viewLoc
}

fun View.hasLeftSpaceExt(popupContentView: View): Boolean {
    val viewLoc = getViewLocationArrExt()
    if (viewLoc[0] <= popupContentView.measuredWidth) {
        return false
    }
    return true
}

fun View.hasRightSpaceExt(mCtx: Context, popupContentView: View): Boolean {
    val viewLoc = getViewLocationArrExt()
    if (mCtx.getScreenWidthExt() - viewLoc[0] - width <= popupContentView.measuredWidth) {
        return false
    }
    return true
}

fun View.hasTopSpaceExt(popupContentView: View): Boolean {
    val viewLoc = getViewLocationArrExt()
    if (viewLoc[1] <= popupContentView.measuredHeight) {
        return false
    }
    return true
}

fun View.hasBottomSpaceExt(mCtx: Context, popupContentView: View): Boolean {
    val viewLoc = getViewLocationArrExt()
    if (mCtx.getScreenHeightExt() - viewLoc[1] - height <= popupContentView.measuredHeight) {
        return false
    }
    return true
}
