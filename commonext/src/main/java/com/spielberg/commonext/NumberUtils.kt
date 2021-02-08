package com.spielberg.commonext

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

private val DF_THREAD_LOCAL: ThreadLocal<DecimalFormat> = object : ThreadLocal<DecimalFormat>() {
    override fun initialValue(): DecimalFormat {
        return NumberFormat.getInstance() as DecimalFormat
    }
}

fun getSafeDecimalFormat(): DecimalFormat {
    return DF_THREAD_LOCAL.get()!!
}

/**
 * Format the value.
 *
 * @param value          The value.
 * @param fractionDigits The number of digits allowed in the fraction portion of value.
 * @return the format value
 */
fun format(value: Float, fractionDigits: Int): String? {
    return format(value, false, 1, fractionDigits, true)
}

/**
 * Format the value.
 *
 * @param value          The value.
 * @param fractionDigits The number of digits allowed in the fraction portion of value.
 * @param isHalfUp       True to rounded towards the nearest neighbor.
 * @return the format value
 */
fun format(value: Float, fractionDigits: Int, isHalfUp: Boolean): String? {
    return format(value, false, 1, fractionDigits, isHalfUp)
}

/**
 * Format the value.
 *
 * @param value            The value.
 * @param minIntegerDigits The minimum number of digits allowed in the integer portion of value.
 * @param fractionDigits   The number of digits allowed in the fraction portion of value.
 * @param isHalfUp         True to rounded towards the nearest neighbor.
 * @return the format value
 */
fun format(value: Float, minIntegerDigits: Int, fractionDigits: Int, isHalfUp: Boolean): String? {
    return format(value, false, minIntegerDigits, fractionDigits, isHalfUp)
}

/**
 * Format the value.
 *
 * @param value          The value.
 * @param isGrouping     True to set grouping will be used in this format, false otherwise.
 * @param fractionDigits The number of digits allowed in the fraction portion of value.
 * @return the format value
 */
fun format(value: Float, isGrouping: Boolean, fractionDigits: Int): String? {
    return format(value, isGrouping, 1, fractionDigits, true)
}

/**
 * Format the value.
 *
 * @param value          The value.
 * @param isGrouping     True to set grouping will be used in this format, false otherwise.
 * @param fractionDigits The number of digits allowed in the fraction portion of value.
 * @param isHalfUp       True to rounded towards the nearest neighbor.
 * @return the format value
 */
fun format(value: Float, isGrouping: Boolean, fractionDigits: Int, isHalfUp: Boolean): String? {
    return format(value, isGrouping, 1, fractionDigits, isHalfUp)
}

/**
 * Format the value.
 *
 * @param value            The value.
 * @param isGrouping       True to set grouping will be used in this format, false otherwise.
 * @param minIntegerDigits The minimum number of digits allowed in the integer portion of value.
 * @param fractionDigits   The number of digits allowed in the fraction portion of value.
 * @param isHalfUp         True to rounded towards the nearest neighbor.
 * @return the format value
 */
fun format(
    value: Float,
    isGrouping: Boolean,
    minIntegerDigits: Int,
    fractionDigits: Int,
    isHalfUp: Boolean
): String? {
    return format(float2Double(value), isGrouping, minIntegerDigits, fractionDigits, isHalfUp)
}

/**
 * Format the value.
 *
 * @param value          The value.
 * @param fractionDigits The number of digits allowed in the fraction portion of value.
 * @return the format value
 */
fun format(value: Double, fractionDigits: Int): String? {
    return format(value, false, 1, fractionDigits, true)
}

/**
 * Format the value.
 *
 * @param value          The value.
 * @param fractionDigits The number of digits allowed in the fraction portion of value.
 * @param isHalfUp       True to rounded towards the nearest neighbor.
 * @return the format value
 */
fun format(value: Double, fractionDigits: Int, isHalfUp: Boolean): String? {
    return format(value, false, 1, fractionDigits, isHalfUp)
}

/**
 * Format the value.
 *
 * @param value            The value.
 * @param minIntegerDigits The minimum number of digits allowed in the integer portion of value.
 * @param fractionDigits   The number of digits allowed in the fraction portion of value.
 * @param isHalfUp         True to rounded towards the nearest neighbor.
 * @return the format value
 */
fun format(value: Double, minIntegerDigits: Int, fractionDigits: Int, isHalfUp: Boolean): String? {
    return format(value, false, minIntegerDigits, fractionDigits, isHalfUp)
}

/**
 * Format the value.
 *
 * @param value          The value.
 * @param isGrouping     True to set grouping will be used in this format, false otherwise.
 * @param fractionDigits The number of digits allowed in the fraction portion of value.
 * @return the format value
 */
fun format(value: Double, isGrouping: Boolean, fractionDigits: Int): String? {
    return format(value, isGrouping, 1, fractionDigits, true)
}

/**
 * Format the value.
 *
 * @param value          The value.
 * @param isGrouping     True to set grouping will be used in this format, false otherwise.
 * @param fractionDigits The number of digits allowed in the fraction portion of value.
 * @param isHalfUp       True to rounded towards the nearest neighbor.
 * @return the format value
 */
fun format(value: Double, isGrouping: Boolean, fractionDigits: Int, isHalfUp: Boolean): String? {
    return format(value, isGrouping, 1, fractionDigits, isHalfUp)
}

/**
 * Format the value.
 *
 * @param value            The value.
 * @param isGrouping       True to set grouping will be used in this format, false otherwise.
 * @param minIntegerDigits The minimum number of digits allowed in the integer portion of value.
 * @param fractionDigits   The number of digits allowed in the fraction portion of value.
 * @param isHalfUp         True to rounded towards the nearest neighbor.
 * @return the format value
 */
fun format(
    value: Double,
    isGrouping: Boolean,
    minIntegerDigits: Int,
    fractionDigits: Int,
    isHalfUp: Boolean
): String? {
    val nf = getSafeDecimalFormat()
    nf.isGroupingUsed = isGrouping
    nf.roundingMode = if (isHalfUp) RoundingMode.HALF_UP else RoundingMode.DOWN
    nf.minimumIntegerDigits = minIntegerDigits
    nf.minimumFractionDigits = fractionDigits
    nf.maximumFractionDigits = fractionDigits
    return nf.format(value)
}

/**
 * Float to double.
 *
 * @param value The value.
 * @return the number of double
 */
fun float2Double(value: Float): Double {
    return BigDecimal(value.toString()).toDouble()
}