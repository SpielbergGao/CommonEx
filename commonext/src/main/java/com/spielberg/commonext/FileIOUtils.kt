package com.spielberg.commonext

import android.util.Log
import java.io.*

private const val sBufferSize = 524288


fun writeFileFromIS(filePath: String?, inputStream: InputStream?): Boolean {
    return writeFileFromIS(filePath, inputStream)
}

/**
 * Write file from input stream.
 *
 * @param file     The file.
 * @param `is`       The input stream.
 * @param append   True to append, false otherwise.
 * @param listener The progress update listener.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromIS(
    file: File,
    inputStream: InputStream?,
    append: Boolean,
    listener: ((progress: Double) -> Unit?)? = null
): Boolean {
    if (inputStream == null || !file.createOrExistsFileExt()) {
        Log.e("FileIOUtils", "create file <$file> failed.")
        return false
    }
    var os: OutputStream? = null
    return try {
        os = BufferedOutputStream(
            FileOutputStream(file, append),
            sBufferSize
        )
        if (listener == null) {
            val data = ByteArray(sBufferSize)
            var len: Int
            while (inputStream.read(data).also { len = it } != -1) {
                os.write(data, 0, len)
            }
        } else {
            val totalSize = inputStream.available().toDouble()
            var curSize = 0
            listener.invoke(0.0)
            val data = ByteArray(sBufferSize)
            var len: Int
            while (inputStream.read(data).also { len = it } != -1) {
                os.write(data, 0, len)
                curSize += len
                listener.invoke(curSize / totalSize)
            }
        }
        true
    } catch (e: IOException) {
        e.printStackTrace()
        false
    } finally {
        try {
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            os?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
