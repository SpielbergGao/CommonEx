package com.spielberg.commonext

import android.os.Build
import android.text.Html
import android.util.Base64
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the urlencoded string.
 */
fun String?.urlEncodeExt(): String? {
    return this.urlEncodeExt("UTF-8")
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the urlencoded string.
 */
fun String?.urlEncodeExt(charsetName: String?): String? {
    return if (this.isEmptyOrBlankExt()) "" else try {
        URLEncoder.encode(this, charsetName)
    } catch (e: UnsupportedEncodingException) {
        throw AssertionError(e)
    }
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the string of decode urlencoded string.
 */
fun String?.urlDecodeExt(): String? {
    return this.urlDecodeExt("UTF-8")
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the string of decode urlencoded string.
 */
fun String?.urlDecodeExt(charsetName: String?): String? {
    return if (this.isEmptyOrBlankExt()) "" else try {
        val safeInput =
            this!!.replace("%(?![0-9a-fA-F]{2})".toRegex(), "%25").replace("\\+".toRegex(), "%2B")
        URLDecoder.decode(safeInput, charsetName)
    } catch (e: UnsupportedEncodingException) {
        throw AssertionError(e)
    }
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return Base64-encode bytes.
 */
fun String.base64EncodeExt(): ByteArray? {
    return this.toByteArray().base64EncodeExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return Base64-encode bytes.
 */
fun ByteArray?.base64EncodeExt(): ByteArray? {
    return if (this == null || this.isEmpty()) ByteArray(0) else Base64.encode(
        this,
        Base64.NO_WRAP
    )
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return Base64-encode string.
 */
fun ByteArray?.base64Encode2StringExt(): String? {
    return if (this == null || this.isEmpty()) "" else Base64.encodeToString(
        this,
        Base64.NO_WRAP
    )
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the bytes of decode Base64-encode string.
 */
fun String?.base64DecodeExt(): ByteArray? {
    return if (this == null || this.isEmpty()) ByteArray(0) else Base64.decode(
        this,
        Base64.NO_WRAP
    )
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the bytes of decode Base64-encode bytes.
 */
fun ByteArray?.base64DecodeExt(): ByteArray? {
    return if (this == null || this.isEmpty()) ByteArray(0) else Base64.decode(
        this,
        Base64.NO_WRAP
    )
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return html-encode string.
 */
fun CharSequence?.htmlEncodeExt(): String? {
    if (this == null || this.isEmpty()) return ""
    val sb = StringBuffer()
    var c: Char
    var i = 0
    val len = this.length
    while (i < len) {
        c = this[i]
        when (c) {
            '<' -> sb.append("&lt;") //$NON-NLS-1$
            '>' -> sb.append("&gt;") //$NON-NLS-1$
            '&' -> sb.append("&amp;") //$NON-NLS-1$
            '\'' ->                     //http://www.w3.org/TR/xhtml1
                // The named character reference &apos; (the apostrophe, U+0027) was
                // introduced in XML 1.0 but does not appear in HTML. Authors should
                // therefore use &#39; instead of &apos; to work as expected in HTML 4
                // user agents.
                sb.append("&#39;") //$NON-NLS-1$
            '"' -> sb.append("&quot;") //$NON-NLS-1$
            else -> sb.append(c)
        }
        i++
    }
    return sb.toString()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the string of decode html-encode string.
 */
fun String?.htmlDecodeExt(): CharSequence? {
    if (this.isEmptyOrBlankExt()) return ""
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the binary encoded string padded with one space
 */
fun String?.binaryEncodeExt(): String? {
    if (this.isEmptyOrBlankExt()) return ""
    val sb = StringBuffer()
    for (i in this!!.toCharArray()) {
        sb.append(Integer.toBinaryString(i.toInt())).append(" ")
    }
    return sb.deleteCharAt(sb.length - 1).toString()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe  Return UTF-8 String from binary
 */
fun String?.binaryDecodeExt(): String? {
    if (this.isEmptyOrBlankExt()) return ""
    val splits = this!!.split(" ".toRegex()).toTypedArray()
    val sb = StringBuffer()
    for (split in splits) {
        sb.append(split.toInt(2).toChar())
    }
    return sb.toString()
}