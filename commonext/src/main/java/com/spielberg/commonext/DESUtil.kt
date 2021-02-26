package com.spielberg.commonext

import android.text.TextUtils
import android.util.Base64
import java.security.Key
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import kotlin.experimental.and


//算法名称
const val KEY_ALGORITHM = "DES"
const val CIPHER_ALGORITHM = "DES"

fun StringBuilder.getBase64Key(): String? {
    val bytes = this.toString().toByteArray()
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}

/**
 * 生成密钥key对象
 *
 * @param keyStr 密钥字符串
 * @return 密钥对象
 * @throws Exception
 */
@Throws(Exception::class)
private fun keyGenerator(keyStr: String): SecretKey {
    val desKey = DESKeySpec(keyStr.toByteArray())
    //创建一个密匙工厂，然后用它把DESKeySpec转换成
    val keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM)
    return keyFactory.generateSecret(desKey)
}

// 字节数组转换十六进制字符串
fun hexStringView(bytes: ByteArray): String? {
    val sb = StringBuffer(bytes.size)
    var sTmp: String
    for (i in bytes.indices) {
        sTmp = Integer.toHexString(0xFF and bytes[i].toInt())
        if (sTmp.length < 2) sb.append(0)
        sb.append(sTmp.toUpperCase()).append(" ")
    }
    return sb.toString()
}

/**
 * 加密数据
 *
 * @param data 待加密数据
 * @param key  密钥
 * @return 加密后的数据
 */
@Throws(Exception::class)
fun encrypt(data: String, key: String): String? {
    val deskey: Key = keyGenerator(key)
    // 实例化Cipher对象，它用于完成实际的加密操作
    val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
    val random = SecureRandom()
    //IvParameterSpec random = new IvParameterSpec(key.getBytes());
    // 初始化Cipher对象，设置为加密模式
    cipher.init(Cipher.ENCRYPT_MODE, deskey, random)
    // 加密字节
    val bytes = data.toByteArray()
    // 加密字节16进制查看
    hexStringView(bytes)
    val results = cipher.doFinal(bytes)
    // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
    return Base64.encodeToString(results, Base64.DEFAULT).replace("\r\n|\r|\n".toRegex(), "")
}

/**
 * 解密数据
 *
 * @param data 待解密数据
 * * @param key 密钥
 * * @return 解密后的数据
 */
@Throws(Exception::class)
fun decrypt(data: String?, key: String): String? {
    val deskey: Key = keyGenerator(key)
    val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
    //初始化Cipher对象，设置为解密模式
    cipher.init(Cipher.DECRYPT_MODE, deskey)
    // 解密字节-执行解密操作
    val bytes = cipher.doFinal(Base64.decode(data, Base64.DEFAULT))
    // 解密字节16进制查看
    // hexStringView(bytes);
    return String(bytes)
}

fun String?.md5(): String? {
    if (this.isEmptyOrBlankExt()) {
        return ""
    }
    val md5: MessageDigest?
    try {
        md5 = MessageDigest.getInstance("MD5")
        val bytes: ByteArray = md5.digest(this!!.toByteArray())
        var result = ""
        for (b in bytes) {
            var temp = Integer.toHexString(b.toInt() and 0xff)
            if (temp.length == 1) {
                temp = "0$temp"
            }
            result += temp
        }
        return result.toUpperCase()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}