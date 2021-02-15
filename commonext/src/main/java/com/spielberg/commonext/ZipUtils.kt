package com.spielberg.commonext

import android.util.Log
import java.io.*
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

private const val BUFFER_LEN = 8192

/**
 * Zip the files.
 *
 * @param srcFiles    The source of files.
 * @param zipFilePath The path of ZIP file.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun zipFiles(srcFiles: Collection<String?>?, zipFilePath: String?): Boolean {
    return zipFiles(srcFiles, zipFilePath, null)
}

/**
 * Zip the files.
 *
 * @param srcFilePaths The paths of source files.
 * @param zipFilePath  The path of ZIP file.
 * @param comment      The comment.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun zipFiles(srcFilePaths: Collection<String?>?, zipFilePath: String?, comment: String?): Boolean {
    if (srcFilePaths == null || zipFilePath == null) return false
    var zos: ZipOutputStream? = null
    return try {
        zos = ZipOutputStream(FileOutputStream(zipFilePath))
        for (srcFile in srcFilePaths) {
            val file = srcFile.getFileByPathExt()
            if (file != null && !zipFile(file, "", zos, comment)) return false
        }
        true
    } finally {
        if (zos != null) {
            zos.finish()
            zos.close()
        }
    }
}

/**
 * Zip the files.
 *
 * @param srcFiles The source of files.
 * @param zipFile  The ZIP file.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun zipFiles(srcFiles: Collection<File>?, zipFile: File?): Boolean {
    return zipFiles(srcFiles, zipFile, null)
}

/**
 * Zip the files.
 *
 * @param srcFiles The source of files.
 * @param zipFile  The ZIP file.
 * @param comment  The comment.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun zipFiles(srcFiles: Collection<File>?, zipFile: File?, comment: String?): Boolean {
    if (srcFiles == null || zipFile == null) return false
    var zos: ZipOutputStream? = null
    return try {
        zos = ZipOutputStream(FileOutputStream(zipFile))
        for (srcFile in srcFiles) {
            if (!zipFile(srcFile, "", zos, comment)) return false
        }
        true
    } finally {
        if (zos != null) {
            zos.finish()
            zos.close()
        }
    }
}

/**
 * Zip the file.
 *
 * @param srcFilePath The path of source file.
 * @param zipFilePath The path of ZIP file.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun zipFile(srcFilePath: String?, zipFilePath: String?): Boolean {
    return zipFile(
        srcFilePath.getFileByPathExt(),
        zipFilePath.getFileByPathExt(),
        null
    )
}

/**
 * Zip the file.
 *
 * @param srcFilePath The path of source file.
 * @param zipFilePath The path of ZIP file.
 * @param comment     The comment.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun zipFile(srcFilePath: String?, zipFilePath: String?, comment: String?): Boolean {
    return zipFile(
        srcFilePath.getFileByPathExt(),
        zipFilePath.getFileByPathExt(),
        comment
    )
}

/**
 * Zip the file.
 *
 * @param srcFile The source of file.
 * @param zipFile The ZIP file.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun zipFile(srcFile: File?, zipFile: File?): Boolean {
    return zipFile(srcFile, zipFile, null)
}

/**
 * Zip the file.
 *
 * @param srcFile The source of file.
 * @param zipFile The ZIP file.
 * @param comment The comment.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun zipFile(srcFile: File?, zipFile: File?, comment: String?): Boolean {
    if (srcFile == null || zipFile == null) return false
    var zos: ZipOutputStream? = null
    return try {
        zos = ZipOutputStream(FileOutputStream(zipFile))
        zipFile(srcFile, "", zos, comment)
    } finally {
        zos?.close()
    }
}

@Throws(IOException::class)
private fun zipFile(srcFile: File, rootPath: String, zos: ZipOutputStream, comment: String?): Boolean {
    var rootPath = rootPath
    rootPath = rootPath + (if (rootPath.isEmptyOrBlankExt()) "" else File.separator) + srcFile.name
    if (srcFile.isDirectory) {
        val fileList = srcFile.listFiles()
        if (fileList == null || fileList.isEmpty()) {
            val entry = ZipEntry("$rootPath/")
            entry.comment = comment
            zos.putNextEntry(entry)
            zos.closeEntry()
        } else {
            for (file in fileList) {
                if (!zipFile(file, rootPath, zos, comment)) return false
            }
        }
    } else {
        var inputStream: InputStream? = null
        try {
            inputStream = BufferedInputStream(FileInputStream(srcFile))
            val entry = ZipEntry(rootPath)
            entry.comment = comment
            zos.putNextEntry(entry)
            val buffer = ByteArray(BUFFER_LEN)
            var len: Int
            while (inputStream.read(buffer, 0, BUFFER_LEN).also {
                    len = it
                } != -1) {
                zos.write(buffer, 0, len)
            }
            zos.closeEntry()
        } finally {
            inputStream?.close()
        }
    }
    return true
}

/**
 * Unzip the file.
 *
 * @param zipFilePath The path of ZIP file.
 * @param destDirPath The path of destination directory.
 * @return the unzipped files
 * @throws IOException if unzip unsuccessfully
 */
@Throws(IOException::class)
fun unzipFile(zipFilePath: String?, destDirPath: String?): List<File?>? {
    return unzipFileByKeyword(zipFilePath, destDirPath, null)
}

/**
 * Unzip the file.
 *
 * @param zipFile The ZIP file.
 * @param destDir The destination directory.
 * @return the unzipped files
 * @throws IOException if unzip unsuccessfully
 */
@Throws(IOException::class)
fun unzipFile(zipFile: File?, destDir: File?): List<File?>? {
    return unzipFileByKeyword(zipFile, destDir, null)
}

/**
 * Unzip the file by keyword.
 *
 * @param zipFilePath The path of ZIP file.
 * @param destDirPath The path of destination directory.
 * @param keyword     The keyboard.
 * @return the unzipped files
 * @throws IOException if unzip unsuccessfully
 */
@Throws(IOException::class)
fun unzipFileByKeyword(zipFilePath: String?, destDirPath: String?, keyword: String?): List<File?>? {
    return unzipFileByKeyword(
        zipFilePath.getFileByPathExt(),
        destDirPath.getFileByPathExt(),
        keyword
    )
}

/**
 * Unzip the file by keyword.
 *
 * @param zipFile The ZIP file.
 * @param destDir The destination directory.
 * @param keyword The keyboard.
 * @return the unzipped files
 * @throws IOException if unzip unsuccessfully
 */
@Throws(IOException::class)
fun unzipFileByKeyword(zipFile: File?, destDir: File?, keyword: String?): List<File?>? {
    if (zipFile == null || destDir == null) return null
    val files: MutableList<File?> = ArrayList()
    val zip = ZipFile(zipFile)
    val entries: Enumeration<*> = zip.entries()
    zip.use { zip ->
        if (keyword.isEmptyOrBlankExt()) {
            while (entries.hasMoreElements()) {
                val entry = entries.nextElement() as ZipEntry
                val entryName = entry.name.replace("\\", "/")
                if (entryName.contains("../")) {
                    Log.e("ZipUtils", "entryName: $entryName is dangerous!")
                    continue
                }
                if (!unzipChildFile(destDir, files, zip, entry, entryName)) return files
            }
        } else {
            while (entries.hasMoreElements()) {
                val entry = entries.nextElement() as ZipEntry
                val entryName = entry.name.replace("\\", "/")
                if (entryName.contains("../")) {
                    Log.e("ZipUtils", "entryName: $entryName is dangerous!")
                    continue
                }
                if (entryName.contains(keyword!!)) {
                    if (!unzipChildFile(destDir, files, zip, entry, entryName)) return files
                }
            }
        }
    }
    return files
}

@Throws(IOException::class)
private fun unzipChildFile(
    destDir: File,
    files: MutableList<File?>,
    zip: ZipFile,
    entry: ZipEntry,
    name: String
): Boolean {
    val file = File(destDir, name)
    files.add(file)
    if (entry.isDirectory) {
        return file.createOrExistsDirExt()
    } else {
        if (!file.createOrExistsFileExt()) return false
        var inputStream: InputStream? = null
        var out: OutputStream? = null
        try {
            inputStream = BufferedInputStream(zip.getInputStream(entry))
            out = BufferedOutputStream(FileOutputStream(file))
            val buffer = ByteArray(BUFFER_LEN)
            var len: Int
            while (inputStream.read(buffer).also { len = it } != -1) {
                out.write(buffer, 0, len)
            }
        } finally {
            inputStream?.close()
            out?.close()
        }
    }
    return true
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the files' path in ZIP file.
 */
@Throws(IOException::class)
fun String?.getFilesPathExt(): List<String?>? {
    return this.getFileByPathExt().getFilesPathExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the files' path in ZIP file.
 *  The ZIP file.
 */
@Throws(IOException::class)
fun File?.getFilesPathExt(): List<String>? {
    if (this == null) return null
    val paths: MutableList<String> = ArrayList()
    val zip = ZipFile(this)
    val entries: Enumeration<*> = zip.entries()
    while (entries.hasMoreElements()) {
        val entryName = (entries.nextElement() as ZipEntry).name.replace("\\", "/")
        if (entryName.contains("../")) {
            Log.e("ZipUtils", "entryName: $entryName is dangerous!")
            paths.add(entryName)
        } else {
            paths.add(entryName)
        }
    }
    zip.close()
    return paths
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe Return the files' comment in ZIP file.
 *  The path of ZIP file.
 */
@Throws(IOException::class)
fun String?.getCommentsExt(): List<String?>? {
    return this.getFileByPathExt().getCommentsExt()
}

/**
 *  @author: long
 *  @email spielberggao@gmail.com
 *  @describe  Return the files' comment in ZIP file.
 */
@Throws(IOException::class)
fun File?.getCommentsExt(): List<String>? {
    if (this == null) return null
    val comments: MutableList<String> = ArrayList()
    val zip = ZipFile(this)
    val entries: Enumeration<*> = zip.entries()
    while (entries.hasMoreElements()) {
        val entry = entries.nextElement() as ZipEntry
        comments.add(entry.comment)
    }
    zip.close()
    return comments
}