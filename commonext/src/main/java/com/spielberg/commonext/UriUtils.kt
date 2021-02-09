package com.spielberg.commonext

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.storage.StorageManager
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import androidx.core.content.FileProvider

/**
 * Resource to uri.
 *
 * res2Uri([res type]/[res name]) -> res2Uri(drawable/icon), res2Uri(raw/icon)
 *
 * res2Uri([resource_id]) -> res2Uri(R.drawable.icon)
 *
 * @param resPath The path of res.
 * @return uri
 */
fun String.res2UriExt(): Uri? {
    return Uri.parse(
        "android.resource://" + getApplicationByReflect()?.packageName.toString() + "/" + this
    )
}

/**
 * File to uri.
 *
 * @param file The file.
 * @return uri
 */
fun File?.file2UriExt(): Uri? {
    if (this == null) return null
    if (!this.isFileExists()) return null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val authority = "${getApplicationByReflect()?.packageName}.utilcode.provider"
        val context = getApplicationByReflect()?.baseContext ?: return null
        return FileProvider.getUriForFile(context, authority, this)
    } else {
        return Uri.fromFile(this)
    }
}

/**
 * Uri to file.
 *
 * @param uri The uri.
 * @return file
 */
@RequiresApi(Build.VERSION_CODES.KITKAT)
fun Uri?.uri2FileExt(): File? {
    if (this == null) return null
    return uri2FileReal(this) ?: copyUri2Cache(this)
}

/**
 * Uri to file.
 *
 * @param uri The uri.
 * @return file
 */
@RequiresApi(Build.VERSION_CODES.KITKAT)
fun uri2FileReal(uri: Uri): File? {
    Log.d("UriUtils", uri.toString())
    val authority = uri.authority
    val scheme = uri.scheme
    val path = uri.path
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && path != null) {
        val externals = arrayOf("/external/", "/external_path/")
        var file: File? = null
        for (external: String in externals) {
            if (path.startsWith(external)) {
                file = File(
                    Environment.getExternalStorageDirectory().absolutePath
                            + path.replace(external, "/")
                )
                if (file.exists()) {
                    Log.d("UriUtils", "$uri -> $external")
                    return file
                }
            }
        }
        file = null
        when {
            path.startsWith("/files_path/") -> {
                file = File(
                    "${getApplicationByReflect()?.filesDir?.absolutePath}${
                        path.replace(
                            "/files_path/",
                            "/"
                        )
                    }"
                )
            }
            path.startsWith("/cache_path/") -> {
                file = File(
                    "${getApplicationByReflect()?.cacheDir?.absolutePath}${
                        path.replace(
                            "/cache_path/",
                            "/"
                        )
                    }"
                )
            }
            path.startsWith("/external_files_path/") -> {
                file = File(
                    "${getApplicationByReflect()?.getExternalFilesDir(null)?.absolutePath}${
                        path.replace(
                            "/external_files_path/",
                            "/"
                        )
                    }"
                )
            }
            path.startsWith("/external_cache_path/") -> {
                file = File(
                    "${getApplicationByReflect()?.externalCacheDir?.absolutePath}${
                        path.replace(
                            "/external_cache_path/",
                            "/"
                        )
                    }"
                )
            }
        }
        if (file != null && file.exists()) {
            Log.d("UriUtils", "$uri -> $path")
            return file
        }
    }
    if ((ContentResolver.SCHEME_FILE == scheme)) {
        if (path != null) return File(path)
        Log.d("UriUtils", "$uri parse failed. -> 0")
        return null
    } // end 0
    else if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && DocumentsContract.isDocumentUri(getApplicationByReflect(), uri))
    ) {
        if (("com.android.externalstorage.documents" == authority)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).toTypedArray()
            val type = split[0]
            if ("primary".equals(type, ignoreCase = true)) {
                return File(Environment.getExternalStorageDirectory().toString() + "/" + split[1])
            } else {
                // Below logic is how External Storage provider build URI for documents
                // http://stackoverflow.com/questions/28605278/android-5-sd-card-label
                val mStorageManager =
                    getApplicationByReflect()?.getSystemService(Context.STORAGE_SERVICE) as? StorageManager
                try {
                    val storageVolumeClazz = Class.forName("android.os.storage.StorageVolume")
                    val getVolumeList = mStorageManager?.javaClass?.getMethod("getVolumeList")
                    val getUuid = storageVolumeClazz.getMethod("getUuid")
                    val getState = storageVolumeClazz.getMethod("getState")
                    val getPath = storageVolumeClazz.getMethod("getPath")
                    val isPrimary = storageVolumeClazz.getMethod("isPrimary")
                    val isEmulated = storageVolumeClazz.getMethod("isEmulated")
                    val result = getVolumeList?.invoke(mStorageManager)
                    val length = java.lang.reflect.Array.getLength(result)
                    for (i in 0 until length) {
                        val storageVolumeElement = java.lang.reflect.Array.get(result, i)
                        //String uuid = (String) getUuid.invoke(storageVolumeElement);
                        val mounted =
                            ((Environment.MEDIA_MOUNTED == getState.invoke(storageVolumeElement)) || (Environment.MEDIA_MOUNTED_READ_ONLY == getState.invoke(
                                storageVolumeElement
                            )))

                        //if the media is not mounted, we need not get the volume details
                        if (!mounted) continue

                        //Primary storage is already handled.
                        if ((isPrimary.invoke(storageVolumeElement) as Boolean
                                    && isEmulated.invoke(storageVolumeElement) as Boolean)
                        ) {
                            continue
                        }
                        val uuid: String? = getUuid.invoke(storageVolumeElement) as String
                        if (uuid != null && (uuid == type)) {
                            return File(
                                getPath.invoke(storageVolumeElement).toString() + "/" + split[1]
                            )
                        }
                    }
                } catch (ex: Exception) {
                    Log.d("UriUtils", "$uri parse failed. $ex -> 1_0")
                }
            }
            Log.d("UriUtils", "$uri parse failed. -> 1_0")
            return null
        } // end 1_0
        else if (("com.android.providers.downloads.documents" == authority)) {
            var id = DocumentsContract.getDocumentId(uri)
            if (TextUtils.isEmpty(id)) {
                Log.d("UriUtils", "$uri parse failed(id is null). -> 1_1")
                return null
            }
            if (id.startsWith("raw:")) {
                return File(id.substring(4))
            } else if (id.startsWith("msf:")) {
                id = id.split(":".toRegex()).toTypedArray()[1]
            }
            var availableId: Long = 0
            try {
                availableId = id.toLong()
            } catch (e: Exception) {
                return null
            }
            val contentUriPrefixesToTry = arrayOf(
                "content://downloads/public_downloads",
                "content://downloads/all_downloads",
                "content://downloads/my_downloads"
            )
            for (contentUriPrefix: String in contentUriPrefixesToTry) {
                val contentUri =
                    ContentUris.withAppendedId(Uri.parse(contentUriPrefix), availableId)
                try {
                    val file = getFileFromUri(contentUri, "1_1")
                    if (file != null) {
                        return file
                    }
                } catch (ignore: Exception) {
                }
            }
            Log.d("UriUtils", "$uri parse failed. -> 1_1")
            return null
        } // end 1_1
        else if (("com.android.providers.media.documents" == authority)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).toTypedArray()
            val type = split[0]
            val contentUri: Uri
            if (("image" == type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            } else if (("video" == type)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            } else if (("audio" == type)) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            } else {
                Log.d("UriUtils", "$uri parse failed. -> 1_2")
                return null
            }
            val selection = "_id=?"
            val selectionArgs = arrayOf(split[1])
            return getFileFromUri(contentUri, selection, selectionArgs, "1_2")
        } // end 1_2
        else if ((ContentResolver.SCHEME_CONTENT == scheme)) {
            return getFileFromUri(uri, "1_3")
        } // end 1_3
        else {
            Log.d("UriUtils", "$uri parse failed. -> 1_4")
            return null
        } // end 1_4
    } // end 1
    else if ((ContentResolver.SCHEME_CONTENT == scheme)) {
        return getFileFromUri(uri, "2")
    } // end 2
    else {
        Log.d("UriUtils", "$uri parse failed. -> 3")
        return null
    } // end 3
}

fun getFileFromUri(uri: Uri, code: String): File? {
    return getFileFromUri(uri, null, null, code)
}

fun getFileFromUri(
    uri: Uri,
    selection: String?,
    selectionArgs: Array<String>?,
    code: String
): File? {
    if (("com.google.android.apps.photos.content" == uri.authority)) {
        if (!uri.lastPathSegment.isEmptyOrBlankExt()) {
            return File(uri.lastPathSegment)
        }
    } else if (("com.tencent.mtt.fileprovider" == uri.authority)) {
        val path = uri.path
        if (!TextUtils.isEmpty(path)) {
            val fileDir = Environment.getExternalStorageDirectory()
            return File(fileDir, path!!.substring("/QQBrowser".length, path.length))
        }
    } else if (("com.huawei.hidisk.fileprovider" == uri.authority)) {
        val path = uri.path
        if (!TextUtils.isEmpty(path)) {
            return File(path!!.replace("/root", ""))
        }
    }
    val cursor: Cursor? = getApplicationByReflect()?.contentResolver?.query(
        uri, arrayOf("_data"), selection, selectionArgs, null
    )
    if (cursor == null) {
        Log.d("UriUtils", "$uri parse failed(cursor is null). -> $code")
        return null
    }
    try {
        return if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("_data")
            if (columnIndex > -1) {
                File(cursor.getString(columnIndex))
            } else {
                Log.d(
                    "UriUtils",
                    "$uri parse failed(columnIndex: $columnIndex is wrong). -> $code"
                )
                null
            }
        } else {
            Log.d("UriUtils", "$uri parse failed(moveToFirst return false). -> $code")
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.d("UriUtils", "$uri parse failed. -> $code")
        return null
    } finally {
        cursor.close()
    }
}

private fun copyUri2Cache(uri: Uri): File? {
    Log.d("UriUtils", "copyUri2Cache() called")
    var inputStream: InputStream? = null
    try {
        inputStream = getApplicationByReflect()?.contentResolver?.openInputStream(uri)
        val file = File(getApplicationByReflect()?.cacheDir, System.currentTimeMillis().toString())
        writeFileFromIS(file.absolutePath, inputStream)
        return file
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        return null
    } finally {
        if (inputStream != null) {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

/**
 * uri to input stream.
 *
 * @param uri The uri.
 * @return the input stream
 */
fun uri2Bytes(uri: Uri?): ByteArray? {
    if (uri == null) return null
    var inputStream: InputStream? = null
    try {
        inputStream = getApplicationByReflect()?.contentResolver?.openInputStream(uri)
        return inputStream.inputStream2BytesExt()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        Log.d("UriUtils", "uri to bytes failed.")
        return null
    } finally {
        if (inputStream != null) {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}