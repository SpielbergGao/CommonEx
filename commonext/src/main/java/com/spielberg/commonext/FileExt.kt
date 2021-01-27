package com.spielberg.commonext

import android.content.Context
import android.os.Environment
import java.io.File

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
fun cleanCustomCacheExt(filePath: String?) {
    deleteFilesByDirectoryExt(File(filePath))
}

/** * 清除本应用所有的数据 * * @param context * @param filepath  */
fun Context.cleanApplicationDataExt(vararg filepath: String?) {
    cleanInternalCacheExt()
    cleanExternalCacheExt()
    cleanDatabasesExt()
    cleanSharedPreferenceExt()
    cleanFilesExt()
    for (filePath: String? in filepath) {
        cleanCustomCacheExt(filePath)
    }
}

/** * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory  */
private fun deleteFilesByDirectoryExt(directory: File?) {
    if ((directory != null) && directory.exists() && directory.isDirectory) {
        for (item: File in directory.listFiles()) {
            item.delete()
        }
    }
}