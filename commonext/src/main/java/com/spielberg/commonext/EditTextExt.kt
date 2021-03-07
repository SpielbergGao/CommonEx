package com.spielberg.commonext

import android.content.Context
import android.graphics.Paint
import android.text.*
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView

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

fun EditText.textString(): String {
    return this.text.toString()
}

fun EditText.textStringTrim(): String {
    return this.textString().trim()
}

fun EditText.isEmpty(): Boolean {
    return this.textString().isEmpty()
}

fun EditText.isTrimEmpty(): Boolean {
    return this.textStringTrim().isEmpty()
}

fun TextView.textString(): String {
    return this.text.toString()
}

fun TextView.textStringTrim(): String {
    return this.textString().trim()
}

fun TextView.isEmpty(): Boolean {
    return this.textString().isEmpty()
}

fun TextView.isTrimEmpty(): Boolean {
    return this.textStringTrim().isEmpty()
}

fun TextView.autoSplitTextExt(indent: String): String? {
    val rawText = text.toString() //原始文本
    val tvPaint: Paint = paint //paint，包含字体等信息
    val tvWidth = (width - paddingLeft - paddingRight).toFloat() //控件可用宽度
    //将缩进处理成空格
    var indentSpace = ""
    var indentWidth = 0F
    if (!TextUtils.isEmpty(indent)) {
        val rawIndentWidth: Float = tvPaint.measureText(indent)
        if (rawIndentWidth < tvWidth) {
            while (tvPaint.measureText(indentSpace).also { indentWidth = it } < rawIndentWidth) {
                indentSpace += " "
            }
        }
    }
    //将原始文本按行拆分
    val rawTextLines = rawText.replace("\r".toRegex(), "").split("\n".toRegex()).toTypedArray()
    val sbNewText = StringBuilder()
    for (rawTextLine in rawTextLines) {
        if (tvPaint.measureText(rawTextLine) <= tvWidth) {
            //如果整行宽度在控件可用宽度之内，就不处理了
            sbNewText.append(rawTextLine)
        } else {
            //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
            var lineWidth = 0f
            var cnt = 0
            while (cnt != rawTextLine.length) {
                val ch = rawTextLine[cnt]
                //从手动换行的第二行开始，加上悬挂缩进
                if (lineWidth < 0.1f && cnt != 0) {
                    sbNewText.append(indentSpace)
                    lineWidth += indentWidth
                }
                lineWidth += tvPaint.measureText(ch.toString())
                if (lineWidth <= tvWidth) {
                    sbNewText.append(ch)
                } else {
                    sbNewText.append("\n")
                    lineWidth = 0F
                    --cnt
                }
                ++cnt
            }
        }
        sbNewText.append("\n")
    }
    //把结尾多余的\n去掉
    if (!rawText.endsWith("\n")) {
        sbNewText.deleteCharAt(sbNewText.length - 1)
    }
    return sbNewText.toString()
}


fun String.ToDBCExt(): String? {
    val c = this.toCharArray()
    for (i in c.indices) {
        if (c[i].toInt() == 12288) {
            c[i] = 32.toChar()
            continue
        }
        if (c[i].toInt() in 65281..65374) c[i] = (c[i] - 65248)
    }
    return String(c)
}