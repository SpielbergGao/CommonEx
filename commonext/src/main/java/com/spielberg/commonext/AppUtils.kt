package com.spielberg.commonext

import android.app.Activity
import android.app.ActivityManager
import android.app.ActivityManager.RunningTaskInfo
import android.content.Context
import android.content.Intent
import android.content.pm.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Process
import android.util.Log
import java.io.*
import java.util.*
import kotlin.system.exitProcess


/**
 * Install the app.
 *
 * Target APIs greater than 25 must hold
 * `<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />`
 *
 * @param filePath The path of file.
 */
fun String?.installApp() {
    this.getFileByPathExt()?.installApp()
}

/**
 * Install the app.
 *
 * Target APIs greater than 25 must hold
 * `<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />`
 *
 * @param file The file.
 */
fun File?.installApp() {
    val installAppIntent: Intent = this.getInstallAppIntent() ?: return
    getApplicationByReflect()?.startActivity(installAppIntent)
}

/**
 * Install the app.
 *
 * Target APIs greater than 25 must hold
 * `<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />`
 *
 * @param uri The uri.
 */
fun Uri?.installApp() {
    val installAppIntent: Intent = this?.getInstallAppIntent() ?: return
    getApplicationByReflect()?.startActivity(installAppIntent)
}

/**
 * Uninstall the app.
 *
 * Target APIs greater than 25 must hold
 * Must hold `<uses-permission android:name="android.permission.REQUEST_DELETE_PACKAGES" />`
 *
 * @param packageName The name of the package.
 */
fun String?.uninstallApp() {
    if (this.isEmptyOrBlankExt()) return
    getApplicationByReflect()?.startActivity(this!!.getUninstallAppIntent())
}

/**
 * Return whether the app is installed.
 *
 * @param pkgName The name of the package.
 * @return `true`: yes<br></br>`false`: no
 */
fun String?.isAppInstalled(): Boolean {
    if (this.isEmptyOrBlankExt()) return false
    val pm: PackageManager? = getApplicationByReflect()?.packageManager
    return try {
        pm?.getApplicationInfo(this!!, 0)?.enabled ?: false
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        false
    }
}

/**
 * Return whether it is a debug application.
 *
 * @return `true`: yes<br></br>`false`: no
 */
fun isAppDebug(): Boolean {
    return getApplicationByReflect()?.packageName?.isAppDebug() ?: false
}

/**
 * Return whether it is a debug application.
 *
 * @param packageName The name of the package.
 * @return `true`: yes<br></br>`false`: no
 */
fun String?.isAppDebug(): Boolean {
    if (this.isEmptyOrBlankExt()) return false
    val ai: ApplicationInfo? = getApplicationByReflect()?.applicationInfo
    return ai != null && ai.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
}

/**
 * Return whether it is a system application.
 *
 * @return `true`: yes<br></br>`false`: no
 */
fun isAppSystem(): Boolean {
    return getApplicationByReflect()?.packageName?.isAppSystem() ?: false
}

/**
 * Return whether it is a system application.
 *
 * @param packageName The name of the package.
 * @return `true`: yes<br></br>`false`: no
 */
fun String?.isAppSystem(): Boolean {
    if (this.isEmptyOrBlankExt()) return false
    return try {
        val pm: PackageManager? = getApplicationByReflect()?.packageManager
        val ai: ApplicationInfo? = pm?.getApplicationInfo(this!!, 0)
        ai != null && ai.flags and ApplicationInfo.FLAG_SYSTEM != 0
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        false
    }
}

/**
 * Return whether application is foreground.
 *
 * Target APIs greater than 21 must hold
 * `<uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />`
 *
 * @param pkgName The name of the package.
 * @return `true`: yes<br></br>`false`: no
 */
fun String?.isAppForeground(): Boolean {
    return !this.isEmptyOrBlankExt() && this == getForegroundProcessName()
}

/**
 * Return whether application is running.
 *
 * @param pkgName The name of the package.
 * @return `true`: yes<br></br>`false`: no
 */
fun String?.isAppRunning(): Boolean {
    if (this.isEmptyOrBlankExt()) return false
    val context = getApplicationByReflect() ?: return false
    val ai: ApplicationInfo = context.applicationInfo
    val uid = ai.uid
    val am: ActivityManager? = context.activityManager
    if (am != null) {
        val taskInfo = am.getRunningTasks(Int.MAX_VALUE)
        if (!taskInfo.isNullOrEmpty()) {
            for (aInfo: RunningTaskInfo in taskInfo) {
                if (aInfo.baseActivity != null && this == aInfo.baseActivity!!.packageName) {
                    return true
                }
            }
        }
        val serviceInfo = am.getRunningServices(Int.MAX_VALUE)
        if (!serviceInfo.isNullOrEmpty()) {
            for (aInfo: ActivityManager.RunningServiceInfo in serviceInfo) {
                if (uid == aInfo.uid) {
                    return true
                }
            }
        }
    }
    return false
}

/**
 * Launch the application.
 *
 * @param packageName The name of the package.
 */
fun String?.launchApp() {
    if (this.isEmptyOrBlankExt()) return
    val launchAppIntent: Intent? = this.getLaunchAppIntent()
    if (launchAppIntent == null) {
        Log.e("AppUtils", "Didn't exist launcher activity.")
        return
    }
    getApplicationByReflect()?.startActivity(launchAppIntent)
}

/**
 * Relaunch the application.
 */
fun relaunchApp() {
    relaunchApp(false)
}

/**
 * Relaunch the application.
 *
 * @param isKillProcess True to kill the process, false otherwise.
 */
fun relaunchApp(isKillProcess: Boolean) {
    val intent: Intent? = getLaunchAppIntent(getApplicationByReflect()?.packageName)
    if (intent == null) {
        Log.e("AppUtils", "Didn't exist launcher activity.")
        return
    }
    intent.addFlags(
        Intent.FLAG_ACTIVITY_NEW_TASK
                or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
    )
    getApplicationByReflect()?.startActivity(intent)
    if (!isKillProcess) return
    Process.killProcess(Process.myPid())
    exitProcess(0)
}

/**
 * Launch the application's details settings.
 */
fun launchAppDetailsSettings() {
    launchAppDetailsSettings(getApplicationByReflect()?.packageName)
}

/**
 * Launch the application's details settings.
 *
 * @param pkgName The name of the package.
 */
fun launchAppDetailsSettings(pkgName: String?) {
    if (pkgName.isEmptyOrBlankExt()) return
    val intent: Intent = getLaunchAppDetailsSettingsIntent(pkgName, true) ?: return
    if (!isIntentAvailable(intent)) return
    getApplicationByReflect()?.startActivity(intent)
}

/**
 * Launch the application's details settings.
 *
 * @param activity    The activity.
 * @param requestCode The requestCode.
 */
fun launchAppDetailsSettings(activity: Activity?, requestCode: Int) {
    launchAppDetailsSettings(activity, requestCode, getApplicationByReflect()?.packageName)
}

/**
 * Launch the application's details settings.
 *
 * @param activity    The activity.
 * @param requestCode The requestCode.
 * @param pkgName     The name of the package.
 */
fun launchAppDetailsSettings(activity: Activity?, requestCode: Int, pkgName: String?) {
    if (activity == null || pkgName.isEmptyOrBlankExt()) return
    val intent: Intent = getLaunchAppDetailsSettingsIntent(pkgName, false) ?: return
    if (!intent.isIntentAvailable()) return
    activity.startActivityForResult(intent, requestCode)
}

/**
 * Return the application's icon.
 *
 * @return the application's icon
 */
fun getAppIcon(): Drawable? {
    return getApplicationByReflect()?.packageName?.getAppIcon()
}

/**
 * Return the application's icon.
 *
 * @param packageName The name of the package.
 * @return the application's icon
 */
fun String?.getAppIcon(): Drawable? {
    if (this.isEmptyOrBlankExt()) return null
    return try {
        val pm: PackageManager? = getApplicationByReflect()?.packageManager
        val pi = pm?.getPackageInfo(this!!, 0)
        pi?.applicationInfo?.loadIcon(pm)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }
}

/**
 * Return the application's icon resource identifier.
 *
 * @return the application's icon resource identifier
 */
fun getAppIconId(): Int {
    return getApplicationByReflect()?.packageName?.getAppIconId() ?: -1
}

/**
 * Return the application's icon resource identifier.
 *
 * @param packageName The name of the package.
 * @return the application's icon resource identifier
 */
fun String?.getAppIconId(): Int {
    if (this.isEmptyOrBlankExt()) return 0
    return try {
        val pm: PackageManager? = getApplicationByReflect()?.packageManager
        val pi = pm?.getPackageInfo(this!!, 0)
        pi?.applicationInfo?.icon ?: 0
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        0
    }
}

/**
 * Return the application's package name.
 *
 * @return the application's package name
 */
fun getAppPackageName(): String? {
    return getApplicationByReflect()?.packageName ?: ""
}

/**
 * Return the application's name.
 *
 * @return the application's name
 */
fun getAppName(): String? {
    return getApplicationByReflect()?.packageName?.getAppName() ?: ""
}

/**
 * Return the application's name.
 *
 * @param packageName The name of the package.
 * @return the application's name
 */
fun String?.getAppName(): String? {
    if (this.isEmptyOrBlankExt()) return ""
    return try {
        val pm: PackageManager? = getApplicationByReflect()?.packageManager
        val pi = pm?.getPackageInfo(this!!, 0)
        pi?.applicationInfo?.loadLabel(pm)?.toString()
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }
}

/**
 * Return the application's path.
 *
 * @return the application's path
 */
fun getAppPath(): String? {
    return getApplicationByReflect()?.packageName?.getAppPath() ?: ""
}

/**
 * Return the application's path.
 *
 * @param packageName The name of the package.
 * @return the application's path
 */
fun String?.getAppPath(): String? {
    if (this.isEmptyOrBlankExt()) return ""
    return try {
        val pm: PackageManager? = getApplicationByReflect()?.packageManager
        val pi = pm?.getPackageInfo(this!!, 0)
        pi?.applicationInfo?.sourceDir
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }
}

/**
 * Return the application's version name.
 *
 * @return the application's version name
 */
fun getAppVersionName(): String? {
    return getApplicationByReflect()?.packageName?.getAppVersionName() ?: ""
}

/**
 * Return the application's version name.
 *
 * @param packageName The name of the package.
 * @return the application's version name
 */
fun String?.getAppVersionName(): String? {
    if (this.isEmptyOrBlankExt()) return ""
    return try {
        val pm: PackageManager? = getApplicationByReflect()?.packageManager
        val pi = pm?.getPackageInfo(this!!, 0)
        pi?.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }
}

/**
 * Return the application's version code.
 *
 * @return the application's version code
 */
fun getAppVersionCode(): Int {
    return getApplicationByReflect()?.packageName?.getAppVersionCode() ?: -1
}

/**
 * Return the application's version code.
 *
 * @param packageName The name of the package.
 * @return the application's version code
 */
fun String?.getAppVersionCode(): Int {
    if (this.isEmptyOrBlankExt()) return -1
    return try {
        val pm: PackageManager? = getApplicationByReflect()?.packageManager
        val pi = pm?.getPackageInfo(this!!, 0)
        pi?.versionCode ?: -1
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        -1
    }
}

/**
 * Return the application's signature.
 *
 * @return the application's signature
 */
fun getAppSignatures(): Array<Signature>? {
    return getApplicationByReflect()?.packageName?.getAppSignatures() ?: emptyArray()
}

/**
 * Return the application's signature.
 *
 * @param packageName The name of the package.
 * @return the application's signature
 */
fun String?.getAppSignatures(): Array<Signature>? {
    if (this.isEmptyOrBlankExt()) return null
    try {
        val pm: PackageManager? = getApplicationByReflect()?.packageManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val pi = pm?.getPackageInfo(this!!, PackageManager.GET_SIGNING_CERTIFICATES)
                ?: return null
            val signingInfo = pi.signingInfo
            return if (signingInfo.hasMultipleSigners()) {
                signingInfo.apkContentsSigners
            } else {
                signingInfo.signingCertificateHistory
            }
        } else {
            val pi =
                pm?.getPackageInfo(this!!, PackageManager.GET_SIGNATURES) ?: return null
            return pi.signatures
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        return null
    }
}

/**
 * Return the application's signature.
 *
 * @param file The file.
 * @return the application's signature
 */
fun  File?.getAppSignatures(): Array<Signature?>? {
    if (this == null) return null
    val pm: PackageManager? = getApplicationByReflect()?.packageManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val pi =
            pm?.getPackageArchiveInfo(this.absolutePath, PackageManager.GET_SIGNING_CERTIFICATES)
                ?: return null
        val signingInfo = pi.signingInfo
        return if (signingInfo.hasMultipleSigners()) {
            signingInfo.apkContentsSigners
        } else {
            signingInfo.signingCertificateHistory
        }
    } else {
        val pi = pm?.getPackageArchiveInfo(this.absolutePath, PackageManager.GET_SIGNATURES)
            ?: return null
        return pi.signatures
    }
}

/**
 * Return the application's signature for SHA1 value.
 *
 * @return the application's signature for SHA1 value
 */
fun getAppSignaturesSHA1(): List<String?>? {
    return getApplicationByReflect()?.packageName?.getAppSignaturesSHA1() ?: emptyList()
}

/**
 * Return the application's signature for SHA1 value.
 *
 * @param packageName The name of the package.
 * @return the application's signature for SHA1 value
 */
fun String.getAppSignaturesSHA1(): List<String?>? {
    return getAppSignaturesHash(this, "SHA1")
}

/**
 * Return the application's signature for SHA256 value.
 *
 * @return the application's signature for SHA256 value
 */
fun getAppSignaturesSHA256(): List<String?>? {
    return getApplicationByReflect()?.packageName?.getAppSignaturesSHA256() ?: emptyList()
}

/**
 * Return the application's signature for SHA256 value.
 *
 * @param packageName The name of the package.
 * @return the application's signature for SHA256 value
 */
fun String.getAppSignaturesSHA256(): List<String?>? {
    return getAppSignaturesHash(this, "SHA256")
}

/**
 * Return the application's signature for MD5 value.
 *
 * @return the application's signature for MD5 value
 */
fun getAppSignaturesMD5(): List<String?>? {
    return getApplicationByReflect()?.packageName?.getAppSignaturesMD5() ?: emptyList()
}

/**
 * Return the application's signature for MD5 value.
 *
 * @param packageName The name of the package.
 * @return the application's signature for MD5 value
 */
fun String.getAppSignaturesMD5(): List<String?>? {
    return getAppSignaturesHash(this, "MD5")
}

/**
 * Return the application's user-ID.
 *
 * @return the application's signature for MD5 value
 */
fun getAppUid(): Int {
    return getApplicationByReflect()?.packageName?.getAppUid() ?: -1
}

/**
 * Return the application's user-ID.
 *
 * @param pkgName The name of the package.
 * @return the application's signature for MD5 value
 */
fun String.getAppUid(): Int {
    return try {
        getApplicationByReflect()?.packageManager?.getApplicationInfo(this, 0)?.uid ?: -1
    } catch (e: Exception) {
        e.printStackTrace()
        -1
    }
}

private fun getAppSignaturesHash(packageName: String, algorithm: String): List<String?>? {
    val result = ArrayList<String?>()
    if (packageName.isEmptyOrBlankExt()) return result
    val signatures = packageName.getAppSignatures()
    if (signatures == null || signatures.isEmpty()) return result
    for (signature: Signature in signatures) {
        val hash: String? =
            hashTemplate(signature.toByteArray(), algorithm).bytes2HexStringExt()?.replace(
                "(?<=[0-9A-F]{2})[0-9A-F]{2}",
                ":$0"
            )
        result.add(hash)
    }
    return result
}

/**
 * Return the application's information.
 *
 *  * name of package
 *  * icon
 *  * name
 *  * path of package
 *  * version name
 *  * version code
 *  * is system
 *
 *
 * @return the application's information
 */
fun getAppInfo(): AppInfo? {
    return getApplicationByReflect()?.packageName?.getAppInfo()
}

/**
 * Return the application's information.
 *
 *  * name of package
 *  * icon
 *  * name
 *  * path of package
 *  * version name
 *  * version code
 *  * is system
 *
 *
 * @param packageName The name of the package.
 * @return the application's information
 */
fun String?.getAppInfo(): AppInfo? {
    try {
        val pm: PackageManager = getApplicationByReflect()?.packageManager ?: return null
        return getBean(pm, pm.getPackageInfo((this)!!, 0))
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        return null
    }
}

/**
 * Return the applications' information.
 *
 * @return the applications' information
 */
fun getAppsInfo(): List<AppInfo>? {
    val list: MutableList<AppInfo> = ArrayList()
    val pm: PackageManager = getApplicationByReflect()?.packageManager ?: return list
    val installedPackages = pm.getInstalledPackages(0)
    for (pi: PackageInfo? in installedPackages) {
        val ai = getBean(pm, pi) ?: continue
        list.add(ai)
    }
    return list
}

/**
 * Return the application's package information.
 *
 * @return the application's package information
 */
fun File?.getApkInfo(): AppInfo? {
    return if ((this == null) || !this.isFile || !this.exists()) null else this.absolutePath.getApkInfo()
}

/**
 * Return the application's package information.
 *
 * @return the application's package information
 */
fun String?.getApkInfo(): AppInfo? {
    if (this.isEmptyOrBlankExt()) return null
    val pm: PackageManager = getApplicationByReflect()?.packageManager ?: return null
    val pi = pm.getPackageArchiveInfo((this)!!, 0) ?: return null
    val appInfo = pi.applicationInfo
    appInfo.sourceDir = this
    appInfo.publicSourceDir = this
    return getBean(pm, pi)
}

private fun getBean(pm: PackageManager, pi: PackageInfo?): AppInfo? {
    if (pi == null) return null
    val ai = pi.applicationInfo
    val packageName = pi.packageName
    val name = ai.loadLabel(pm).toString()
    val icon = ai.loadIcon(pm)
    val packagePath = ai.sourceDir
    val versionName = pi.versionName
    val versionCode = pi.versionCode
    val isSystem = (ApplicationInfo.FLAG_SYSTEM and ai.flags) != 0
    return AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem)
}

/**
 * The application's information.
 */
class AppInfo(
    packageName: String?, name: String?, icon: Drawable?, packagePath: String?,
    versionName: String?, versionCode: Int, isSystem: Boolean
) {
    var packageName: String? = null
    var name: String? = null
    var icon: Drawable? = null
    var packagePath: String? = null
    var versionName: String? = null
    var versionCode = 0
    var isSystem = false

    override fun toString(): String {
        return ("{" +
                "\n    pkg name: " + packageName +
                "\n    app icon: " + icon +
                "\n    app name: " + name +
                "\n    app path: " + packagePath +
                "\n    app v name: " + versionName +
                "\n    app v code: " + versionCode +
                "\n    is system: " + isSystem +
                "\n}")
    }

    init {
        this.name = name
        this.icon = icon
        this.packageName = packageName
        this.packagePath = packagePath
        this.versionName = versionName
        this.versionCode = versionCode
        this.isSystem = isSystem
    }
}
