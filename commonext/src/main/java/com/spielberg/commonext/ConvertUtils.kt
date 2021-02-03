package com.spielberg.commonext

import android.annotation.SuppressLint
import com.spielberg.commonext.constant.MemoryConstants


private val HEX_DIGITS_UPPER =
    charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
private val HEX_DIGITS_LOWER =
    charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')


/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 2/2/21 9:50 PM
 *  @describe Bytes to hex string.
 *  e.g. bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns "00A8"
 */
fun ByteArray?.bytes2HexStringExt(): String? {
    return this.bytes2HexStringExt(true)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 2/2/21 9:50 PM
 *  @describe Bytes to hex string.
 *  e.g. bytes2HexString(new byte[] { 0, (byte) 0xa8 }, true) returns "00A8"
 */
fun ByteArray?.bytes2HexStringExt(isUpperCase: Boolean): String? {
    if (this == null) return ""
    val hexDigits: CharArray =
        if (isUpperCase) HEX_DIGITS_UPPER else HEX_DIGITS_LOWER
    val len = this.size
    if (len <= 0) return ""
    val ret = CharArray(len shl 1)
    var i = 0
    var j = 0
    while (i < len) {
        ret[j++] = hexDigits[this[i].toInt() shr 4 and 0x0f]
        ret[j++] = hexDigits[(this[i].toInt() and 0x0f)]
        i++
    }
    return String(ret)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Size of byte to fit size of memory.
 */
fun byte2FitMemorySize(byteSize: Long): String? {
    return byte2FitMemorySize(byteSize, 3)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 2/3/21 9:14 PM
 *  @describe Size of byte to fit size of memory.
 *  @param byteSize  Size of byte.
 *  @param precision The precision
 */
@SuppressLint("DefaultLocale")
fun byte2FitMemorySize(byteSize: Long, precision: Int): String? {
    require(precision >= 0) { "precision shouldn't be less than zero!" }
    return when {
        byteSize < 0 -> {
            throw IllegalArgumentException("byteSize shouldn't be less than zero!")
        }
        byteSize < MemoryConstants.KB -> {
            String.format("%." + precision + "fB", byteSize.toDouble())
        }
        byteSize < MemoryConstants.MB -> {
            java.lang.String.format(
                "%." + precision + "fKB",
                byteSize.toDouble() / MemoryConstants.KB
            )
        }
        byteSize < MemoryConstants.GB -> {
            java.lang.String.format(
                "%." + precision + "fMB",
                byteSize.toDouble() / MemoryConstants.MB
            )
        }
        else -> {
            java.lang.String.format(
                "%." + precision + "fGB",
                byteSize.toDouble() / MemoryConstants.GB
            )
        }
    }
}
