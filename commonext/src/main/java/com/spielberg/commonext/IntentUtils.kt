package com.spielberg.commonext

import android.Manifest.permission
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.annotation.NonNull
import androidx.annotation.RequiresPermission
import androidx.core.content.FileProvider
import java.io.File
import java.util.*


/**
 * Return whether the intent is available.
 *
 * @param intent The intent.
 * @return `true`: yes<br></br>`false`: no
 */
fun Intent.isIntentAvailable(): Boolean {
    val context = getApplicationByReflect() ?: return false
    return context.packageManager.queryIntentActivities(
        this,
        PackageManager.MATCH_DEFAULT_ONLY
    ).size > 0
}

/**
 * Return the intent of install app.
 *
 * Target APIs greater than 25 must hold
 * `<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />`
 *
 * @param filePath The path of file.
 * @return the intent of install app
 */
fun String?.getInstallAppIntent(): Intent? {
    return this.getFileByPathExt().getInstallAppIntent()
}

/**
 * Return the intent of install app.
 *
 * Target APIs greater than 25 must hold
 * `<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />`
 *
 * @param file The file.
 * @return the intent of install app
 */
fun File?.getInstallAppIntent(): Intent? {
    if (!this.isFileExists()) return null
    val context = getApplicationByReflect() ?: return null
    val uri: Uri
    uri = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        Uri.fromFile(this)
    } else {
        val authority = "${context.packageName}.utilcode.provider"
        FileProvider.getUriForFile(context, authority, this!!)
    }
    return uri?.getInstallAppIntent()
}

/**
 * Return the intent of install app.
 *
 * Target APIs greater than 25 must hold
 * `<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />`
 *
 * @param uri The uri.
 * @return the intent of install app
 */
fun Uri?.getInstallAppIntent(): Intent? {
    if (this == null) return null
    val intent = Intent(Intent.ACTION_VIEW)
    val type = "application/vnd.android.package-archive"
    intent.setDataAndType(this, type)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }
    return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}

/**
 * Return the intent of uninstall app.
 *
 * Target APIs greater than 25 must hold
 * Must hold `<uses-permission android:name="android.permission.REQUEST_DELETE_PACKAGES" />`
 *
 * @param pkgName The name of the package.
 * @return the intent of uninstall app
 */
fun String.getUninstallAppIntent(): Intent? {
    val intent = Intent(Intent.ACTION_DELETE)
    intent.data = Uri.parse("package:$this")
    return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}

/**
 * Return the intent of launch app.
 *
 * @param pkgName The name of the package.
 * @return the intent of launch app
 */
fun String?.getLaunchAppIntent(): Intent? {
    val launcherActivity: String? = this.getLauncherActivity()
    if (launcherActivity.isEmptyOrBlankExt()) return null
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_LAUNCHER)
    intent.setClassName(this!!, launcherActivity!!)
    return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}

/**
 * Return the intent of launch app details settings.
 *
 * @param pkgName The name of the package.
 * @return the intent of launch app details settings
 */
fun getLaunchAppDetailsSettingsIntent(pkgName: String): Intent? {
    return getLaunchAppDetailsSettingsIntent(pkgName, false)
}

/**
 * Return the intent of launch app details settings.
 *
 * @param pkgName The name of the package.
 * @return the intent of launch app details settings
 */
fun getLaunchAppDetailsSettingsIntent(pkgName: String, isNewTask: Boolean): Intent? {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:$pkgName")
    return getIntent(intent, isNewTask)
}

/**
 * Return the intent of share text.
 *
 * @param content The content.
 * @return the intent of share text
 */
fun getShareTextIntent(content: String?): Intent? {
    var intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, content)
    intent = Intent.createChooser(intent, "")
    return getIntent(intent, true)
}

/**
 * Return the intent of share image.
 *
 * @param imagePath The path of image.
 * @return the intent of share image
 */
fun getShareImageIntent(imagePath: String?): Intent? {
    return getShareTextImageIntent("", imagePath)
}

/**
 * Return the intent of share image.
 *
 * @param imageFile The file of image.
 * @return the intent of share image
 */
fun getShareImageIntent(imageFile: File?): Intent? {
    return getShareTextImageIntent("", imageFile)
}

/**
 * Return the intent of share image.
 *
 * @param imageUri The uri of image.
 * @return the intent of share image
 */
fun getShareImageIntent(imageUri: Uri?): Intent? {
    return getShareTextImageIntent("", imageUri)
}

/**
 * Return the intent of share image.
 *
 * @param content   The content.
 * @param imagePath The path of image.
 * @return the intent of share image
 */
fun getShareTextImageIntent(content: String, imagePath: String?): Intent? {
    return getShareTextImageIntent(content, imagePath.getFileByPathExt())
}

/**
 * Return the intent of share image.
 *
 * @param content   The content.
 * @param imageFile The file of image.
 * @return the intent of share image
 */
fun getShareTextImageIntent(content: String, imageFile: File?): Intent? {
    return getShareTextImageIntent(content, imageFile.file2UriExt())
}

/**
 * Return the intent of share image.
 *
 * @param content  The content.
 * @param imageUri The uri of image.
 * @return the intent of share image
 */
fun getShareTextImageIntent(content: String, imageUri: Uri?): Intent? {
    var intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_TEXT, content)
    intent.putExtra(Intent.EXTRA_STREAM, imageUri)
    intent.type = "image/*"
    intent = Intent.createChooser(intent, "")
    return getIntent(intent, true)
}

/**
 * Return the intent of share images.
 *
 * @param imagePaths The paths of images.
 * @return the intent of share images
 */
fun getShareImageIntent(imagePaths: LinkedList<String?>?): Intent? {
    return getShareTextImageIntent("", imagePaths)
}

/**
 * Return the intent of share images.
 *
 * @param images The files of images.
 * @return the intent of share images
 */
fun getShareImageIntent(images: List<File?>?): Intent? {
    return getShareTextImageIntent("", images)
}

/**
 * Return the intent of share images.
 *
 * @param uris The uris of image.
 * @return the intent of share image
 */
fun getShareImageIntent(uris: ArrayList<Uri?>?): Intent? {
    return getShareTextImageIntent("", uris)
}

/**
 * Return the intent of share images.
 *
 * @param content    The content.
 * @param imagePaths The paths of images.
 * @return the intent of share images
 */
fun getShareTextImageIntent(
    content: String,
    imagePaths: LinkedList<String?>?
): Intent? {
    val files: MutableList<File?> = ArrayList()
    if (imagePaths != null) {
        for (imagePath in imagePaths) {
            val file: File? = imagePath.getFileByPathExt()
            if (file != null) {
                files.add(file)
            }
        }
    }
    return getShareTextImageIntent(content, files)
}

/**
 * Return the intent of share images.
 *
 * @param content The content.
 * @param images  The files of images.
 * @return the intent of share images
 */
fun getShareTextImageIntent(content: String?, images: List<File?>?): Intent? {
    val uris = ArrayList<Uri?>()
    if (images != null) {
        for (image in images) {
            val uri: Uri? = image?.file2UriExt()
            if (uri != null) {
                uris.add(uri)
            }
        }
    }
    return getShareTextImageIntent(content, uris)
}

/**
 * Return the intent of share images.
 *
 * @param content The content.
 * @param uris    The uris of image.
 * @return the intent of share image
 */
fun getShareTextImageIntent(content: String?, uris: ArrayList<Uri?>?): Intent? {
    var intent = Intent(Intent.ACTION_SEND_MULTIPLE)
    intent.putExtra(Intent.EXTRA_TEXT, content)
    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
    intent.type = "image/*"
    intent = Intent.createChooser(intent, "")
    return getIntent(intent, true)
}

/**
 * Return the intent of component.
 *
 * @param pkgName   The name of the package.
 * @param className The name of class.
 * @return the intent of component
 */
fun getComponentIntent(pkgName: String?, className: String?): Intent? {
    return getComponentIntent(pkgName, className, null, false)
}

/**
 * Return the intent of component.
 *
 * @param pkgName   The name of the package.
 * @param className The name of class.
 * @param isNewTask True to add flag of new task, false otherwise.
 * @return the intent of component
 */
fun getComponentIntent(
    pkgName: String?,
    className: String?,
    isNewTask: Boolean
): Intent? {
    return getComponentIntent(pkgName, className, null, isNewTask)
}

/**
 * Return the intent of component.
 *
 * @param pkgName   The name of the package.
 * @param className The name of class.
 * @param bundle    The Bundle of extras to add to this intent.
 * @return the intent of component
 */
fun getComponentIntent(
    pkgName: String?,
    className: String?,
    bundle: Bundle?
): Intent? {
    return getComponentIntent(pkgName, className, bundle, false)
}

/**
 * Return the intent of component.
 *
 * @param pkgName   The name of the package.
 * @param className The name of class.
 * @param bundle    The Bundle of extras to add to this intent.
 * @param isNewTask True to add flag of new task, false otherwise.
 * @return the intent of component
 */
fun getComponentIntent(
    pkgName: String?,
    className: String?,
    bundle: Bundle?,
    isNewTask: Boolean
): Intent? {
    val intent = Intent()
    if (bundle != null) intent.putExtras(bundle)
    val cn = ComponentName(pkgName!!, className!!)
    intent.component = cn
    return getIntent(intent, isNewTask)
}

/**
 * Return the intent of shutdown.
 *
 * Requires root permission
 * or hold `android:sharedUserId="android.uid.system"`,
 * `<uses-permission android:name="android.permission.SHUTDOWN" />`
 * in manifest.
 *
 * @return the intent of shutdown
 */
fun getShutdownIntent(): Intent? {
    val intent: Intent
    intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Intent(Intent.ACTION_SHUTDOWN)
    } else {
        Intent("com.android.internal.intent.action.REQUEST_SHUTDOWN")
    }
    intent.putExtra("android.intent.extra.KEY_CONFIRM", false)
    return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}

/**
 * Return the intent of dial.
 *
 * @param phoneNumber The phone number.
 * @return the intent of dial
 */
fun getDialIntent(phoneNumber: String): Intent? {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    return getIntent(intent, true)
}

/**
 * Return the intent of call.
 *
 * Must hold `<uses-permission android:name="android.permission.CALL_PHONE" />`
 *
 * @param phoneNumber The phone number.
 * @return the intent of call
 */
@RequiresPermission(permission.CALL_PHONE)
fun getCallIntent(phoneNumber: String): Intent? {
    val intent = Intent("android.intent.action.CALL", Uri.parse("tel:$phoneNumber"))
    return getIntent(intent, true)
}

/**
 * Return the intent of send SMS.
 *
 * @param phoneNumber The phone number.
 * @param content     The content of SMS.
 * @return the intent of send SMS
 */
fun getSendSmsIntent(phoneNumber: String, content: String?): Intent? {
    val uri = Uri.parse("smsto:$phoneNumber")
    val intent = Intent(Intent.ACTION_SENDTO, uri)
    intent.putExtra("sms_body", content)
    return getIntent(intent, true)
}

/**
 * Return the intent of capture.
 *
 * @param outUri The uri of output.
 * @return the intent of capture
 */
fun getCaptureIntent(outUri: Uri?): Intent? {
    return getCaptureIntent(outUri, false)
}

/**
 * Return the intent of capture.
 *
 * @param outUri    The uri of output.
 * @param isNewTask True to add flag of new task, false otherwise.
 * @return the intent of capture
 */
fun getCaptureIntent(outUri: Uri?, isNewTask: Boolean): Intent? {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    return getIntent(intent, isNewTask)
}

private fun getIntent(intent: Intent, isNewTask: Boolean): Intent? {
    return if (isNewTask) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) else intent
}

/**
 * Return the name of launcher activity.
 *
 * @param pkg The name of the package.
 * @return the name of launcher activity
 */
fun String?.getLauncherActivity( ): String? {
    if (this.isEmptyOrBlankExt()) return ""
    val intent = Intent(Intent.ACTION_MAIN, null)
    intent.addCategory(Intent.CATEGORY_LAUNCHER)
    intent.setPackage(this)
    val pm: PackageManager? = getApplicationByReflect()?.packageManager
    val info = pm?.queryIntentActivities(intent, 0)
    return if (info == null || info.size == 0) {
        ""
    } else info[0].activityInfo.name
}