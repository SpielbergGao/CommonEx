package com.spielberg.commonext

import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextPaint
import android.text.TextUtils.TruncateAt
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import java.util.*


/**
 * 获取多个 TextView Hint 文本
 * @param views TextView[]
 * @param <T>   泛型
 * @return [<] 多个 TextView Hint 文本
</T> */
fun getHints(vararg views: TextView?): List<String> {
    val lists: MutableList<String> = ArrayList()
    for (view in views) {
        val text = view?.hint
        if (text != null) {
            lists.add(text.toString())
        }
    }
    return lists
}

/**
 * 设置 Hint 文本
 * @param textView [TextView]
 * @param text     Hint text
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setHint(
    text: CharSequence?
) {
    this?.hint = text
}

/**
 * 获取 Hint 字体颜色
 * @param textView [TextView]
 * @param <T>      泛型
 * @return [ColorStateList]
</T> */
fun TextView?.getHintTextColors(): ColorStateList? {
    return this?.hintTextColors
}

/**
 * 设置 Hint 字体颜色
 * @param textView [TextView]
 * @param color    R.color.id
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setHintTextColor(
    @ColorInt color: Int
) {
    this?.setHintTextColor(color)
}

/**
 * 设置 Hint 字体颜色
 * @param textView [TextView]
 * @param colors   [ColorStateList]
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setHintTextColor(
    colors: ColorStateList?
) {
    this?.setHintTextColor(colors)
}

/**
 * 设置多个 TextView Hint 字体颜色
 * @param color R.color.id
 * @param views TextView[]
 * @param <T>   泛型
 * @return `true` success, `false` fail
</T> */
fun setHintTextColors(
    @ColorInt color: Int,
    vararg views: TextView?
): Boolean {
    for (view in views) {
        view?.setHintTextColor(color)
    }
    return true
}

/**
 * 设置多个 TextView Hint 字体颜色
 * @param colors [ColorStateList]
 * @param views  TextView[]
 * @param <T>    泛型
 * @return `true` success, `false` fail
</T> */
fun setHintTextColors(
    colors: ColorStateList?,
    vararg views: TextView
): Boolean {
    for (view in views) {
        view.setHintTextColor(colors)
    }
    return true
}

/**
 * 获取文本
 * @param textView [TextView]
 * @param <T>      泛型
 * @return [TextView.getText]
</T> */
fun TextView?.getText(): String? {
    return this?.text?.toString()
}

/**
 * 获取多个 TextView 文本
 * @param views TextView[]
 * @param <T>   泛型
 * @return [<] 多个 TextView 文本
</T> */
fun getTexts(vararg views: TextView): List<String> {
    val lists: MutableList<String> = ArrayList()
    for (view in views) {
        val text = view.text
        if (text != null) {
            lists.add(text.toString())
        }
    }
    return lists
}

/**
 * 获取字体颜色
 * @param textView [TextView]
 * @param <T>      泛型
 * @return [ColorStateList]
</T> */
fun TextView?.getTextColors(): ColorStateList? {
    return this?.textColors
}


/**
 * 设置字体颜色
 * @param textView [TextView]
 * @param color    R.color.id
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setTextColor(
    @ColorInt color: Int
) {
    this?.setTextColor(color)
}

/**
 * 设置字体颜色
 * @param textView [TextView]
 * @param colors   [ColorStateList]
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setTextColor(
    colors: ColorStateList?
) {
    this?.setTextColor(colors)
}

/**
 * 设置多个 TextView 字体颜色
 * @param color R.color.id
 * @param views TextView[]
 * @param <T>   泛型
 * @return `true` success, `false` fail
</T> */
fun setTextColors(
    @ColorInt color: Int,
    vararg views: TextView
): Boolean {
    for (view in views) {
        view.setTextColor(color)
    }
    return true
}

/**
 * 设置多个 TextView 字体颜色
 * @param colors [ColorStateList]
 * @param views  TextView[]
 * @param <T>    泛型
 * @return `true` success, `false` fail
</T> */
fun setTextColors(
    colors: ColorStateList?,
    vararg views: TextView
): Boolean {
    for (view in views) {
        view.setTextColor(colors)
    }
    return true
}

/**
 * 设置 Html 内容
 * @param textView [TextView]
 * @param content  Html content
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setHtmlText(
    content: String?
) {
    if (this != null && content != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.text = Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY)
        } else {
            this.text = Html.fromHtml(content)
        }
    }
}

/**
 * 设置多个 TextView Html 内容
 * @param content Html content
 * @param views   TextView[]
 * @param <T>     泛型
 * @return `true` success, `false` fail
</T> */
fun setHtmlTexts(
    content: String?,
    vararg views: TextView
): Boolean {
    if (content != null) {
        for (view in views) {
            view.setHtmlText(content)
        }
        return true
    }
    return false
}

/**
 * 获取字体
 * @param textView [TextView]
 * @param <T>      泛型
 * @return [Typeface]
</T> */
fun <T : TextView?> getTypeface(textView: T?): Typeface? {
    return textView?.typeface
}

/**
 * 设置字体
 * @param textView [TextView]
 * @param typeface [Typeface] 字体样式
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setTypeface(
    typeface: Typeface?
) {
    this?.setTypeface(typeface)
}

/**
 * 设置字体
 * @param textView [TextView]
 * @param typeface [Typeface] 字体样式
 * @param style    样式
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setTypeface(
    typeface: Typeface?,
    style: Int
) {
    this?.setTypeface(typeface, style)
}

/**
 * 设置字体大小 ( px 像素 )
 * @param textView [TextView]
 * @param size     字体大小
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setTextSizeByPx(
    size: Float
) {
    this?.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
}

/**
 * 设置字体大小 ( sp 缩放像素 )
 * @param textView [TextView]
 * @param size     字体大小
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setTextSizeBySp(
    size: Float
) {
    this?.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
}

/**
 * 设置字体大小 ( dp 与设备无关的像素 )
 * @param textView [TextView]
 * @param size     字体大小
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setTextSizeByDp(
    size: Float
) {
    this?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
}

/**
 * 设置字体大小 ( inches 英寸 )
 * @param textView [TextView]
 * @param size     字体大小
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setTextSizeByIn(
    size: Float
) {
    this?.setTextSize(TypedValue.COMPLEX_UNIT_IN, size)
}

/**
 * 设置字体大小
 * @param textView [TextView]
 * @param unit     字体参数类型
 * @param size     字体大小
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setTextSize(
    unit: Int,
    size: Float
) {
    this?.setTextSize(unit, size)
}

/**
 * 设置多个 TextView 字体大小
 * @param views TextView[]
 * @param unit  参数类型
 * @param size  字体大小
 * @param <T>   泛型
 * @return `true` success, `false` fail
</T> */
fun setTextSizes(
    views: Array<TextView>?,
    unit: Int,
    size: Float
): Boolean {
    if (views != null) {
        for (view in views) {
            view.setTextSize(unit, size)
        }
        return true
    }
    return false
}
// =
/**
 * 获取 TextView 字体大小 ( px )
 * @param textView [TextView]
 * @param <T>      泛型
 * @return 字体大小 (px)
</T> */
fun TextView?.getTextSize(): Float {
    return this?.textSize ?: -1f
}

/**
 * 清空 flags
 * @param textView [TextView]
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.clearFlags() {
    this?.paintFlags = 0
}

/**
 * 设置 TextView 抗锯齿 flags
 * @param textView [TextView]
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setAntiAliasFlag() {
    this?.paintFlags = Paint.ANTI_ALIAS_FLAG
}

/**
 * 设置 TextView 是否加粗
 * @param textView [TextView]
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setBold() {
    this?.setBold(true)
}

/**
 * 设置 TextView 是否加粗
 * @param textView [TextView]
 * @param isBold   `true` yes, `false` no
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setBold(
    isBold: Boolean
) {
    this?.setTypeface(Typeface.defaultFromStyle(if (isBold) Typeface.BOLD else Typeface.NORMAL))
}

/**
 * 设置 TextView 是否加粗
 * @param textView [TextView]
 * @param typeface [Typeface] 字体样式
 * @param isBold   `true` yes, `false` no
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setBold(
    typeface: Typeface?,
    isBold: Boolean
) {
    this?.setTypeface(typeface, if (isBold) Typeface.BOLD else Typeface.NORMAL)
}

/**
 * 设置下划线
 * @param textView [TextView]
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setUnderlineText() {
    this?.setUnderlineText(true)
}

/**
 * 设置下划线并加清晰
 * @param textView    [TextView]
 * @param isAntiAlias 是否消除锯齿
 * @param <T>         泛型
 * @return [TextView]
</T> */
fun TextView?.setUnderlineText(
    isAntiAlias: Boolean
){
    this?.paintFlags = this?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG)?:0
    if (isAntiAlias) {
        this?.paintFlags = this?.paintFlags?.or(Paint.ANTI_ALIAS_FLAG) ?: 0
    }
}

/**
 * 设置中划线
 * @param textView [TextView]
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setStrikeThruText(){
    this?. setStrikeThruText(true)
}

/**
 * 设置中划线并加清晰
 * @param textView    [TextView]
 * @param isAntiAlias 是否消除锯齿
 * @param <T>         泛型
 * @return [TextView]
</T> */
fun TextView?.setStrikeThruText(
    isAntiAlias: Boolean
){
    this?.paintFlags = this?.paintFlags?.or(Paint.STRIKE_THRU_TEXT_FLAG) ?: 0
    if (isAntiAlias) {
        this?.paintFlags = this?.paintFlags?.or(Paint.ANTI_ALIAS_FLAG) ?: 0
    }
}

// =
/**
 * 获取文字水平间距
 * @param textView [TextView]
 * @param <T>      泛型
 * @return 文字水平间距
</T> */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
fun TextView?.getLetterSpacing(): Float {
    return this?.letterSpacing ?: 0f
}

/**
 * 设置文字水平间距
 * <pre>
 * android:letterSpacing
</pre> *
 * @param textView      [TextView]
 * @param letterSpacing 文字水平间距
 * @param <T>           泛型
 * @return [TextView]
</T> */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
fun <T : TextView?> setLetterSpacing(
    textView: T?,
    letterSpacing: Float
): T? {
    if (textView != null) {
        textView.letterSpacing = letterSpacing
    }
    return textView
}

/**
 * 设置文字水平间距
 * @param view          [TextView]
 * @param letterSpacing 文字水平间距
 * @return [View]
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
fun setLetterSpacing(
    view: View?,
    letterSpacing: Float
): View? {
    setLetterSpacing(getTextView<TextView>(view), letterSpacing)
    return view
}
// =
/**
 * 获取文字行间距 ( 行高 )
 * @param textView [TextView]
 * @param <T>      泛型
 * @return 文字行间距 ( 行高 )
</T> */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
fun <T : TextView?> getLineSpacingExtra(textView: T?): Float {
    return textView?.lineSpacingExtra ?: 0f
}

/**
 * 获取文字行间距倍数
 * @param textView [TextView]
 * @param <T>      泛型
 * @return 文字行间距倍数
</T> */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
fun <T : TextView?> getLineSpacingMultiplier(textView: T?): Float {
    return textView?.lineSpacingMultiplier ?: 0f
}

/**
 * 设置文字行间距 ( 行高 )
 * @param textView    [TextView]
 * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
 * @param <T>         泛型
 * @return [TextView]
</T> */
fun <T : TextView?> setLineSpacing(
    textView: T,
    lineSpacing: Float
): T {
    return setLineSpacingAndMultiplier(textView, lineSpacing, 1.0f)
}

/**
 * 设置文字行间距 ( 行高 )、行间距倍数
 * @param textView    [TextView]
 * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
 * @param multiplier  行间距倍数, android:lineSpacingMultiplier
 * @param <T>         泛型
 * @return [TextView]
</T> */
fun <T : TextView?> setLineSpacingAndMultiplier(
    textView: T?,
    lineSpacing: Float,
    multiplier: Float
): T? {
    textView?.setLineSpacing(lineSpacing, multiplier)
    return textView
}
// =
/**
 * 设置文字行间距 ( 行高 )
 * @param view        [TextView]
 * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
 * @return [View]
 */
fun setLineSpacing(
    view: View?,
    lineSpacing: Float
): View? {
    setLineSpacingAndMultiplier(getTextView<TextView>(view), lineSpacing, 1.0f)
    return view
}

/**
 * 设置文字行间距 ( 行高 )、行间距倍数
 * @param view        [TextView]
 * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
 * @param multiplier  行间距倍数, android:lineSpacingMultiplier
 * @return [View]
 */
fun setLineSpacingAndMultiplier(
    view: View?,
    lineSpacing: Float,
    multiplier: Float
): View? {
    setLineSpacingAndMultiplier(getTextView<TextView>(view), lineSpacing, multiplier)
    return view
}
// =
/**
 * 获取字体水平方向的缩放
 * @param textView [TextView]
 * @param <T>      泛型
 * @return 字体水平方向的缩放
</T> */
fun <T : TextView?> getTextScaleX(textView: T?): Float {
    return textView?.textScaleX ?: 0f
}

/**
 * 设置字体水平方向的缩放
 * <pre>
 * android:textScaleX
</pre> *
 * @param textView [TextView]
 * @param size     缩放比例
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun <T : TextView?> setTextScaleX(
    textView: T?,
    size: Float
): T? {
    if (textView != null) {
        textView.textScaleX = size
    }
    return textView
}

/**
 * 设置字体水平方向的缩放
 * @param view [TextView]
 * @param size 缩放比例
 * @return [View]
 */
fun setTextScaleX(
    view: View?,
    size: Float
): View? {
    setTextScaleX(getTextView<TextView>(view), size)
    return view
}
// =
/**
 * 是否保留字体留白间隙区域
 * @param textView [TextView]
 * @param <T>      泛型
 * @return `true` yes, `false` no
</T> */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
fun <T : TextView?> getIncludeFontPadding(textView: T?): Boolean {
    return textView?.includeFontPadding ?: false
}

/**
 * 设置是否保留字体留白间隙区域
 * <pre>
 * android:includeFontPadding
</pre> *
 * @param textView       [TextView]
 * @param includePadding 是否保留字体留白间隙区域
 * @param <T>            泛型
 * @return [TextView]
</T> */
fun <T : TextView?> setIncludeFontPadding(
    textView: T?,
    includePadding: Boolean
): T? {
    if (textView != null) {
        textView.includeFontPadding = includePadding
    }
    return textView
}

/**
 * 设置是否保留字体留白间隙区域
 * @param view           [TextView]
 * @param includePadding 是否保留字体留白间隙区域
 * @return [View]
 */
fun setIncludeFontPadding(
    view: View?,
    includePadding: Boolean
): View? {
    setIncludeFontPadding(getTextView<TextView>(view), includePadding)
    return view
}
// =
/**
 * 获取输入类型
 * @param textView [TextView]
 * @param <T>      泛型
 * @return 输入类型
</T> */
fun <T : TextView?> getInputType(textView: T?): Int {
    return textView?.inputType ?: 0
}

/**
 * 设置输入类型
 * @param textView [TextView]
 * @param type     类型
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun <T : TextView?> setInputType(
    textView: T?,
    type: Int
): T? {
    if (textView != null) {
        textView.inputType = type
    }
    return textView
}

/**
 * 设置输入类型
 * @param view [TextView]
 * @param type 类型
 * @return [View]
 */
fun setInputType(
    view: View?,
    type: Int
): View? {
    setInputType(getTextView<TextView>(view), type)
    return view
}
// =
/**
 * 获取软键盘右下角按钮类型
 * @param textView [TextView]
 * @param <T>      泛型
 * @return 软键盘右下角按钮类型
</T> */
fun <T : TextView?> getImeOptions(textView: T?): Int {
    return textView?.imeOptions ?: 0
}

/**
 * 设置软键盘右下角按钮类型
 * @param textView   [TextView]
 * @param imeOptions 软键盘按钮类型
 * @param <T>        泛型
 * @return [TextView]
</T> */
fun <T : TextView?> setImeOptions(
    textView: T?,
    imeOptions: Int
): T? {
    if (textView != null) {
        textView.imeOptions = imeOptions
    }
    return textView
}

/**
 * 设置软键盘右下角按钮类型
 * @param view       [TextView]
 * @param imeOptions 软键盘按钮类型
 * @return [View]
 */
fun setImeOptions(
    view: View?,
    imeOptions: Int
): View? {
    setImeOptions(getTextView<TextView>(view), imeOptions)
    return view
}
// =
/**
 * 设置行数
 * @param textView [TextView]
 * @param lines    行数
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun <T : TextView?> setLines(
    textView: T?,
    lines: Int
): T? {
    textView?.setLines(lines)
    return textView
}

/**
 * 设置行数
 * @param view  [TextView]
 * @param lines 行数
 * @return [View]
 */
fun setLines(
    view: View?,
    lines: Int
): View? {
    setLines(getTextView<TextView>(view), lines)
    return view
}
// =
/**
 * 获取最大行数
 * @param textView [TextView]
 * @param <T>      泛型
 * @return 最大行数
</T> */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
fun <T : TextView?> getMaxLines(textView: T?): Int {
    return textView?.maxLines ?: 0
}

/**
 * 设置最大行数
 * @param textView [TextView]
 * @param maxLines 最大行数
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun <T : TextView?> setMaxLines(
    textView: T?,
    maxLines: Int
): T? {
    if (textView != null) {
        textView.maxLines = maxLines
    }
    return textView
}

/**
 * 设置最大行数
 * @param view     [TextView]
 * @param maxLines 最大行数
 * @return [View]
 */
fun setMaxLines(
    view: View?,
    maxLines: Int
): View? {
    setMaxLines(getTextView<TextView>(view), maxLines)
    return view
}
// =
/**
 * 获取最小行数
 * @param textView [TextView]
 * @param <T>      泛型
 * @return 最小行数
</T> */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
fun <T : TextView?> getMinLines(textView: T?): Int {
    return textView?.minLines ?: 0
}

/**
 * 设置最小行数
 * @param textView [TextView]
 * @param minLines 最小行数
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun <T : TextView?> setMinLines(
    textView: T?,
    minLines: Int
): T? {
    if (textView != null && minLines > 0) {
        textView.minLines = minLines
    }
    return textView
}

/**
 * 设置最小行数
 * @param view     [TextView]
 * @param minLines 最小行数
 * @return [View]
 */
fun setMinLines(
    view: View?,
    minLines: Int
): View? {
    setMinLines(getTextView<TextView>(view), minLines)
    return view
}
// =
/**
 * 获取最大字符宽度限制
 * @param textView [TextView]
 * @param <T>      泛型
 * @return 最大字符宽度限制
</T> */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
fun <T : TextView?> getMaxEms(textView: T?): Int {
    return textView?.maxEms ?: 0
}

/**
 * 设置最大字符宽度限制
 * @param textView [TextView]
 * @param maxEms   最大字符
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun <T : TextView?> setMaxEms(
    textView: T?,
    maxEms: Int
): T? {
    if (textView != null && maxEms > 0) {
        textView.maxEms = maxEms
    }
    return textView
}

/**
 * 设置最大字符宽度限制
 * @param view   [TextView]
 * @param maxEms 最大字符
 * @return [View]
 */
fun setMaxEms(
    view: View?,
    maxEms: Int
): View? {
    setMaxEms(getTextView<TextView>(view), maxEms)
    return view
}
// =
/**
 * 获取最小字符宽度限制
 * @param textView [TextView]
 * @param <T>      泛型
 * @return 最小字符宽度限制
</T> */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
fun <T : TextView?> getMinEms(textView: T?): Int {
    return textView?.minEms ?: 0
}

/**
 * 设置最小字符宽度限制
 * @param textView [TextView]
 * @param minEms   最小字符
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun <T : TextView?> setMinEms(
    textView: T?,
    minEms: Int
): T? {
    if (textView != null && minEms > 0) {
        textView.minEms = minEms
    }
    return textView
}

/**
 * 设置最小字符宽度限制
 * @param view   [TextView]
 * @param minEms 最小字符
 * @return [View]
 */
fun setMinEms(
    view: View?,
    minEms: Int
): View? {
    setMinEms(getTextView<TextView>(view), minEms)
    return view
}
// =
/**
 * 设置指定字符宽度
 * @param textView [TextView]
 * @param ems      字符
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun <T : TextView?> setEms(
    textView: T?,
    ems: Int
): T? {
    if (textView != null && ems > 0) {
        textView.setEms(ems)
    }
    return textView
}

/**
 * 设置指定字符宽度
 * @param view [TextView]
 * @param ems  字符
 * @return [View]
 */
fun setEms(
    view: View?,
    ems: Int
): View? {
    setEms(getTextView<TextView>(view), ems)
    return view
}
// =
/**
 * 设置长度限制
 * @param textView  [TextView]
 * @param maxLength 长度限制
 * @param <T>       泛型
 * @return [TextView]
</T> */
fun <T : TextView?> setMaxLength(
    textView: T?,
    maxLength: Int
): T? {
    if (textView != null && maxLength > 0) {
        // 设置最大长度限制
        val filters = arrayOf<InputFilter>(LengthFilter(maxLength))
        textView.filters = filters
    }
    return textView
}

/**
 * 设置长度限制
 * @param view      [TextView]
 * @param maxLength 长度限制
 * @return [View]
 */
fun setMaxLength(
    view: View?,
    maxLength: Int
): View? {
    setMaxLength(getTextView<TextView>(view), maxLength)
    return view
}
// =
/**
 * 设置长度限制, 并且设置内容
 * @param textView  [TextView]
 * @param content   文本内容
 * @param maxLength 长度限制
 * @param <T>       泛型
 * @return [TextView]
</T> */
fun TextView?.setMaxLengthAndText(
    content: CharSequence?,
    maxLength: Int
) {
    setText(setMaxLength(this, maxLength), content)
}

/**
 * 设置长度限制, 并且设置内容
 * @param view      [TextView]
 * @param content   文本内容
 * @param maxLength 长度限制
 * @return [View]
 */
fun setMaxLengthAndText(
    view: View?,
    content: CharSequence?,
    maxLength: Int
): View? {
    return setText(setMaxLength(view, maxLength), content)
}

/**
 * 设置 Ellipsize 效果
 * @param textView [TextView]
 * @param where    [TextUtils.TruncateAt]
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setEllipsize(
    where: TruncateAt?
) {
    this?.ellipsize = where
}

/**
 * 获取自动识别文本类型
 * @param textView [TextView]
 * @param <T>      泛型
 * @return 自动识别文本类型
</T> */
fun <T : TextView?> getAutoLinkMask(textView: T?): Int {
    return textView?.autoLinkMask ?: 0
}

/**
 * 设置自动识别文本链接
 * @param textView [TextView]
 * @param mask     [android.text.util.Linkify]
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setAutoLinkMask(
    mask: Int
) {
    this?.autoLinkMask = mask
}

/**
 * 设置文本全为大写
 * @param textView [TextView]
 * @param allCaps  是否全部大写
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setAllCaps(
    allCaps: Boolean
) {
    this?.isAllCaps = allCaps
}

/**
 * 获取文本视图显示转换
 * @param textView [TextView]
 * @param <T>      泛型
 * @return [TransformationMethod]
</T> */
fun TextView?.getTransformationMethod(): TransformationMethod? {
    return this?.transformationMethod
}

/**
 * 设置文本视图显示转换
 * @param textView [TextView]
 * @param method   [TransformationMethod]
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setTransformationMethod(
    method: TransformationMethod?
) {
    this?.transformationMethod = method
}

/**
 * 设置密码文本视图显示转换
 * @param textView          [TextView]
 * @param isDisplayPassword 是否显示密码
 * @param <T>               泛型
 * @return [TextView]
</T> */
fun TextView?.setTransformationMethod(
    isDisplayPassword: Boolean
) {
    this?.transformationMethod =
        if (isDisplayPassword) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
}

/**
 * 获取 TextView Paint
 * @param textView [TextView]
 * @param <T>      泛型
 * @return [Paint]
</T> */
fun TextView?.getPaint(): Paint? {
    return this?.paint
}

/**
 * 获取字体高度
 * @param textView [TextView]
 * @param <T>      泛型
 * @return 字体高度
</T> */
fun TextView?.getTextHeight(): Int {
    return getTextHeight(this?.paint)
}

/**
 * 获取字体高度
 * @param paint [TextView.getPaint]
 * @return 字体高度
 */
fun getTextHeight(paint: Paint?): Int {
    if (paint != null) {
        // 获取字体高度
        val fontMetrics = paint.fontMetricsInt
        // 计算内容高度
        return Math.ceil((fontMetrics.descent - fontMetrics.ascent).toDouble()).toInt()
    }
    return -1
}

/**
 * 获取字体顶部偏移高度
 * @param textView [TextView]
 * @param <T>      泛型
 * @return 字体顶部偏移高度
</T> */
fun TextView?.getTextTopOffsetHeight(): Int {
    return getTextTopOffsetHeight(getPaint(this))
}

/**
 * 获取字体顶部偏移高度
 * @param paint [TextView.getPaint]
 * @return 字体顶部偏移高度
 */
fun getTextTopOffsetHeight(paint: Paint?): Int {
    if (paint != null) {
        // 获取字体高度
        val fontMetrics = paint.fontMetricsInt
        // 计算字体偏差 ( 顶部偏差 ) baseLine
        return Math.ceil((Math.abs(fontMetrics.top) - Math.abs(fontMetrics.ascent)).toDouble())
            .toInt()
    }
    return -1
}

/**
 * 计算字体宽度
 * @param textView [TextView]
 * @param text     待测量文本
 * @param <T>      泛型
 * @return 字体宽度
</T> */
fun TextView?.getTextWidth(
    text: String?
): Float {
    return getTextWidth(this.getPaint(), text)
}

/**
 * 计算字体宽度
 * @param textView [TextView]
 * @param <T>      泛型
 * @return 字体宽度
</T> */
fun TextView?.getTextWidth(): Float {
    return getTextWidth(this.getPaint(), getText(this))
}

/**
 * 计算字体宽度
 * @param paint [TextView.getPaint]
 * @param text  待测量文本
 * @return 字体宽度
 */
fun getTextWidth(
    paint: Paint?,
    text: String?
): Float {
    return if (paint != null && text != null) {
        paint.measureText(text)
    } else -1f
}
// =
/**
 * 计算字体宽度
 * @param view  [TextView]
 * @param text  待测量文本
 * @param start 开始位置
 * @param end   结束位置
 * @return 字体宽度
 */
fun getTextWidth(
    view: View?,
    text: String?,
    start: Int,
    end: Int
): Float {
    return getTextWidth(view.getPaint(), text, start, end)
}

/**
 * 计算字体宽度
 * @param view  [TextView]
 * @param text  待测量文本
 * @param start 开始位置
 * @param end   结束位置
 * @return 字体宽度
 */
fun getTextWidth(
    view: View?,
    text: CharSequence?,
    start: Int,
    end: Int
): Float {
    return getTextWidth(view.getPaint(), text, start, end)
}

/**
 * 计算字体宽度
 * @param view  [TextView]
 * @param text  待测量文本
 * @param start 开始位置
 * @param end   结束位置
 * @return 字体宽度
 */
fun getTextWidth(
    view: View?,
    text: CharArray?,
    start: Int,
    end: Int
): Float {
    return getTextWidth(view?.getPaint(), text, start, end)
}

/**
 * 计算字体宽度
 * @param paint [TextView.getPaint]
 * @param text  待测量文本
 * @param start 开始位置
 * @param end   结束位置
 * @return 字体宽度
 */
fun getTextWidth(
    paint: Paint?,
    text: String?,
    start: Int,
    end: Int
): Float {
    if (paint != null && text != null) {
        try {
            return paint.measureText(text, start, end)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return -1f
}

/**
 * 计算字体宽度
 * @param paint [TextView.getPaint]
 * @param text  待测量文本
 * @param start 开始位置
 * @param end   结束位置
 * @return 字体宽度
 */
fun getTextWidth(
    paint: Paint?,
    text: CharSequence?,
    start: Int,
    end: Int
): Float {
    if (paint != null && text != null) {
        try {
            return paint.measureText(text, start, end)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return -1f
}

/**
 * 计算字体宽度
 * @param paint [TextView.getPaint]
 * @param text  待测量文本
 * @param start 开始位置
 * @param end   结束位置
 * @return 字体宽度
 */
fun getTextWidth(
    paint: Paint?,
    text: CharArray?,
    start: Int,
    end: Int
): Float {
    if (paint != null && text != null) {
        try {
            return paint.measureText(text, start, end)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return -1f
}

/**
 * 获取画布中间居中位置
 * @param targetRect [Rect] 目标坐标
 * @param paint      [TextView.getPaint]
 * @return 画布 Y 轴居中位置
 */
fun getCenterRectY(
    targetRect: Rect?,
    paint: Paint?
): Int {
    if (targetRect != null && paint != null) {
        // 获取字体高度
        val fontMetrics = paint.fontMetricsInt
        // 获取底部 Y 轴居中位置
        return targetRect.top + (targetRect.bottom - targetRect.top) / 2 - (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.top
        // canvas.drawText(testString, targetRect.centerX(), baseline, paint);
    }
    return -1
}

/**
 * 通过需要的高度, 计算字体大小
 * @param height 需要的高度
 * @return 字体大小
 */
@JvmOverloads
fun reckonTextSizeByHeight(
    height: Int,
    startSize: Float = 40.0f
): Float {
    if (height <= 0 || startSize <= 0) return 0f
    val paint = Paint()
    // 默认字体大小
    var textSize = startSize
    // 计算内容高度
    var calcTextHeight: Int
    // 特殊处理 ( 防止死循环记录控制 )
    var state = 0 // 1 -=, 2 +=
    // 循环计算
    while (true) {
        // 设置画笔大小
        paint.textSize = textSize
        // 获取字体高度
        val fontMetrics = paint.fontMetricsInt
        // 计算内容高度
        calcTextHeight =
            Math.ceil((fontMetrics.descent - fontMetrics.ascent).toDouble()).toInt()
        // 符合条件则直接返回
        if (calcTextHeight == height) {
            return textSize
        } else if (calcTextHeight > height) { // 如果计算的字体高度大于
            textSize -= 0.5f
            if (state == 2) {
                if (calcTextHeight < height) {
                    return textSize
                }
            }
            state = 1
        } else {
            textSize += 0.5f
            if (state == 1) {
                if (calcTextHeight < height) {
                    return textSize
                }
            }
            state = 2
        }
    }
}

/**
 * 通过需要的宽度, 计算字体大小 ( 最接近该宽度的字体大小 )
 * @param width    需要的宽度
 * @param textView [TextView]
 * @return 字体大小
 */
@JvmOverloads
fun reckonTextSizeByWidth(
    width: Int,
    textView: TextView?,
    content: String? = getText<TextView?>(textView)
): Float {
    return if (textView == null || content == null) 0f else reckonTextSizeByWidth(
        width, textView.paint,
        getTextSize(textView), content
    )
}

/**
 * 通过需要的宽度, 计算字体大小 ( 最接近该宽度的字体大小 )
 * @param width       需要的宽度
 * @param curTextSize 当前字体大小
 * @param content     待计算内容
 * @return 字体大小
 */
fun reckonTextSizeByWidth(
    width: Int,
    curTextSize: Float,
    content: String?
): Float {
    return if (width <= 0 || curTextSize <= 0 || content == null) 0f else reckonTextSizeByWidth(
        width,
        Paint(),
        curTextSize,
        content
    )
}

/**
 * 通过需要的宽度, 计算字体大小 ( 最接近该宽度的字体大小 )
 * @param width       需要的宽度
 * @param paint       [Paint]
 * @param curTextSize 当前字体大小
 * @param content     待计算内容
 * @return 字体大小
 */
fun reckonTextSizeByWidth(
    width: Int,
    paint: Paint?,
    curTextSize: Float,
    content: String?
): Float {
    if (paint == null || width <= 0 || curTextSize <= 0 || content == null) return 0f
    if (content.isNullOrEmpty()) return curTextSize
    // 初始化内容画笔, 计算宽高
    val tvPaint = TextPaint(paint)
    // 字体大小
    var textSize = curTextSize
    // 字体内容宽度
    var calcTextWidth: Int
    // 特殊处理 ( 防止死循环记录控制 )
    var state = 0 // 1 -=, 2 +=
    // 循环计算
    while (true) {
        // 设置画笔大小
        tvPaint.textSize = textSize
        // 获取字体内容宽度
        calcTextWidth = tvPaint.measureText(content).toInt()
        // 符合条件则直接返回
        if (calcTextWidth == width) {
            return textSize
        } else if (calcTextWidth > width) { // 如果计算的字体宽度大于
            textSize -= 0.5f
            if (state == 2) {
                if (calcTextWidth < width) {
                    return textSize
                }
            }
            state = 1
        } else {
            textSize += 0.5f
            if (state == 1) {
                if (calcTextWidth < width) {
                    return textSize
                }
            }
            state = 2
        }
    }
}

/**
 * 计算第几位超过宽度
 * @param textView [TextView]
 * @param text     待测量文本
 * @param width    指定的宽度
 * @param <T>      泛型
 * @return -1 表示没超过, 其他值表示对应的索引位置
</T> */
fun TextView?.calcTextWidth(
    text: String?,
    width: Float
): Int {
    return calcTextWidth(getPaint(this), text, width)
}

/**
 * 计算第几位超过宽度
 * @param textView [TextView]
 * @param width    指定的宽度
 * @param <T>      泛型
 * @return -1 表示没超过, 其他值表示对应的索引位置
</T> */
fun TextView?.calcTextWidth(width: Float): Int {
    return calcTextWidth(getPaint(this), getText(this), width)
}

/**
 * 计算第几位超过宽度
 * @param paint [TextView.getPaint]
 * @param text  文本内容
 * @param width 指定的宽度
 * @return -1 表示没超过, 其他值表示对应的索引位置
 */
fun calcTextWidth(
    paint: Paint?,
    text: String?,
    width: Float
): Int {
    if (paint != null && text != null && width > 0) {
        // 全部文本宽度
        val allTextWidth = getTextWidth(paint, text)
        // 判断是否超过
        if (allTextWidth <= width) return -1 // 表示没超过
        // 获取数据长度
        val length = text.length
        // 超过长度且只有一个数据, 那么只能是第一个就超过了
        if (length == 1) return 1
        // 二分法寻找最佳位置
        var start = 0
        var end = length
        var mid = 0
        // 判断是否大于位置
        while (start < end) {
            mid = (start + end) / 2
            // 获取字体宽度
            val textWidth = getTextWidth(paint, text, 0, mid)
            // 如果相等直接返回
            if (textWidth == width) {
                return mid
            } else if (textWidth > width) {
                end = mid - 1
            } else {
                start = mid + 1
            }
        }
        // 计算最符合的位置
        var i = Math.min(Math.min(start, mid), end)
        while (i <= length) {
            val textWidth = getTextWidth(paint, text, 0, i)
            if (textWidth >= width) return i
            i++
        }
        return start
    }
    return -1
}

/**
 * 计算文本换行行数
 * @param textView [TextView]
 * @param text     待测量文本
 * @param width    指定的宽度
 * @param <T>      泛型
 * @return 行数
</T> */
fun TextView?.calcTextLine(
    text: String?,
    width: Float
): Int {
    return calcTextLine(getPaint(this), text, width)
}

/**
 * 计算文本行数
 * @param textView [TextView]
 * @param width    指定的宽度
 * @param <T>      泛型
 * @return 行数
</T> */
fun TextView?.calcTextLine(
    width: Float
): Int {
    return calcTextLine(getPaint(this), getText(this), width)
}

/**
 * 计算文本行数
 * @param paint [TextView.getPaint]
 * @param text  文本内容
 * @param width 指定的宽度
 * @return 行数
 */
fun calcTextLine(
    paint: Paint?,
    text: String?,
    width: Float
): Int {
    if (paint != null && text != null && width > 0) {
        // 全部文本宽度
        val allTextWidth = getTextWidth(paint, text)
        // 判断是否超过
        if (allTextWidth <= width) return 1
        val result = (allTextWidth / width).toInt()
        return if (allTextWidth - width * result == 0f) result else result + 1
    }
    return 0
}

/**
 * 设置 CompoundDrawables Padding
 * @param textView [TextView]
 * @param padding  CompoundDrawables Padding
 * @param <T>      泛型
 * @return [View]
</T> */
fun TextView?.setCompoundDrawablePadding(
    padding: Int
) {
    this?.compoundDrawablePadding = padding
}

// ========================
// = setCompoundDrawables =
// ========================
/**
 * 设置 Left CompoundDrawables
 * @param textView [TextView]
 * @param left     left Drawable
 * @param <T>      泛型
 * @return [View]
</T> */
fun TextView?.setCompoundDrawablesByLeft(
    left: Drawable?
) {
    this?.setCompoundDrawables(left, null, null, null)
}

/**
 * 设置 Top CompoundDrawables
 * @param textView [TextView]
 * @param top      top Drawable
 * @param <T>      泛型
 * @return [View]
</T> */
fun TextView?.setCompoundDrawablesByTop(
    top: Drawable?
) {
    this?.setCompoundDrawables(null, top, null, null)
}

/**
 * 设置 Right CompoundDrawables
 * @param textView [TextView]
 * @param right    right Drawable
 * @param <T>      泛型
 * @return [View]
</T> */
fun TextView?.setCompoundDrawablesByRight(
    right: Drawable?
) {
    this?.setCompoundDrawables(null, null, right, null)
}

/**
 * 设置 Bottom CompoundDrawables
 * @param textView [TextView]
 * @param bottom   bottom Drawable
 * @param <T>      泛型
 * @return [View]
</T> */
fun TextView?.setCompoundDrawablesByBottom(
    bottom: Drawable?
) {
    this?.setCompoundDrawables(null, null, null, bottom)
}

/**
 * 设置 CompoundDrawables
 * <pre>
 * CompoundDrawable 的大小控制是通过 drawable.setBounds() 控制
 * 需要先设置 Drawable 的 setBounds
 * [dev.utils.app.image.ImageUtils.setBounds]
</pre> *
 * @param textView [TextView]
 * @param left     left Drawable
 * @param top      top Drawable
 * @param right    right Drawable
 * @param bottom   bottom Drawable
 * @param <T>      泛型
 * @return [View]
</T> */
fun TextView?.setCompoundDrawables(
    left: Drawable?,
    top: Drawable?,
    right: Drawable?,
    bottom: Drawable?
) {
    this?.setCompoundDrawables(left, top, right, bottom)
}
// ===========================================
// = setCompoundDrawablesWithIntrinsicBounds =
// ===========================================
/**
 * 设置 Left CompoundDrawables ( 按照原有比例大小显示图片 )
 * @param textView [TextView]
 * @param left     left Drawable
 * @param <T>      泛型
 * @return [View]
</T> */
fun TextView?.setCompoundDrawablesWithIntrinsicBoundsByLeft(
    left: Drawable?
) {
    this?.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null)
}

/**
 * 设置 Top CompoundDrawables ( 按照原有比例大小显示图片 )
 * @param textView [TextView]
 * @param top      top Drawable
 * @param <T>      泛型
 * @return [View]
</T> */
fun TextView?.setCompoundDrawablesWithIntrinsicBoundsByTop(
    top: Drawable?
) {
    this?.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null)
}

/**
 * 设置 Right CompoundDrawables ( 按照原有比例大小显示图片 )
 * @param textView [TextView]
 * @param right    right Drawable
 * @param <T>      泛型
 * @return [View]
</T> */
fun TextView?.setCompoundDrawablesWithIntrinsicBoundsByRight(
    right: Drawable?
) {
    this?.setCompoundDrawablesWithIntrinsicBounds(null, null, right, null)
}

/**
 * 设置 Bottom CompoundDrawables ( 按照原有比例大小显示图片 )
 * @param textView [TextView]
 * @param bottom   bottom Drawable
 * @param <T>      泛型
 * @return [View]
</T> */
fun TextView?.setCompoundDrawablesWithIntrinsicBoundsByBottom(
    bottom: Drawable?
) {
    this?.setCompoundDrawablesWithIntrinsicBounds(null, null, null, bottom)
}

fun TextView?.setCompoundDrawablesWithIntrinsicBounds(
    left: Drawable?,
    top: Drawable?,
    right: Drawable?,
    bottom: Drawable?
) {
    this?.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
}