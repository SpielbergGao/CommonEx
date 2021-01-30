package com.spielberg.commonext

import android.content.Context
import android.os.Environment
import java.io.*
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

/** * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context  */
fun Context.cleanInternalCacheExt() {
    deleteFilesByDirectoryExt(cacheDir)
}

/** * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context  */
fun Context.cleanDatabasesExt() {
    deleteFilesByDirectoryExt(File("/data/data/$packageName/databases"))
}

/**
 * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
 * context
 */
fun Context.cleanSharedPreferenceExt() {
    deleteFilesByDirectoryExt(
        File("/data/data/$packageName/shared_prefs")
    )
}

/** * 按名字清除本应用数据库 * * @param context * @param dbName  */
fun Context.cleanDatabaseByNameExt(dbName: String?) {
    deleteDatabase(dbName)
}

/** * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context  */
fun Context.cleanFilesExt() {
    deleteFilesByDirectoryExt(filesDir)
}

/**
 * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
 * context
 */
fun Context.cleanExternalCacheExt() {
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        deleteFilesByDirectoryExt(externalCacheDir)
    }
}

/** * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath  */
fun String.cleanCustomCacheExt() {
    deleteFilesByDirectoryExt(File(this))
}

/** * 清除本应用所有的数据 * * @param context * @param filepath  */
fun Context.cleanApplicationDataExt(vararg filepath: String?) {
    cleanInternalCacheExt()
    cleanExternalCacheExt()
    cleanDatabasesExt()
    cleanSharedPreferenceExt()
    cleanFilesExt()
    for (filePath: String? in filepath) {
        filePath?.cleanCustomCacheExt()
    }
}

/** * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory  */
private fun deleteFilesByDirectoryExt(directory: File?) {
    if ((directory != null) && directory.exists() && directory.isDirectory) {
        directory.listFiles()?.forEach {
            it.delete()
        }
    }
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 1/30/21 9:37 PM
 *  @describe 把文件从文件系统中读取出来
 *  @return 如果无法读取, 则返回null
 */
fun String.loadFileExt(): ByteArray? {
    return try {
        val f = File(this)
        var unread = f.length().toInt()
        var read = 0
        val buf = ByteArray(unread) // 读取文件长度
        val fin = FileInputStream(f)
        do {
            val count = fin.read(buf, read, unread)
            read += count
            unread -= count
        } while (unread != 0)
        fin.close()
        buf
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        null
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 1/30/21 9:38 PM
 *  @describe 把文件从文件系统中读取出来
 *  @return 如果无法读取, 则返回null
 */
fun File.loadFileExt(): ByteArray? {
    return try {
        var unread = length().toInt()
        var read = 0
        val buf = ByteArray(unread) // 读取文件长度
        val fin = FileInputStream(this)
        do {
            val count = fin.read(buf, read, unread)
            read += count
            unread -= count
        } while (unread != 0)
        fin.close()
        buf
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        null
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun String?.isEmptyOrBlankExt(): Boolean {
    return this.isNullOrBlank() || this.isNullOrEmpty()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 1/30/21 9:48 PM
 *  @describe 复制文件到指定目录 成功返回文件长度否则返回-1
 */
fun String?.copyFileExt(dstPath: String): Long {
    if (this.isEmptyOrBlankExt() || dstPath.isEmptyOrBlankExt()) {
        return -1
    }
    val source = File(this!!)
    if (!source.exists()) {
        return -1
    }
    if (this == dstPath) {
        return source.length()
    }
    var fcin: FileChannel? = null
    var fcout: FileChannel? = null
    try {
        fcin = FileInputStream(source).channel
        fcout = FileOutputStream(dstPath.createFileExt()).channel
        val tmpBuffer = ByteBuffer.allocateDirect(4096)
        while (fcin.read(tmpBuffer) != -1) {
            tmpBuffer.flip()
            fcout.write(tmpBuffer)
            tmpBuffer.clear()
        }
        return source.length()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            fcin?.close()
            fcout?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return -1
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 1/30/21 9:49 PM
 *  @describe 创建文件
 */
fun String?.createFileExt(): File? {
    if (this.isNullOrEmpty() || this.isNullOrBlank()) {
        return null
    }
    val f = File(this)
    if (f.parentFile != null && !f.parentFile!!.exists()) { // 如果不存在上级文件夹
        f.parentFile!!.mkdirs()
    }
    return try {
        f.createNewFile()
        f
    } catch (e: IOException) {
        if (f.exists()) {
            f.delete()
        }
        e.printStackTrace()
        null
    }
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @date: 1/30/21 9:53 PM
 *  @describe 获取文件长度 文件不存在则返回 -1
 */
fun String?.getFileLengthExt(): Long {
    if (this.isNullOrBlank() || this.isNullOrEmpty()) {
        return -1
    }
    val srcFile = File(this)
    return if (!srcFile.exists()) {
        -1
    } else srcFile.length()
}

fun save(path: String?, content: String): Long {
    return save(content.toByteArray(), path)
}

/**
 * 把数据保存到文件系统中，并且返回其大小
 *
 * @param data
 * @param filePath
 * @return 如果保存失败, 则返回-1
 */
fun save(data: ByteArray?, filePath: String?): Long {
    if (filePath.isNullOrEmpty() || filePath.isNullOrBlank()) {
        return -1
    }
    val f = File(filePath)
    if (f.parentFile == null) {
        return -1
    }
    if (!f.parentFile!!.exists()) { // 如果不存在上级文件夹
        f.parentFile!!.mkdirs()
    }
    try {
        f.createNewFile()
        val fout = FileOutputStream(f)
        fout.write(data)
        fout.close()
    } catch (e: IOException) {
        e.printStackTrace()
        return -1
    }
    return f.length()
}