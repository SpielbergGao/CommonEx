package com.spielberg.commonext

import android.app.ActivityManager
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
fun uninstallApk(context: Context, packageName: String) {
    val intent = Intent(Intent.ACTION_DELETE)
    val packageURI: Uri = Uri.parse("package:$packageName")
    intent.data = packageURI
    context.startActivity(intent)
}

/**
 * 打开已安装应用的详情
 */
fun goToInstalledAppDetails(context: Context, packageName: String?) {
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
    context.startActivity(intent)
}


/**
 * 获取指定程序信息
 */
fun getTopRunningTask(context: Context): RunningTaskInfo? {
    try {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        // 得到当前正在运行的任务栈
        val runningTasks = am.getRunningTasks(1)
        // 得到前台显示的任务栈
        return runningTasks[0]
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


fun getAppVersionName(context: Context): String? {
    try {
        val pm: PackageManager = context.packageManager
        val pi: PackageInfo = pm.getPackageInfo(context.packageName, 0)
        return pi.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return ""
}

fun getAppVersionCode(context: Context): Int {
    try {
        val pm: PackageManager = context.packageManager
        val pi: PackageInfo = pm.getPackageInfo(context.packageName, 0)
        return pi.versionCode
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return -1
}

/**
 * 获取当前系统安装应用的默认位置
 *
 * @return APP_INSTALL_AUTO or APP_INSTALL_INTERNAL or APP_INSTALL_EXTERNAL.
 */
fun getInstallLocation(): Int {
    val commandResult: ShellUtil.CommandResult = ShellUtil.execCommand(
        "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm get-install-location", false, true
    )
    if (commandResult.result === 0 && commandResult.responseMsg != null && commandResult.responseMsg.length() > 0) {
        try {
            return commandResult.responseMsg.substring(0, 1).toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }
    return APP_INSTALL_AUTO
}


/**
 * get app package info
 */
fun getAppPackageInfo(context: Context?): PackageInfo? {
    if (context != null) {
        val pm: PackageManager = context.packageManager
        if (pm != null) {
            var pi: PackageInfo
            try {
                return pm.getPackageInfo(context.packageName, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    return null
}

/**
 * whether context is system application
 */
fun isSystemApplication(context: Context?): Boolean {
    return if (context == null) {
        false
    } else isSystemApplication(context, context.packageName)
}

/**
 * whether packageName is system application
 */
fun isSystemApplication(context: Context, packageName: String?): Boolean {
    val packageManager: PackageManager = context.packageManager
    if (packageManager == null || packageName == null || packageName.length == 0) {
        return false
    }
    try {
        val app = packageManager.getApplicationInfo(packageName, 0)
        return app != null && app.flags and ApplicationInfo.FLAG_SYSTEM > 0
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
fun isInsatalled(context: Context, pkg: String): Boolean {
    if (!Check.isEmpty(pkg)) {
        val list = getInsatalledPackages(context)
        if (!Check.isEmpty(list)) {
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
fun getApplicationInfo(context: Context, pkg: String?): ApplicationInfo? {
    try {
        return context.packageManager.getApplicationInfo(pkg, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return null
}

/**
 * 获取指定程序信息
 */
fun getPackageInfo(context: Context, pkg: String?): PackageInfo? {
    try {
        return context.packageManager.getPackageInfo(pkg, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return null
}

/**
 * 启动应用
 */
fun startAppByPackageName(context: Context, packageName: String?): Boolean {
    return startAppByPackageName(context, packageName, null)
}

/**
 * 启动应用
 */
fun startAppByPackageName(
    context: Context,
    packageName: String?,
    param: Map<String?, String?>?
): Boolean {
    var pi: PackageInfo? = null
    try {
        pi = context.packageManager.getPackageInfo(packageName, 0)
        val resolveIntent = Intent(Intent.ACTION_MAIN, null)
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            resolveIntent.setPackage(pi.packageName)
        }
        val apps: List<ResolveInfo> =
            context.packageManager.queryIntentActivities(resolveIntent, 0)
        val ri = apps.iterator().next()
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
            context.startActivity(intent)
            return true
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(
            context.applicationContext, "启动失败",
            Toast.LENGTH_LONG
        ).show()
    }
    return false
}