package com.spielberg.commonext

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.core.content.ContextCompat
import java.io.IOException
import java.io.InputStream
import java.io.UnsupportedEncodingException


/**
 * Return the drawable by identifier.
 *
 * @param id The identifier.
 * @return the drawable by identifier
 */
fun getDrawable(@DrawableRes id: Int): Drawable? {
    val context = getApplicationByReflect() ?: return null
    return ContextCompat.getDrawable(context.baseContext, id)
}

/**
 * Return the id identifier by name.
 *
 * @param name The name of id.
 * @return the id identifier by name
 */
fun getIdByName(name: String?): Int {
    val context = getApplicationByReflect() ?: return 0
    return context.resources.getIdentifier(name, "id", context.packageName)
}

/**
 * Return the string identifier by name.
 *
 * @param name The name of string.
 * @return the string identifier by name
 */
fun getStringIdByName(name: String?): Int {
    val context = getApplicationByReflect() ?: return 0
    return context.resources.getIdentifier(name, "string", context.packageName)
}

/**
 * Return the color identifier by name.
 *
 * @param name The name of color.
 * @return the color identifier by name
 */
fun getColorIdByName(name: String?): Int {
    val context = getApplicationByReflect() ?: return 0
    return context.resources.getIdentifier(name, "color", context.packageName)
}

/**
 * Return the dimen identifier by name.
 *
 * @param name The name of dimen.
 * @return the dimen identifier by name
 */
fun getDimenIdByName(name: String?): Int {
    val context = getApplicationByReflect() ?: return 0
    return context.resources.getIdentifier(name, "dimen", context.packageName)
}

/**
 * Return the drawable identifier by name.
 *
 * @param name The name of drawable.
 * @return the drawable identifier by name
 */
fun getDrawableIdByName(name: String?): Int {
    val context = getApplicationByReflect() ?: return 0
    return context.resources.getIdentifier(name, "drawable", context.packageName)
}

/**
 * Return the mipmap identifier by name.
 *
 * @param name The name of mipmap.
 * @return the mipmap identifier by name
 */
fun getMipmapIdByName(name: String?): Int {
    val context = getApplicationByReflect() ?: return 0
    return context.resources.getIdentifier(name, "mipmap", context.packageName)
}

/**
 * Return the layout identifier by name.
 *
 * @param name The name of layout.
 * @return the layout identifier by name
 */
fun getLayoutIdByName(name: String?): Int {
    val context = getApplicationByReflect() ?: return 0
    return context.resources.getIdentifier(name, "layout", context.packageName)
}

/**
 * Return the style identifier by name.
 *
 * @param name The name of style.
 * @return the style identifier by name
 */
fun getStyleIdByName(name: String?): Int {
    val context = getApplicationByReflect() ?: return 0
    return context.resources.getIdentifier(name, "style", context.packageName)
}

/**
 * Return the anim identifier by name.
 *
 * @param name The name of anim.
 * @return the anim identifier by name
 */
fun getAnimIdByName(name: String?): Int {
    val context = getApplicationByReflect() ?: return 0
    return context.resources.getIdentifier(name, "anim", context.packageName)
}

/**
 * Return the menu identifier by name.
 *
 * @param name The name of menu.
 * @return the menu identifier by name
 */
fun getMenuIdByName(name: String?): Int {
    val context = getApplicationByReflect() ?: return 0
    return context.resources.getIdentifier(name, "menu", context.packageName)
}

/**
 * Copy the file from assets.
 *
 * @param assetsFilePath The path of file in assets.
 * @param destFilePath   The path of destination file.
 * @return `true`: success<br></br>`false`: fail
 */
fun copyFileFromAssets(assetsFilePath: String, destFilePath: String): Boolean {
    var res = true
    try {
        val assets: Array<String>? = getApplicationByReflect()?.assets?.list(assetsFilePath)
        if (assets != null && assets.isNotEmpty()) {
            for (asset in assets) {
                res = res and copyFileFromAssets("$assetsFilePath/$asset", "$destFilePath/$asset")
            }
        } else {
            res = UtilsBridge.writeFileFromIS(
                destFilePath,
                getApplicationByReflect()?.assets?.open(assetsFilePath)
            )
        }
    } catch (e: IOException) {
        e.printStackTrace()
        res = false
    }
    return res
}

/**
 * Return the content of assets.
 *
 * @param assetsFilePath The path of file in assets.
 * @return the content of assets
 */
fun readAssets2String(assetsFilePath: String?): String? {
    return readAssets2String(assetsFilePath, null)
}

/**
 * Return the content of assets.
 *
 * @param assetsFilePath The path of file in assets.
 * @param charsetName    The name of charset.
 * @return the content of assets
 */
fun readAssets2String(assetsFilePath: String?, charsetName: String?): String? {
    if (assetsFilePath.isEmptyOrBlankExt()) return ""
    return try {
        val inputStream: InputStream? = getApplicationByReflect()?.assets?.open(assetsFilePath!!)
        val bytes: ByteArray = UtilsBridge.inputStream2Bytes(inputStream) ?: return ""
        if (charsetName.isEmptyOrBlankExt()) {
            String(bytes)
        } else {
            try {
                String(bytes, charsetName)
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                ""
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        ""
    }
}

/**
 * Return the content of file in assets.
 *
 * @param assetsPath The path of file in assets.
 * @return the content of file in assets
 */
fun readAssets2List(assetsPath: String?): List<String?>? {
    return readAssets2List(assetsPath, "")
}

/**
 * Return the content of file in assets.
 *
 * @param assetsPath  The path of file in assets.
 * @param charsetName The name of charset.
 * @return the content of file in assets
 */
fun readAssets2List(
    assetsPath: String?,
    charsetName: String?
): List<String?>? {
    if (assetsPath.isEmptyOrBlankExt())return emptyList()
    return try {
        UtilsBridge.inputStream2Lines(
            getApplicationByReflect()?.resources?.assets?.open(assetsPath!!),
            charsetName
        )
    } catch (e: IOException) {
        e.printStackTrace()
        emptyList<String>()
    }
}


/**
 * Copy the file from raw.
 *
 * @param resId        The resource id.
 * @param destFilePath The path of destination file.
 * @return `true`: success<br></br>`false`: fail
 */
fun copyFileFromRaw(@RawRes resId: Int, destFilePath: String?): Boolean {
    return UtilsBridge.writeFileFromIS(
        destFilePath,
        getApplicationByReflect()?.resources?.openRawResource(resId)
    )
}

/**
 * Return the content of resource in raw.
 *
 * @param resId The resource id.
 * @return the content of resource in raw
 */
fun readRaw2String(@RawRes resId: Int): String? {
    return readRaw2String(resId, null)
}

/**
 * Return the content of resource in raw.
 *
 * @param resId       The resource id.
 * @param charsetName The name of charset.
 * @return the content of resource in raw
 */
fun readRaw2String(@RawRes resId: Int, charsetName: String?): String? {
    val inputStream: InputStream? = getApplicationByReflect()?.resources?.openRawResource(resId)
    val bytes: ByteArray = inputStream?.inputStream2BytesExt() ?: return null
    return if (charsetName.isEmptyOrBlankExt()) {
        String(bytes)
    } else {
        try {
            String(bytes, charsetName.toCharset())
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            ""
        }
    }
}

/**
 * Return the content of resource in raw.
 *
 * @param resId The resource id.
 * @return the content of file in assets
 */
fun readRaw2List(@RawRes resId: Int): List<String?>? {
    return readRaw2List(resId, "")
}

/**
 * Return the content of resource in raw.
 *
 * @param resId       The resource id.
 * @param charsetName The name of charset.
 * @return the content of file in assets
 */
fun readRaw2List(
    @RawRes resId: Int,
    charsetName: String?
): List<String?>? {
    return UtilsBridge.inputStream2Lines(
        getApplicationByReflect()?.resources?.openRawResource(resId),
        charsetName
    )
}