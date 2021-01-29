package com.spielberg.commonext

import android.graphics.Paint
import android.text.TextUtils
import android.widget.TextView

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 1/29/21 7:34 PM
 *  @describe 根据指定内容进行换行
 */
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

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 1/29/21 7:34 PM
 *  @describe 半角转圆角
 */
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