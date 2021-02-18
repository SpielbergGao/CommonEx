package com.spielberg.commonext

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.io.Serializable

/**
 * Return the bytes of hash encryption.
 *
 * @param data      The data.
 * @param algorithm The name of hash encryption.
 * @return the bytes of hash encryption
 */
fun hashTemplate(data: ByteArray?, algorithm: String?): ByteArray? {
    return if (data == null || data.isEmpty()) null else try {
        val md = MessageDigest.getInstance(algorithm)
        md.update(data)
        md.digest()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
        null
    }
}

fun getLaunchAppDetailsSettingsIntent(pkgName: String?, isNewTask: Boolean): Intent? {
    return getLaunchAppDetailsSettingsIntent(pkgName, isNewTask)
}

fun isIntentAvailable(intent: Intent?): Boolean {
    return isIntentAvailable(intent)
}

fun getLaunchAppIntent(pkgName: String?): Intent? {
    return getLaunchAppIntent(pkgName)
}

fun getForegroundProcessName(): String? {
    return getForegroundProcessName()
}

fun Array<out Pair<String, Any?>>.toBundle(): Bundle? {
    return Bundle().apply {
        forEach { it ->
            when (val value = it.second) {
                null -> putSerializable(it.first, null as Serializable?)
                is Int -> putInt(it.first, value)
                is Long -> putLong(it.first, value)
                is CharSequence -> putCharSequence(it.first, value)
                is String -> putString(it.first, value)
                is Float -> putFloat(it.first, value)
                is Double -> putDouble(it.first, value)
                is Char -> putChar(it.first, value)
                is Short -> putShort(it.first, value)
                is Boolean -> putBoolean(it.first, value)
                is Serializable -> putSerializable(it.first, value)
                is Parcelable -> putParcelable(it.first, value)

                is IntArray -> putIntArray(it.first, value)
                is LongArray -> putLongArray(it.first, value)
                is FloatArray -> putFloatArray(it.first, value)
                is DoubleArray -> putDoubleArray(it.first, value)
                is CharArray -> putCharArray(it.first, value)
                is ShortArray -> putShortArray(it.first, value)
                is BooleanArray -> putBooleanArray(it.first, value)

                is Array<*> -> when {
                    value.isArrayOf<CharSequence>() -> putCharSequenceArray(it.first, value as Array<CharSequence>)
                    value.isArrayOf<String>() -> putStringArray(it.first, value as Array<String>)
                    value.isArrayOf<Parcelable>() -> putParcelableArray(it.first, value as Array<Parcelable>)
                }
            }
        }
    }

}