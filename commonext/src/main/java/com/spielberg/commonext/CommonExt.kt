package com.spielberg.commonext

import android.content.ClipData
import android.content.Context
import android.text.Html
import android.text.Spanned
import android.view.View
import java.io.*


/**
 * 判断是否为空 并传入相关操作
 */
inline fun <reified T> T?.notNull(notNullAction: (T) -> Unit, nullAction: () -> Unit = {}) {
    if (this != null) {
        notNullAction.invoke(this)
    } else {
        nullAction.invoke()
    }
}


/**
 * 复制文本到粘贴板
 */
fun Context.copyToClipboard(text: String, label: String = "JetpackMvvm") {
    val clipData = ClipData.newPlainText(label, text)
    clipboardManager?.setPrimaryClip(clipData)
}


/**
 * 设置点击事件
 * @param views 需要设置点击事件的view
 * @param onClick 点击触发的方法
 */
fun setOnclick(vararg views: View?, onClick: (View) -> Unit) {
    views.forEach {
        it?.setOnClickListener { view ->
            onClick.invoke(view)
        }
    }
}

/**
 * 设置防止重复点击事件
 * @param views 需要设置点击事件的view集合
 * @param interval 时间间隔 默认0.5秒
 * @param onClick 点击触发的方法
 */
fun setOnclickNoRepeat(vararg views: View?, interval: Long = 500, onClick: (View) -> Unit) {
    views.forEach {
        it?.clickNoRepeat(interval = interval) { view ->
            onClick.invoke(view)
        }
    }
}

fun String.toHtml(flag: Int = Html.FROM_HTML_MODE_LEGACY): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, flag)
    } else {
        Html.fromHtml(this)
    }
}

/**
 * Save the response as a binary file i.e html file.
 *
 * @param context  the context
 * @param filename the filename
 * @param response the response
 * @throws IOException the io exception
 */
@Throws(IOException::class)
fun saveBinaryFileFromResponse(context: Context, filename: String?, response: Response) {
    val downloadedFile = File(context.cacheDir, filename)
    val sink: BufferedSink = Okio.buffer(Okio.sink(downloadedFile))
    sink.writeAll(response.body().source())
    sink.close()
}

fun <T> MutableList<T>.onDuplication(data: List<T>?) {
    data?.forEach { new ->
        val has = this.find { old -> new?.equals(old) ?: false }
        if (has == null) {
            this.add(new)
        } else {
            val index = this.indexOf(has)
            if (index >= this.size) {
                return
            }
            this[index] = new
        }
    }
}

/**
 * 获取异常栈信息
 * @param throwable 异常
 * @param errorInfo 获取失败返回字符串
 * @return 异常栈信息字符串
 */
fun getThrowableStackTrace(
    throwable: Throwable?,
    errorInfo: String?
): String? {
    if (throwable != null) {
        var printWriter: PrintWriter? = null
        return try {
            val writer: Writer = StringWriter()
            printWriter = PrintWriter(writer)
            throwable.printStackTrace(printWriter)
            //                // 获取错误栈信息
            //                StackTraceElement[] stElement = throwable.getStackTrace();
            //                // 标题, 提示属于什么异常
            //                printWriter.append(throwable.toString());
            //                printWriter.append(DevFinal.NEW_LINE_STR);
            //                // 遍历错误栈信息, 并且进行换行缩进
            //                for (StackTraceElement element : stElement) {
            //                    printWriter.append("\tat ");
            //                    printWriter.append(element.toString());
            //                    printWriter.append(DevFinal.NEW_LINE_STR);
            //                }
            writer.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            e.toString()
        } finally {
            printWriter?.close()
        }
    }
    return errorInfo
}

/**
 * 去掉结尾多余的 . 与 0
 * @param value 待处理数值
 * @return 处理后的数值字符串
 */
fun subZeroAndDot(value: String): String {
    if (value.isNotBlank() && value.isNotEmpty()) {
        var str = value
        if (str.indexOf(".") >= 0) {
            // 去掉多余的 0
            str = str.replace("0+?$".toRegex(), "")
            // 最后一位是 . 则去掉
            str = str.replace("[.]$".toRegex(), "")
        }
        return str
    }
    return value
}

/**
 * 计算百分比值 ( 最大 100%)
 * @param value 指定值
 * @param max   最大值
 * @return 百分比值
 */
fun percentI(
    value: Int,
    max: Int
): Int {
    if (max <= 0) return 0
    if (value <= 0) return 0
    return if (value >= max) 1 else value / max
}

/**
 * 计算百分比值 ( 最大 100%)
 * @param value 指定值
 * @param max   最大值
 * @return 百分比值
 */
fun percentI(
    value: Float,
    max: Float
): Int {
    if (max <= 0) return 0
    if (value <= 0) return 0
    return if (value >= max) 1 else (value / max).toInt()
}


/**
 * 计算百分比值 ( 最大 100%)
 * @param value 指定值
 * @param max   最大值
 * @return 百分比值
 */
fun percentI(
    value: Long,
    max: Long
): Int {
    if (max <= 0) return 0
    if (value <= 0) return 0
    return if (value >= max) 1 else (value / max).toInt()
}

/**
 * 计算百分比值 ( 最大 100%)
 * @param value 指定值
 * @param max   最大值
 * @return 百分比值
 */
fun percentD(
    value: Int,
    max: Int
): Double {
    if (max <= 0) return 0.0
    if (value <= 0) return 0.0
    return if (value >= max) 1.0 else value.toDouble() / max.toDouble()
}

/**
 * 计算百分比值 ( 最大 100%)
 * @param value 指定值
 * @param max   最大值
 * @return 百分比值
 */
fun percentD(
    value: Float,
    max: Float
): Double {
    if (max <= 0) return 0.0
    if (value <= 0) return 0.0
    return if (value >= max) 1.0 else (value / max).toDouble()
}

/**
 * 计算百分比值 ( 最大 100%)
 * @param value 指定值
 * @param max   最大值
 * @return 百分比值
 */
fun percentD(
    value: Long,
    max: Long
): Double {
    if (max <= 0) return 0.0
    if (value <= 0) return 0.0
    return if (value >= max) 1.0 else (value / max).toDouble()
}

/**
 * 计算百分比值 ( 最大 100%)
 * @param value 指定值
 * @param max   最大值
 * @return 百分比值
 */
fun percentL(
    value: Int,
    max: Int
): Long {
    if (max <= 0) return 0L
    if (value <= 0) return 0L
    return if (value >= max) 1L else value.toLong() / max.toLong()
}

/**
 * 计算百分比值 ( 最大 100%)
 * @param value 指定值
 * @param max   最大值
 * @return 百分比值
 */
fun percentL(
    value: Float,
    max: Float
): Long {
    if (max <= 0) return 0L
    if (value <= 0) return 0L
    return if (value >= max) 1L else value.toLong() / max.toLong()
}

/**
 * 计算百分比值 ( 最大 100%)
 * @param value 指定值
 * @param max   最大值
 * @return 百分比值
 */
fun percentL(
    value: Long,
    max: Long
): Long {
    if (max <= 0) return 0L
    if (value <= 0) return 0L
    return if (value >= max) 1L else value / max
}

/**
 * 计算百分比值 ( 最大 100%)
 * @param value 指定值
 * @param max   最大值
 * @return 百分比值
 */
fun percentL(
    value: Double,
    max: Double
): Long {
    if (max <= 0) return 0L
    if (value <= 0) return 0L
    return if (value >= max) 1L else (value / max).toLong()
}

/**
 * 计算百分比值 ( 最大 100%)
 * @param value 指定值
 * @param max   最大值
 * @return 百分比值
 */
fun percentF(
    value: Int,
    max: Int
): Float {
    if (max <= 0) return 0.0f
    if (value <= 0) return 0.0f
    return if (value >= max) 1.0f else value.toFloat() / max.toFloat()
}

/**
 * 计算百分比值 ( 最大 100%)
 * @param value 指定值
 * @param max   最大值
 * @return 百分比值
 */
fun percentF(
    value: Float,
    max: Float
): Float {
    if (max <= 0) return 0.0f
    if (value <= 0) return 0.0f
    return if (value >= max) 1.0f else value / max
}

/**
 * 计算百分比值 ( 最大 100%)
 * @param value 指定值
 * @param max   最大值
 * @return 百分比值
 */
fun percentF(
    value: Long,
    max: Long
): Float {
    if (max <= 0) return 0.0f
    if (value <= 0) return 0.0f
    return if (value >= max) 1.0f else (value / max).toFloat()
}
