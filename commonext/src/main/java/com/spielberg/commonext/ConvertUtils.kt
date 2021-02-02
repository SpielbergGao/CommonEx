package com.spielberg.commonext


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
