package com.pmm.ui.ktx

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.pmm.ui.types.FILE
import java.io.File


private val SCHEME = "package"

//进入拨号键面
fun Context.launchCallPage(phoneNumber: String) {
    //用intent启动拨打电话
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    startActivity(intent)
}

//直接拨打电话
fun Context.callDirect(phoneNumber: String) {
    //用intent启动拨打电话
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("call:$phoneNumber"))
    startActivity(intent)
}


//app信息界面 -- 修改权限  --修改通知开关
fun Context.openAppInfoPage(targetPackageName: String = packageName) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts(SCHEME, targetPackageName, null)
    intent.data = uri
    startActivity(intent)
}

//系统app列表
fun Context.openAppList() {
    val intent = Intent(Settings.ACTION_APPLICATION_SETTINGS)
    startActivity(intent)
}

//系统浏览器-打开网页
fun Context.openWebSite(url: String) {
    val intent = Intent()
    intent.action = "android.intent.action.VIEW"
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val content_url = Uri.parse(url)
    intent.data = content_url
    startActivity(intent)
}

//打开微信
fun Context.openWeiXin() {
    try {
        val intent = Intent(Intent.ACTION_MAIN)
        val cmp = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI")
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.component = cmp
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        toast("检查到您手机没有安装该APP，请安装后使用该功能")
    }

}

//打开相对应包名的app
fun Context.openAPP(appPackageName: String) {
    try {
        val intent = packageManager.getLaunchIntentForPackage(appPackageName)
        startActivity(intent)
    } catch (e: Exception) {
        toast("检查到您手机没有安装该APP，请安装后使用该功能")
    }

}


//打开市场-评价
fun Context.openMark(packageName: String = this.packageName) {
    try {
        val uri = Uri.parse("market://details?id=$packageName")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        toast("检测不到已安装的应用市场")
    }

}

//进入系统的通知界面  4.4一下没有->到设置界面
@Deprecated("待测试")
fun Context.openNotificationPage() {
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val intent = Intent()
        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS";
        intent.putExtra("app_package", this.getPackageName());
        intent.putExtra("app_uid", this.applicationInfo.uid);
        startActivity(intent)
    } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.parse("package:" + this.packageName)
        startActivity(intent)
    }
}

/**
 * 其他应用-打开PDF
 */
fun Context.openPdfByOthers(file: File) {

    try {
        val intent = Intent("android.intent.action.VIEW")
        intent.addCategory("android.intent.category.DEFAULT")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val uri = this.getUri4File(file)
        intent.setDataAndType(uri, "application/pdf")
        startActivity(intent)
    } catch (e: Exception) {
        toast("请安装PDF阅读软件")
    }
}

/**`
 * 本地打开应用
 */
fun Context.openFileByLocal(file: File, fileType: FILE) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        val uri = this.getUri4File(file)
        intent.setDataAndType(uri, fileType.uriType)

        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        toast("请安装可以打开此文件的App")
    }
}


/**
 * 返回桌面
 */
fun Context.backHomeTable() {
    val home = Intent(Intent.ACTION_MAIN)
    home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    home.addCategory(Intent.CATEGORY_HOME)
    startActivity(home)
}


