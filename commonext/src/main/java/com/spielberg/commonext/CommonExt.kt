package com.spielberg.commonext

import android.content.ClipData
import android.content.Context
import android.text.Html
import android.text.Spanned
import android.view.View
import java.io.File
import java.io.IOException


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