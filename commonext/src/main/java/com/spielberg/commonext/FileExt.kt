package com.spielberg.commonext

import android.content.ContentResolver
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.util.Log
import java.io.*
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.security.DigestInputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

private val LINE_SEP = System.getProperty("line.separator")
private const val sBufferSize = 524288

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
 *  @describe 创建文件
 */
fun String?.createFileExt(): File? {
    if (this.isEmptyOrBlankExt()) {
        return null
    }
    val f = File(this!!)
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
 *  @describe Return the file by path.
 */
fun String?.getFileByPathExt(): File? {
    return if (this.isEmptyOrBlankExt()) null else File(this!!)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe 获取文件长度 文件不存在则返回 -1
 */
fun String?.getFileLengthExt(): Long {
    if (this.isEmptyOrBlankExt()) {
        return -1
    }
    val srcFile = File(this!!)
    return if (!srcFile.exists()) {
        -1
    } else srcFile.length()
}

fun File?.getFileLengthExt(): Long {
    if (this == null) return -1
    if (!this.exists()) return -1
    return this.length()
}

fun saveExt(path: String?, content: String): Long {
    return saveExt(content.toByteArray(), path)
}

/**
 * 把数据保存到文件系统中，并且返回其大小
 *
 * @param data
 * @param filePath
 * @return 如果保存失败, 则返回-1
 */
fun saveExt(data: ByteArray?, filePath: String?): Long {
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

fun moveExt(srcFilePath: String?, dstFilePath: String?): Boolean {
    if (srcFilePath.isEmptyOrBlankExt() || dstFilePath.isEmptyOrBlankExt()) {
        return false
    }
    val srcFile = File(srcFilePath!!)
    if (!srcFile.exists() || !srcFile.isFile) {
        return false
    }
    val dstFile = File(dstFilePath!!)
    if (dstFile.parentFile == null) {
        return false
    }
    if (!dstFile.parentFile!!.exists()) { // 如果不存在上级文件夹
        dstFile.parentFile!!.mkdirs()
    }
    return srcFile.renameTo(dstFile)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the name of file without extension.
 */
fun File?.getFileNameNoExtensionExt(): String? {
    return if (this == null) "" else path.getFileNameNoExtensionExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the name of file without extension.
 */
fun String.getFileNameNoExtensionExt(): String? {
    if (this.isEmptyOrBlankExt()) return ""
    val lastPoi = this.lastIndexOf('.')
    val lastSep = this.lastIndexOf(File.separator)
    if (lastSep == -1) {
        return if (lastPoi == -1) this else this.substring(0, lastPoi)
    }
    return if (lastPoi == -1 || lastSep > lastPoi) {
        this.substring(lastSep + 1)
    } else this.substring(lastSep + 1, lastPoi)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe 获取文件名
 */
fun String?.getFileNameFromPathExt(): String? {
    if (this.isEmptyOrBlankExt()) return null
    val sep = this!!.lastIndexOf('/')
    if (sep > -1 && sep < this.length - 1) {
        return this.substring(sep + 1)
    }
    return null
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the name of file.
 */
fun String.getFileNameExt(): String? {
    if (this.isEmptyOrBlankExt()) return ""
    val lastSep = this.lastIndexOf(File.separator)
    return if (lastSep == -1) this else this.substring(lastSep + 1)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the name of file.
 */
fun File?.getFileNameExt(): String? {
    return if (this == null) "" else this.absolutePath.getFileNameExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the file's path of directory.
 */
fun File?.getDirNameExt(): String? {
    return if (this == null) "" else this.absolutePath.getDirNameExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the file's path of directory.
 */
fun String.getDirNameExt(): String? {
    if (this.isEmptyOrBlankExt()) return ""
    val lastSep = this.lastIndexOf(File.separator)
    return if (lastSep == -1) "" else this.substring(0, lastSep + 1)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe  获取不带扩展名的文件名
 */
fun String?.getFileNameNoExt(): String? {
    if (this.isEmptyOrBlankExt()) return null
    val dot = this!!.lastIndexOf('.')
    if (dot > -1 && dot < this.length) {
        return this.substring(0, dot)
    }
    return null
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the extension of file.
 */
fun File?.getFileExtensionExt(): String? {
    return if (this == null) "" else path.getFileExtensionExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the extension of file.
 */
fun String.getFileExtensionExt(): String? {
    if (this.isEmptyOrBlankExt()) return ""
    val lastPoi = this.lastIndexOf('.')
    val lastSep = this.lastIndexOf(File.separator)
    return if (lastPoi == -1 || lastSep >= lastPoi) "" else this.substring(lastPoi + 1)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Notify system to scan the file.
 */
fun String?.notifySystemToScan() {
    this?.createFileExt()?.notifySystemToScanExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Notify system to scan the file.
 */
fun File?.notifySystemToScanExt() {
    if (this == null || !this.exists()) return
    getApplicationByReflect()?.run {
        MediaScannerConnection.scanFile(
            this, arrayOf(this@notifySystemToScanExt.toString()),
            arrayOf(this@notifySystemToScanExt.name), null
        )
    }
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the total size of file system.
 */
fun String?.getFsTotalSizeExt(): Long {
    if (this.isEmptyOrBlankExt()) return 0
    val statFs = StatFs(this)
    val blockSize: Long
    val totalSize: Long
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        blockSize = statFs.blockSizeLong
        totalSize = statFs.blockCountLong
    } else {
        blockSize = statFs.blockSize.toLong()
        totalSize = statFs.blockCount.toLong()
    }
    return blockSize * totalSize
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe  Return the available size of file system.
 */
fun String?.getFsAvailableSizeExt(): Long {
    if (this.isEmptyOrBlankExt()) return 0
    val statFs = StatFs(this)
    val blockSize: Long
    val availableSize: Long
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        blockSize = statFs.blockSizeLong
        availableSize = statFs.availableBlocksLong
    } else {
        blockSize = statFs.blockSize.toLong()
        availableSize = statFs.availableBlocks.toLong()
    }
    return blockSize * availableSize
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe  Return the MD5 of file.
 */
fun String?.getFileMD5ToStringExt(): String? {
    val file = if (this.isEmptyOrBlankExt()) null else File(this!!)
    return file.getFileMD5ToStringExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the MD5 of file.
 */
fun File?.getFileMD5ToStringExt(): String? {
    return this.getFileMD5Ext().bytes2HexStringExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the MD5 of file.
 */
fun String?.getFileMD5Ext(): ByteArray? {
    return this.getFileByPathExt().getFileMD5Ext()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the MD5 of file.
 */
fun File?.getFileMD5Ext(): ByteArray? {
    if (this == null) return null
    var dis: DigestInputStream? = null
    try {
        val fis = FileInputStream(this)
        var md = MessageDigest.getInstance("MD5")
        dis = DigestInputStream(fis, md)
        val buffer = ByteArray(1024 * 256)
        while (true) {
            if (dis.read(buffer) <= 0) break
        }
        md = dis.messageDigest
        return md.digest()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            dis?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return null
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return whether the file exists.
 */
fun File?.isFileExists(): Boolean {
    if (this == null) return false
    return if (this.exists()) {
        true
    } else this.absolutePath.isFileExistsExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return whether the file exists.
 */
fun String?.isFileExistsExt(): Boolean {
    val file = this.getFileByPathExt() ?: return false
    return if (file.exists()) {
        true
    } else this.isFileExistsApi29Ext()
}

fun String?.isFileExistsApi29Ext(): Boolean {
    if (Build.VERSION.SDK_INT < 29) return false
    try {
        val uri = Uri.parse(this)
        val cr: ContentResolver? = getApplicationByReflect()?.contentResolver
        val afd = cr?.openAssetFileDescriptor(uri, "r") ?: return false
        afd.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        return false
    } catch (ignore: IOException) {
        ignore.printStackTrace()
        return false
    }
    return true
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Rename the file.
 */
fun String?.renameExt(newName: String): Boolean {
    return this.getFileByPathExt().renameExt(newName)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Rename the file.
 */
fun File?.renameExt(newName: String): Boolean {
    // file is null then return false
    if (this == null) return false
    // file doesn't exist then return false
    if (!this.exists()) return false
    // the new name is space then return false
    if (newName.isEmptyOrBlankExt()) return false
    // the new name equals old name then return true
    if (newName == this.name) return true
    val newFile = File(this.parent + File.separator + newName)
    // the new name of file exists then return false
    return (!newFile.exists() && this.renameTo(newFile))
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return whether it is a directory.
 */
fun String?.isDirExt(): Boolean {
    return this.getFileByPathExt().isDirExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return whether it is a directory.
 */
fun File?.isDirExt(): Boolean {
    return this != null && this.exists() && this.isDirectory
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return whether it is a file.
 */
fun String?.isFileExt(): Boolean {
    return this.getFileByPathExt().isFileExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return whether it is a file.
 */
fun File?.isFileExt(): Boolean {
    return this != null && this.exists() && this.isFile
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Create a directory if it doesn't exist, otherwise do nothing.
 */
fun String?.createOrExistsDirExt(): Boolean {
    return this.getFileByPathExt().createOrExistsDirExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Create a directory if it doesn't exist, otherwise do nothing.
 */
fun File?.createOrExistsDirExt(): Boolean {
    return this != null && if (this.exists()) this.isDirectory else this.mkdirs()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Create a file if it doesn't exist, otherwise do nothing.
 */
fun String?.createOrExistsFileExt(): Boolean {
    return this.getFileByPathExt().createOrExistsFileExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe  Create a file if it doesn't exist, otherwise do nothing.
 */
fun File?.createOrExistsFileExt(): Boolean {
    if (this == null) return false
    if (this.exists()) return this.isFile
    return if (!this.parentFile.createOrExistsDirExt()) false else try {
        this.createNewFile()
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Create a file if it doesn't exist, otherwise delete old file before creating.
 */
fun String?.createFileByDeleteOldFileExt(): Boolean {
    return this.getFileByPathExt().createFileByDeleteOldFileExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Create a file if it doesn't exist, otherwise delete old file before creating.
 */
fun File?.createFileByDeleteOldFileExt(): Boolean {
    if (this == null) return false
    // file exists and unsuccessfully delete then return false
    if (this.exists() && !this.delete()) return false
    return if (!this.parentFile.createOrExistsDirExt()) false else try {
        this.createNewFile()
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Delete the directory.
 */
fun String?.deleteExt(): Boolean {
    return this.getFileByPathExt().deleteExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Delete the directory.
 */
fun File?.deleteExt(): Boolean {
    if (this == null) return false
    return if (this.isDirectory) {
        this.deleteDirExt()
    } else this.deleteFileExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Delete the directory.
 */
private fun File?.deleteDirExt(): Boolean {
    if (this == null) return false
    // dir doesn't exist then return true
    if (!this.exists()) return true
    // dir isn't a directory then return false
    if (!this.isDirectory) return false
    val files = this.listFiles()
    if (files != null && files.isNotEmpty()) {
        for (file in files) {
            if (file.isFile) {
                if (!file.delete()) return false
            } else if (file.isDirectory) {
                if (!this.deleteDirExt()) return false
            }
        }
    }
    return this.delete()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Delete the file.
 */
private fun File?.deleteFileExt(): Boolean {
    return this != null && (!this.exists() || this.isFile && this.delete())
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Delete the all in directory.
 */
fun String?.deleteAllInDirExt(): Boolean {
    return this.getFileByPathExt().deleteAllInDirExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Delete the all in directory.
 */
fun File?.deleteAllInDirExt(): Boolean {
    return this.deleteFilesInDirWithFilterExt() { true }
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Delete all files in directory.
 */
fun String?.deleteFilesInDirExt(): Boolean {
    return this.getFileByPathExt().deleteFilesInDirExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Delete all files in directory.
 */
fun File?.deleteFilesInDirExt(): Boolean {
    return this.deleteFilesInDirWithFilterExt() { pathname -> pathname.isFile }
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Delete all files that satisfy the filter in directory.
 */
fun deleteFilesInDirWithFilter(
    dirPath: String?,
    filter: FileFilter?
): Boolean {
    return dirPath.getFileByPathExt().deleteFilesInDirWithFilterExt(filter)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Delete all files that satisfy the filter in directory.
 */
fun File?.deleteFilesInDirWithFilterExt(filter: FileFilter?): Boolean {
    if (this == null || filter == null) return false
    // dir doesn't exist then return true
    if (!this.exists()) return true
    // dir isn't a directory then return false
    if (!this.isDirectory) return false
    val files = this.listFiles()
    if (files != null && files.isNotEmpty()) {
        for (file in files) {
            if (filter.accept(file)) {
                if (file.isFile) {
                    if (!file.delete()) return false
                } else if (file.isDirectory) {
                    if (!file.deleteDirExt()) return false
                }
            }
        }
    }
    return true
}

/**
 * Return the files in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dirPath The path of directory.
 * @return the files in directory
 */
fun listFilesInDir(dirPath: String?): List<File?>? {
    return listFilesInDir(dirPath, null)
}

/**
 * Return the files in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dir The directory.
 * @return the files in directory
 */
fun listFilesInDir(dir: File?): List<File?>? {
    return listFilesInDir(dir, null)
}

/**
 * Return the files in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dirPath    The path of directory.
 * @param comparator The comparator to determine the order of the list.
 * @return the files in directory
 */
fun listFilesInDir(dirPath: String?, comparator: Comparator<File?>?): List<File?>? {
    return listFilesInDir(dirPath.getFileByPathExt(), false, comparator)
}

/**
 * Return the files in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dir        The directory.
 * @param comparator The comparator to determine the order of the list.
 * @return the files in directory
 */
fun listFilesInDir(dir: File?, comparator: Comparator<File?>?): List<File?>? {
    return listFilesInDir(dir, false, comparator)
}

/**
 * Return the files in directory.
 *
 * @param dirPath     The path of directory.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @return the files in directory
 */
fun listFilesInDir(dirPath: String?, isRecursive: Boolean): List<File?>? {
    return listFilesInDir(dirPath.getFileByPathExt(), isRecursive)
}

/**
 * Return the files in directory.
 *
 * @param dir         The directory.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @return the files in directory
 */
fun listFilesInDir(dir: File?, isRecursive: Boolean): List<File?>? {
    return listFilesInDir(dir, isRecursive, null)
}

/**
 * Return the files in directory.
 *
 * @param dirPath     The path of directory.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @param comparator  The comparator to determine the order of the list.
 * @return the files in directory
 */
fun listFilesInDir(
    dirPath: String?,
    isRecursive: Boolean,
    comparator: Comparator<File?>?
): List<File?>? {
    return listFilesInDir(dirPath.getFileByPathExt(), isRecursive, comparator)
}

/**
 * Return the files in directory.
 *
 * @param dir         The directory.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @param comparator  The comparator to determine the order of the list.
 * @return the files in directory
 */
fun listFilesInDir(
    dir: File?,
    isRecursive: Boolean,
    comparator: Comparator<File?>?
): List<File?>? {
    return listFilesInDirWithFilter(dir, { true }, isRecursive, comparator)
}

/**
 * Return the files that satisfy the filter in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dirPath The path of directory.
 * @param filter  The filter.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dirPath: String?,
    filter: FileFilter
): List<File?>? {
    return listFilesInDirWithFilter(dirPath.getFileByPathExt(), filter)
}

/**
 * Return the files that satisfy the filter in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dir    The directory.
 * @param filter The filter.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dir: File?,
    filter: FileFilter
): List<File?>? {
    return listFilesInDirWithFilter(dir, filter, false, null)
}

/**
 * Return the files that satisfy the filter in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dirPath    The path of directory.
 * @param filter     The filter.
 * @param comparator The comparator to determine the order of the list.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dirPath: String?,
    filter: FileFilter,
    comparator: Comparator<File?>?
): List<File?>? {
    return listFilesInDirWithFilter(dirPath.getFileByPathExt(), filter, comparator)
}

/**
 * Return the files that satisfy the filter in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dir        The directory.
 * @param filter     The filter.
 * @param comparator The comparator to determine the order of the list.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dir: File?,
    filter: FileFilter,
    comparator: Comparator<File?>?
): List<File?>? {
    return listFilesInDirWithFilter(dir, filter, false, comparator)
}

/**
 * Return the files that satisfy the filter in directory.
 *
 * @param dirPath     The path of directory.
 * @param filter      The filter.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dirPath: String?,
    filter: FileFilter,
    isRecursive: Boolean
): List<File?>? {
    return listFilesInDirWithFilter(dirPath.getFileByPathExt(), filter, isRecursive)
}

/**
 * Return the files that satisfy the filter in directory.
 *
 * @param dir         The directory.
 * @param filter      The filter.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dir: File?,
    filter: FileFilter,
    isRecursive: Boolean
): List<File?>? {
    return listFilesInDirWithFilter(dir, filter, isRecursive, null)
}


/**
 * Return the files that satisfy the filter in directory.
 *
 * @param dirPath     The path of directory.
 * @param filter      The filter.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @param comparator  The comparator to determine the order of the list.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dirPath: String?,
    filter: FileFilter,
    isRecursive: Boolean,
    comparator: Comparator<File?>?
): List<File?>? {
    return listFilesInDirWithFilter(dirPath.getFileByPathExt(), filter, isRecursive, comparator)
}

/**
 * Return the files that satisfy the filter in directory.
 *
 * @param dir         The directory.
 * @param filter      The filter.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @param comparator  The comparator to determine the order of the list.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dir: File?,
    filter: FileFilter,
    isRecursive: Boolean,
    comparator: Comparator<File?>?
): List<File?>? {
    val files = listFilesInDirWithFilterInner(dir, filter, isRecursive)
    if (comparator != null) {
        Collections.sort(files, comparator)
    }
    return files
}

private fun listFilesInDirWithFilterInner(
    dir: File?,
    filter: FileFilter,
    isRecursive: Boolean
): List<File?> {
    val list: MutableList<File?> = ArrayList()
    if (!dir.isDirExt()) return list
    val files = dir!!.listFiles()
    if (files != null && files.isNotEmpty()) {
        for (file in files) {
            if (filter.accept(file)) {
                list.add(file)
            }
            if (isRecursive && file.isDirectory) {
                list.addAll(listFilesInDirWithFilterInner(file, filter, true))
            }
        }
    }
    return list
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the time that the file was last modified.
 */
fun String?.getFileLastModifiedExt(): Long {
    return this.getFileByPathExt().getFileLastModifiedExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the time that the file was last modified.
 */
fun File?.getFileLastModifiedExt(): Long {
    return this?.lastModified() ?: -1
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the number of lines of file.
 */
fun String?.getFileLinesExt(): Int {
    return this.getFileByPathExt().getFileLinesExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the number of lines of file.
 */
fun File?.getFileLinesExt(): Int {
    var count = 1
    var inputStream: InputStream? = null
    try {
        inputStream = BufferedInputStream(FileInputStream(this))
        val buffer = ByteArray(1024)
        var readChars: Int
        if (LINE_SEP.endsWith("\n")) {
            while (inputStream.read(buffer, 0, 1024).also { readChars = it } != -1) {
                for (i in 0 until readChars) {
                    if (buffer[i].toChar() == '\n') ++count
                }
            }
        } else {
            while (inputStream.read(buffer, 0, 1024).also { readChars = it } != -1) {
                for (i in 0 until readChars) {
                    if (buffer[i].toChar() == '\r') ++count
                }
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            inputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return count
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the size.
 */
fun String?.getSizeExt(): String? {
    return this.getFileByPathExt().getSizeExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the size.
 */
fun File?.getSizeExt(): String? {
    if (this == null) return ""
    return if (this.isDirectory) {
        this.getDirSizeExt()
    } else this.getFileSizeExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the size of directory.
 */
fun File.getDirSizeExt(): String? {
    val len = this.getDirLengthExt()
    return if (len == -1L) "" else byte2FitMemorySize(len)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the size of file.
 */
fun File.getFileSizeExt(): String? {
    val len: Long = this.getFileLengthExt()
    return if (len == -1L) "" else byte2FitMemorySize(len)
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the length.
 */
fun String?.getLengthExt(): Long {
    return this.getFileByPathExt().getLengthExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the length.
 */
fun File?.getLengthExt(): Long {
    if (this == null) return 0
    return if (this.isDirectory) {
        this.getDirLengthExt()
    } else this.getFileLengthExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the length of directory.
 */
fun File.getDirLengthExt(): Long {
    if (!this.isDirExt()) return 0
    var len: Long = 0
    val files = this.listFiles()
    if (files != null && files.isNotEmpty()) {
        for (file in files) {
            len += if (file.isDirectory) {
                file.getDirLengthExt()
            } else {
                file.length()
            }
        }
    }
    return len
}



