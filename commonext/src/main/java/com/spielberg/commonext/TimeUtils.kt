
package com.spielberg.commonext

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private val SDF_THREAD_LOCAL: ThreadLocal<MutableMap<String, SimpleDateFormat>> =
    object : ThreadLocal<MutableMap<String, SimpleDateFormat>>() {
        override fun initialValue(): MutableMap<String, SimpleDateFormat> {
            return HashMap()
        }
    }

private fun getDefaultFormat(): SimpleDateFormat {
    return getSafeDateFormat("yyyy-MM-dd HH:mm:ss")
}

@SuppressLint("SimpleDateFormat")
fun getSafeDateFormat(pattern: String): SimpleDateFormat {
    val sdfMap: MutableMap<String, SimpleDateFormat> = SDF_THREAD_LOCAL.get()!!
    var simpleDateFormat = sdfMap[pattern]
    if (simpleDateFormat == null) {
        simpleDateFormat = SimpleDateFormat(pattern)
        sdfMap[pattern] = simpleDateFormat
    }
    return simpleDateFormat
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe  Milliseconds to the formatted time string.
 */
fun millis2String(millis: Long): String? {
    return millis2String(millis, getDefaultFormat())
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Milliseconds to the formatted time string.
 */
fun millis2String(millis: Long, pattern: String): String? {
    return millis2String(millis, getSafeDateFormat(pattern))
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe  Milliseconds to the formatted time string.
 */
fun millis2String(millis: Long, format: DateFormat): String? {
    return format.format(Date(millis))
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Formatted time string to the milliseconds.
 */
fun string2Millis(time: String?): Long {
    return string2Millis(time, getDefaultFormat())
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Formatted time string to the milliseconds.
 */
fun string2Millis(time: String?, pattern: String): Long {
    return string2Millis(time, getSafeDateFormat(pattern))
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Formatted time string to the milliseconds.
 */
fun string2Millis(time: String?, format: DateFormat): Long {
    try {
        return format.parse(time).time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return -1
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Formatted time string to the date.
 */
fun string2Date(time: String?): Date? {
    return string2Date(time, getDefaultFormat())
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Formatted time string to the date.
 */
fun string2Date(time: String?, pattern: String): Date? {
    return string2Date(time, getSafeDateFormat(pattern))
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Formatted time string to the date.
 */
fun string2Date(time: String?, format: DateFormat): Date? {
    try {
        return format.parse(time)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return null
}