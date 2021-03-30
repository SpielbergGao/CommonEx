package com.spielberg.commonext

import android.content.ClipData
import android.content.ClipboardManager.OnPrimaryClipChangedListener


/**
 * Copy the text to clipboard.
 *
 * The label equals name of package.
 *
 * @param text The text.
 */
fun copyText(text: CharSequence?) {
    val cm = getApplicationByReflect()?.clipboardManager
    cm?.setPrimaryClip(ClipData.newPlainText(getApplicationByReflect()?.packageName, text))
}

/**
 * Copy the text to clipboard.
 *
 * @param label The label.
 * @param text  The text.
 */
fun copyText(label: CharSequence?, text: CharSequence?) {
    val cm = getApplicationByReflect()?.clipboardManager
    cm?.setPrimaryClip(ClipData.newPlainText(label, text))
}

/**
 * Clear the clipboard.
 */
fun clear() {
    val cm = getApplicationByReflect()?.clipboardManager
    cm?.setPrimaryClip(ClipData.newPlainText(null, ""))
}

/**
 * Return the label for clipboard.
 *
 * @return the label for clipboard
 */
fun getLabel(): CharSequence? {
    val cm = getApplicationByReflect()?.clipboardManager
    val des = cm?.primaryClipDescription ?: return ""
    return des.label ?: return ""
}

/**
 * Return the text for clipboard.
 *
 * @return the text for clipboard
 */
fun getText(): CharSequence? {
    val cm = getApplicationByReflect()?.clipboardManager
    val clip = cm?.primaryClip
    if (clip != null && clip.itemCount > 0) {
        val text = clip.getItemAt(0).coerceToText(getApplicationByReflect())
        if (text != null) {
            return text
        }
    }
    return ""
}

/**
 * Add the clipboard changed listener.
 */
fun addChangedListener(listener: OnPrimaryClipChangedListener?) {
    val cm = getApplicationByReflect()?.clipboardManager
    cm?.addPrimaryClipChangedListener(listener)
}

/**
 * Remove the clipboard changed listener.
 */
fun removeChangedListener(listener: OnPrimaryClipChangedListener?) {
    val cm = getApplicationByReflect()?.clipboardManager
    cm?.removePrimaryClipChangedListener(listener)
}