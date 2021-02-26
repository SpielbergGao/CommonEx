package com.spielberg.commonext

import android.util.Base64
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.security.*
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
            var temp = Integer.toHexString((b.toInt() and 0xff))
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

fun File?.md5(): String? {
    if (this == null) return null
    val buffersize = 1024
    val fis: FileInputStream?
    val dis: DigestInputStream?
    try {
        //创建MD5转换器和文件流
        var messageDigest = MessageDigest.getInstance("MD5")
        fis = FileInputStream(this)
        dis = DigestInputStream(fis, messageDigest)
        val buffer = ByteArray(buffersize)
        //DigestInputStream实际上在流处理文件时就在内部就进行了一定的处理
        while (dis.read(buffer) > 0);
        //通过DigestInputStream对象得到一个最终的MessageDigest对象。
        messageDigest = dis.messageDigest
        // 通过messageDigest拿到结果，也是字节数组，包含16个元素
        val array = messageDigest.digest()
        // 同样，把字节数组转换成字符串
        val hex = java.lang.StringBuilder(array.size * 2)
        for (b in array) {
            if (b.toInt() and 0xFF < 0x10) {
                hex.append("0")
            }
            hex.append(Integer.toHexString((b.toInt() and 0xFF)))
        }
        return hex.toString()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}

