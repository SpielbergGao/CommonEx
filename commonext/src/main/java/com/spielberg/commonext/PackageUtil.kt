package com.spielberg.commonext

import android.app.ActivityManager.RunningTaskInfo
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import java.io.File


/**
 * App installation location flags of android system
 */
const val APP_INSTALL_AUTO = 0
const val APP_INSTALL_INTERNAL = 1
const val APP_INSTALL_EXTERNAL = 2

/**
 * 调用系统安装应用
 */
fun install(context: Context, file: File?): Boolean {
    if (file == null || !file.exists() || !file.isFile) {
        return false
    }
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
    return true
}

/**
 * 调用系统卸载应用
 */
fun Context.uninstallApk(packageName: String) {
    val intent = Intent(Intent.ACTION_DELETE)
    val packageURI: Uri = Uri.parse("package:$packageName")
    intent.data = packageURI
    this.startActivity(intent)
}

/**
 * 打开已安装应用的详情
 */
fun Context.goToInstalledAppDetails(packageName: String?) {
    val intent = Intent()
    val sdkVersion = Build.VERSION.SDK_INT
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", packageName, null)
    } else {
        intent.action = Intent.ACTION_VIEW
        intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails")
        intent.putExtra(
            if (sdkVersion == Build.VERSION_CODES.FROYO) "pkg" else "com.android.settings.ApplicationPkgName",
            packageName
        )
    }
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    this.startActivity(intent)
}


/**
 * 获取指定程序信息
 */
fun Context.getTopRunningTask(): RunningTaskInfo? {
    try {
        // 得到当前正在运行的任务栈
        val runningTasks = this.activityManager?.getRunningTasks(1)
        // 得到前台显示的任务栈
        return runningTasks?.get(0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


fun Context.getAppVersionName(): String? {
    try {
        return this.packageManager?.getPackageInfo(this.packageName, 0)?.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return ""
}

fun Context.getAppVersionCode(): Int {
    try {
        return this.packageManager.getPackageInfo(this.packageName, 0)?.versionCode ?: 0
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return -1
}


/**
 * get app package info
 */
fun Context?.getAppPackageInfo(): PackageInfo? {
    try {
        return this?.packageManager?.getPackageInfo(this.packageName, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * whether context is system application
 */
fun isSystemApplication(context: Context?): Boolean {
    return context?.isSystemApplication(context.packageName) ?: false
}

/**
 * whether packageName is system application
 */
fun Context.isSystemApplication(packageName: String?): Boolean {
    val packageManager: PackageManager? = this.packageManager
    if (packageManager == null || packageName == null || packageName.isEmpty()) {
        return false
    }
    try {
        val app = packageManager.getApplicationInfo(packageName, 0)
        return app.flags and ApplicationInfo.FLAG_SYSTEM > 0
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}

/**
 * 获取已安装的全部应用信息
 */
fun getInsatalledPackages(context: Context): List<PackageInfo> {
    return context.packageManager.getInstalledPackages(0)
}

/**
 * 获取已安装的全部应用信息
 */
fun isInsatalled(context: Context, pkg: String?): Boolean {
    if (!pkg.isEmptyOrBlankExt()) {
        val list = getInsatalledPackages(context)
        if (list.isNotEmpty()) {
            for (pi in list) {
                if (pkg.equals(pi.packageName, ignoreCase = true)) {
                    return true
                }
            }
        }
    }
    return false
}

/**
 * 获取指定程序信息
 */
fun Context.getApplicationInfo(pkg: String): ApplicationInfo? {
    try {
        return this.packageManager.getApplicationInfo(pkg, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return null
}

/**
 * 获取指定程序信息
 */
fun Context.getPackageInfo(pkg: String): PackageInfo? {
    try {
        return this.packageManager.getPackageInfo(pkg, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return null
}

/**
 * 启动应用
 */
fun Context.startAppByPackageName(packageName: String): Boolean {
    return startAppByPackageName(packageName, null)
}

/**
 * 启动应用
 */
fun Context.startAppByPackageName(
    packageName: String,
    param: Map<String?, String?>?
): Boolean {
    var pi: PackageInfo? = null
    try {
        pi = this.packageManager.getPackageInfo(packageName, 0)
        val resolveIntent = Intent(Intent.ACTION_MAIN, null)
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            resolveIntent.setPackage(pi.packageName)
        }
        val apps: List<ResolveInfo>? = this.packageManager.queryIntentActivities(resolveIntent, 0)
        val ri = apps?.iterator()?.next()
        if (ri != null) {
            val packageName1 = ri.activityInfo.packageName
            val className = ri.activityInfo.name
            val intent = Intent(Intent.ACTION_MAIN)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            val cn = ComponentName(packageName1, className)
            intent.component = cn
            if (param != null) {
                for ((key, value) in param) {
                    intent.putExtra(key, value)
                }
            }
            this.startActivity(intent)
            return true
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(
            this.applicationContext, "启动失败",
            Toast.LENGTH_LONG
        ).show()
    }
    return false
}