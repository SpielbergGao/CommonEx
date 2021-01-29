package com.spielberg.commonext

import android.content.Context
import android.text.*
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.addTextChangedListenerExt(
    before: ((s: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null,
    onText: ((s: CharSequence?, start: Int, before: Int, count: Int) -> Unit)? = null,
    after: ((s: Editable?) -> Unit)? = null
) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            before?.invoke(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onText?.invoke(s, start, before, count)
        }

        override fun afterTextChanged(s: Editable?) {
            after?.invoke(s)
        }
    })
}

fun View.hideInputMethodExt() {
    val imm: InputMethodManager? =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showInputMethodExt() {
    val imm: InputMethodManager? =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun View.showInputMethodExt(delay: Long) {
    postDelayed({
        showInputMethodExt()
    }, delay)
}