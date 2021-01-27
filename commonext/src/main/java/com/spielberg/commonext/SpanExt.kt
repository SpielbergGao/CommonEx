package com.spielberg.commonext

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import android.widget.TextView
import java.util.regex.Pattern


fun CharSequence.toSizeSpanExt(range: IntRange, scale: Float = 1.5F): CharSequence {
    return SpannableString(this).apply {
        setSpan(RelativeSizeSpan(scale), range.first, range.last, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }
}

fun CharSequence.toColorSpanExt(range: IntRange, color: Int = Color.RED): CharSequence {
    return SpannableString(this).apply {
        setSpan(ForegroundColorSpan(color), range.first, range.last, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }
}

fun CharSequence.toBackgroundColorSpanExt(range: IntRange, color: Int = Color.RED): CharSequence {
    return SpannableString(this).apply {
        setSpan(BackgroundColorSpan(color), range.first, range.last, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }
}

fun CharSequence.toStrikeThrougthSpanExt(range: IntRange): CharSequence {
    return SpannableString(this).apply {
        setSpan(StrikethroughSpan(), range.first, range.last, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }
}

fun CharSequence.toClickSpanExt(range: IntRange, color: Int = Color.RED, isUnderlineText: Boolean = false, clickAction: ()->Unit): CharSequence {
    return SpannableString(this).apply {
        val clickableSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                clickAction()
            }
            override fun updateDrawState(ds: TextPaint) {
                ds.color = color
                ds.isUnderlineText = isUnderlineText
            }
        }
        setSpan(clickableSpan, range.first, range.last, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }
}

fun CharSequence.toStyleSpanExt(style: Int = Typeface.BOLD, range: IntRange): CharSequence {
    return SpannableString(this).apply {
        setSpan(StyleSpan(style), range.first, range.last, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }
}

fun TextView.sizeSpanExt(str: String = "", range: IntRange, scale: Float = 1.5f): TextView{
    text = (if(str.isEmpty())text else str).toSizeSpanExt(range, scale)
    return this
}

fun TextView.appendSizeSpanExt(str: String = "", scale: Float = 1.5f): TextView{
    append(str.toSizeSpanExt(0..str.length, scale))
    return this
}

fun TextView.colorSpanExt(str: String = "", range: IntRange, color: Int = Color.RED): TextView{
    text = (if(str.isEmpty())text else str).toColorSpanExt(range, color)
    return this
}

fun TextView.appendColorSpanExt(str: String = "", color: Int = Color.RED): TextView{
    append(str.toColorSpanExt(0..str.length, color))
    return this
}

fun TextView.backgroundColorSpanExt(str: String = "", range: IntRange, color: Int = Color.RED): TextView{
    text = (if(str.isEmpty())text else str).toBackgroundColorSpanExt(range, color)
    return this
}

fun TextView.appendBackgroundColorSpanExt(str: String = "", color: Int = Color.RED): TextView{
    append(str.toBackgroundColorSpanExt(0..str.length, color))
    return this
}

fun TextView.strikeThrougthSpanExt(str: String = "", range: IntRange): TextView{
    text = (if(str.isEmpty())text else str).toStrikeThrougthSpanExt(range)
    return this
}

fun TextView.appendStrikeThrougthSpanExt(str: String = ""): TextView{
    append(str.toStrikeThrougthSpanExt(0..str.length))
    return this
}

fun TextView.clickSpanExt(str: String = "", range: IntRange,
                       color: Int = Color.RED, isUnderlineText: Boolean = false,clickAction: ()->Unit): TextView{
    movementMethod = LinkMovementMethod.getInstance()
    highlightColor = Color.TRANSPARENT  // remove click bg color
    text = (if(str.isEmpty())text else str).toClickSpanExt(range, color, isUnderlineText, clickAction)
    return this
}

fun TextView.appendClickSpanExt(str: String = "", color: Int = Color.RED,
                             isUnderlineText: Boolean = false, clickAction: ()->Unit): TextView{
    movementMethod = LinkMovementMethod.getInstance()
    highlightColor = Color.TRANSPARENT  // remove click bg color
    append(str.toClickSpanExt(0..str.length, color, isUnderlineText, clickAction))
    return this
}

fun TextView.styleSpanExt(str: String = "", range: IntRange, style: Int = Typeface.BOLD): TextView{
    text = (if(str.isEmpty())text else str).toStyleSpanExt(style = style, range =  range)
    return this
}

fun TextView.appendStyleSpanExt(str: String = "", style: Int = Typeface.BOLD): TextView{
    append(str.toStyleSpanExt(style = style, range =  0..str.length))
    return this
}

fun String.formatUrlExt(): String{
    var t = this
    val regex = "((http[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)|((www|m).[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)"
    val compile = Pattern.compile(regex)
    val matcher = compile.matcher(this)
    while (matcher.find()) {
        val linkContent: CharSequence = this.subSequence(matcher.start(), matcher.end())
        t = t.replace(linkContent.toString()," $linkContent ")
    }
    return t
}
