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
 * 设置字体
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
 * @param <T>      泛型
 * @return 字体大小 (px)
</T> */
fun TextView?.getTextSize(): Float {
    return this?.textSize ?: -1f
}

/**
 * 清空 flags
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.clearFlags() {
    this?.paintFlags = 0
}

/**
 * 设置 TextView 抗锯齿 flags
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setAntiAliasFlag() {
    this?.paintFlags = Paint.ANTI_ALIAS_FLAG
}

/**
 * 设置下划线并加清晰
 * @param isAntiAlias 是否消除锯齿
 * @param <T>         泛型
 * @return [TextView]
</T> */
fun TextView?.setUnderlineText(
    isAntiAlias: Boolean = true
) {
    this?.paintFlags = this?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG) ?: 0
    if (isAntiAlias) {
        this?.paintFlags = this?.paintFlags?.or(Paint.ANTI_ALIAS_FLAG) ?: 0
    }
}

/**
 * 设置中划线并加清晰
 * @param isAntiAlias 是否消除锯齿
 * @param <T>         泛型
 * @return [TextView]
</T> */
fun TextView?.setStrikeThruText(
    isAntiAlias: Boolean = true
) {
    this?.paintFlags = this?.paintFlags?.or(Paint.STRIKE_THRU_TEXT_FLAG) ?: 0
    if (isAntiAlias) {
        this?.paintFlags = this?.paintFlags?.or(Paint.ANTI_ALIAS_FLAG) ?: 0
    }
}

// =
/**
 * 获取文字水平间距
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
 * @param letterSpacing 文字水平间距
 * @param <T>           泛型
 * @return [TextView]
</T> */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
fun TextView?.setLetterSpacing(
    letterSpacing: Float
) {
    this?.letterSpacing = letterSpacing
}


// =
/**
 * 获取文字行间距 ( 行高 )
 * @param <T>      泛型
 * @return 文字行间距 ( 行高 )
</T> */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
fun TextView?.getLineSpacingExtra(): Float {
    return this?.lineSpacingExtra ?: 0f
}

/**
 * 获取文字行间距倍数
 * @param <T>      泛型
 * @return 文字行间距倍数
</T> */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
fun TextView?.getLineSpacingMultiplier(): Float {
    return this?.lineSpacingMultiplier ?: 0f
}

/**
 * 设置文字行间距 ( 行高 )
 * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
 * @param <T>         泛型
 * @return [TextView]
</T> */
fun TextView?.setLineSpacing(
    lineSpacing: Float
) {
    this?.setLineSpacingAndMultiplier(lineSpacing, 1.0f)
}

/**
 * 设置文字行间距 ( 行高 )、行间距倍数
 * @param lineSpacing 文字行间距 ( 行高 ), android:lineSpacingExtra
 * @param multiplier  行间距倍数, android:lineSpacingMultiplier
 * @param <T>         泛型
 * @return [TextView]
</T> */
fun TextView?.setLineSpacingAndMultiplier(
    lineSpacing: Float,
    multiplier: Float
) {
    this?.setLineSpacing(lineSpacing, multiplier)
}

/**
 * 获取字体水平方向的缩放
 * @param <T>      泛型
 * @return 字体水平方向的缩放
</T> */
fun TextView?.getTextScaleX(): Float {
    return this?.textScaleX ?: 0f
}

/**
 * 设置字体水平方向的缩放
 * <pre>
 * android:textScaleX
</pre> *
 * @param size     缩放比例
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setTextScaleX(
    size: Float
) {
    this?.textScaleX = size
}

/**
 * 是否保留字体留白间隙区域
 * @param <T>      泛型
 * @return `true` yes, `false` no
</T> */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
fun TextView?.getIncludeFontPadding(): Boolean {
    return this?.includeFontPadding ?: false
}

/**
 * 设置是否保留字体留白间隙区域
 * <pre>
 * android:includeFontPadding
</pre> *
 * @param includePadding 是否保留字体留白间隙区域
 * @param <T>            泛型
 * @return [TextView]
</T> */
fun TextView?.setIncludeFontPadding(
    includePadding: Boolean
) {
    this?.includeFontPadding = includePadding
}

/**
 * 获取输入类型
 * @param <T>      泛型
 * @return 输入类型
</T> */
fun TextView?.getInputType(): Int {
    return this?.inputType ?: 0
}

/**
 * 设置输入类型
 * @param type     类型
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setInputType(
    type: Int
) {
    this?.inputType = type
}

/**
 * 获取软键盘右下角按钮类型
 * @param <T>      泛型
 * @return 软键盘右下角按钮类型
</T> */
fun TextView?.getImeOptions(): Int {
    return this?.imeOptions ?: 0
}

/**
 * 设置软键盘右下角按钮类型
 * @param imeOptions 软键盘按钮类型
 * @param <T>        泛型
 * @return [TextView]
</T> */
fun TextView?.setImeOptions(
    imeOptions: Int
) {
    this?.imeOptions = imeOptions
}

/**
 * 设置行数
 * @param lines    行数
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setLines(
    lines: Int
) {
    this?.setLines(lines)
}

/**
 * 获取最大行数
 * @param <T>      泛型
 * @return 最大行数
</T> */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
fun TextView?.getMaxLines(): Int {
    return this?.maxLines ?: 0
}

/**
 * 设置最大行数
 * @param maxLines 最大行数
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setMaxLines(
    maxLines: Int
) {
    this?.maxLines = maxLines
}

/**
 * 获取最小行数
 * @param <T>      泛型
 * @return 最小行数
</T> */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
fun TextView?.getMinLines(): Int {
    return this?.minLines ?: 0
}

/**
 * 设置最小行数
 * @param minLines 最小行数
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setMinLines(
    minLines: Int
) {
    this?.minLines = minLines
}

/**
 * 获取最大字符宽度限制
 * @param <T>      泛型
 * @return 最大字符宽度限制
</T> */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
fun TextView?.getMaxEms(): Int {
    return this?.maxEms ?: 0
}

/**
 * 设置最大字符宽度限制
 * @param maxEms   最大字符
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setMaxEms(
    maxEms: Int
) {
    this?.maxEms = maxEms
}

/**
 * 获取最小字符宽度限制
 * @param <T>      泛型
 * @return 最小字符宽度限制
</T> */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
fun TextView?.getMinEms(): Int {
    return this?.minEms ?: 0
}

/**
 * 设置最小字符宽度限制
 * @param minEms   最小字符
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setMinEms(
    minEms: Int
) {
    this?.minEms = minEms
}

/**
 * 设置指定字符宽度
 * @param ems      字符
 * @param <T>      泛型
 * @return [TextView]
</T> */
fun TextView?.setEms(
    ems: Int
) {
    this?.setEms(ems)
}

/**
 * 设置长度限制
 * @param maxLength 长度限制
 * @param <T>       泛型
 * @return [TextView]
</T> */
fun TextView?.setMaxLength(
    maxLength: Int
){
    // 设置最大长度限制
    val filters = arrayOf<InputFilter>(LengthFilter(maxLength))
    this?.filters = filters
}

/**
 * 设置 Ellipsize 效果
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
 * @param <T>      泛型
 * @return 自动识别文本类型
</T> */
fun TextView?.getAutoLinkMask(): Int {
    return this?.autoLinkMask ?: 0
}

/**
 * 设置自动识别文本链接
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
 * @param <T>      泛型
 * @return [TransformationMethod]
</T> */
fun TextView?.getTransformationMethod(): TransformationMethod? {
    return this?.transformationMethod
}

/**
 * 设置文本视图显示转换
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
 * @param <T>      泛型
 * @return [Paint]
</T> */
fun TextView?.getPaint(): Paint? {
    return this?.paint
}

/**
 * 获取字体高度
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
 * @param <T>      泛型
 * @return 字体顶部偏移高度
</T> */
fun TextView?.getTextTopOffsetHeight(): Int {
    return getTextTopOffsetHeight(this.getPaint())
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
 * @param <T>      泛型
 * @return 字体宽度
</T> */
fun TextView?.getTextWidth(): Float {
    return getTextWidth(this.getPaint(), this?.text?.toString())
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
        calcTextHeight = Math.ceil((fontMetrics.descent - fontMetrics.ascent).toDouble()).toInt()
        // 符合条件则直接返回
        when {
            calcTextHeight == height -> {
                return textSize
            }
            calcTextHeight > height -> { // 如果计算的字体高度大于
                textSize -= 0.5f
                if (state == 2) {
                    if (calcTextHeight < height) {
                        return textSize
                    }
                }
                state = 1
            }
            else -> {
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
    content: String? = textView.toString()
): Float {
    return if (textView == null || content == null) 0f else reckonTextSizeByWidth(
        width, textView.paint,
        textView.textSize, content
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
 * @param text     待测量文本
 * @param width    指定的宽度
 * @param <T>      泛型
 * @return -1 表示没超过, 其他值表示对应的索引位置
</T> */
fun TextView?.calcTextWidth(
    text: String?,
    width: Float
): Int {
    return calcTextWidth(this.getPaint(), text, width)
}

/**
 * 计算第几位超过宽度
 * @param width    指定的宽度
 * @param <T>      泛型
 * @return -1 表示没超过, 其他值表示对应的索引位置
</T> */
fun TextView?.calcTextWidth(width: Float): Int {
    return calcTextWidth(this.getPaint(), this?.textString(), width)
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
 * @param text     待测量文本
 * @param width    指定的宽度
 * @param <T>      泛型
 * @return 行数
</T> */
fun TextView?.calcTextLine(
    text: String?,
    width: Float
): Int {
    return calcTextLine(this.getPaint(), text, width)
}

/**
 * 计算文本行数
 * @param width    指定的宽度
 * @param <T>      泛型
 * @return 行数
</T> */
fun TextView?.calcTextLine(
    width: Float
): Int {
    return calcTextLine(this.getPaint(), this?.textString(), width)
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