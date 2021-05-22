package com.spielberg.commonext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.spielberg.commonext.handlers.LifecycleHandler

fun FragmentActivity.fragmentManager(action: FragmentTransaction.() -> Unit) {
    supportFragmentManager.beginTransaction()
        .apply { action() }
        .commitAllowingStateLoss()
}

fun FragmentActivity.replace(
    layoutId: Int,
    f: Fragment,
    bundle: Array<out Pair<String, Any?>>? = null
) {
    if (bundle != null) f.arguments = bundle.toBundle()
    supportFragmentManager.beginTransaction()
        .replace(layoutId, f)
        .commitAllowingStateLoss()
}

fun FragmentActivity.add(
    layoutId: Int,
    f: Fragment,
    bundle: Array<out Pair<String, Any?>>? = null
) {
    if (bundle != null) f.arguments = bundle.toBundle()
    supportFragmentManager.beginTransaction()
        .add(layoutId, f)
        .commitAllowingStateLoss()
}

fun FragmentActivity.hide(f: Fragment) {
    supportFragmentManager.beginTransaction()
        .hide(f)
        .commitAllowingStateLoss()
}

fun FragmentActivity.show(f: Fragment) {
    supportFragmentManager.beginTransaction()
        .show(f)
        .commitAllowingStateLoss()
}

fun FragmentActivity.remove(f: Fragment) {
    supportFragmentManager.beginTransaction()
        .remove(f)
        .commitAllowingStateLoss()
}


fun Fragment.replace(layoutId: Int, f: Fragment, bundle: Array<out Pair<String, Any?>>? = null) {
    if (bundle != null) f.arguments = bundle.toBundle()
    childFragmentManager.beginTransaction()
        .replace(layoutId, f)
        .commitAllowingStateLoss()
}

fun Fragment.add(layoutId: Int, f: Fragment, bundle: Array<out Pair<String, Any?>>? = null) {
    if (bundle != null) f.arguments = bundle.toBundle()
    childFragmentManager.beginTransaction()
        .add(layoutId, f)
        .commitAllowingStateLoss()
}

fun Fragment.hide(f: Fragment) {
    childFragmentManager.beginTransaction()
        .hide(f)
        .commitAllowingStateLoss()
}

fun Fragment.show(f: Fragment) {
    childFragmentManager.beginTransaction()
        .show(f)
        .commitAllowingStateLoss()
}

fun Fragment.remove(f: Fragment) {
    childFragmentManager.beginTransaction()
        .remove(f)
        .commitAllowingStateLoss()
}

fun Fragment.popBackStackExt() {
    childFragmentManager.popBackStack(this.javaClass.simpleName, this.id)
}

//post, postDelay
fun Fragment.post(action: () -> Unit) {
    LifecycleHandler(this).post { action() }
}

fun Fragment.postDelay(delay: Long = 0, action: () -> Unit) {
    LifecycleHandler(this).postDelayed({ action() }, delay)
}