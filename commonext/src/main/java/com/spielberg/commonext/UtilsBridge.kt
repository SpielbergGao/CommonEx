package com.spielberg.commonext

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

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
