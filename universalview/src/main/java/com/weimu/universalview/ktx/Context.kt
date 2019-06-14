package com.weimu.universalview.ktx

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.ViewConfiguration
import android.view.WindowManager
import java.lang.reflect.Field

/**
 * Author:你需要一台永动机
 * Date:2019-05-17 11:51
 * Description:
 */

//获取颜色
fun Context.getColorPro(colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

//根据手机的分辨率从 dp 的单位 转成为 px(像素)
fun Context.dip2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

//根据手机的分辨率从 px(像素) 的单位 转成为 dp
fun Context.px2dip(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

//字体大小设置
fun Context.sp2px(spValue: Float): Int {
    val fontScale = resources.displayMetrics.density
    return (spValue * fontScale + 0.5f).toInt()
}


// 获取状态栏/通知栏的高度
fun Context.getStatusBarHeight(): Int {
    var c: Class<*>? = null
    var obj: Any? = null
    var field: Field? = null
    var x = 0
    var sbar = 0
    try {
        c = Class.forName("com.android.internal.R\$dimen")
        obj = c!!.newInstance()
        field = c.getField("status_bar_height")
        x = Integer.parseInt(field!!.get(obj).toString())
        sbar = resources.getDimensionPixelSize(x)
    } catch (e1: Exception) {
        e1.printStackTrace()
    }
    return sbar
}

//Navigation是否显示
fun Context.isNavigationBarShow(): Boolean {
    val ws = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        val display = ws.defaultDisplay
        val size = Point()
        val realSize = Point()
        display.getSize(size)
        display.getRealSize(realSize)
        return realSize.y != size.y
    } else {
        val menu = ViewConfiguration.get(this).hasPermanentMenuKey()
        val back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
        return !(menu || back)
    }
}

//Navigation的高度
fun Context.getNavigationBarHeight(): Int {
    if (!isNavigationBarShow()) return 0
    val resources = resources
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    //获取NavigationBar的高度
    return resources.getDimensionPixelSize(resourceId)
}

//获取屏幕宽度
fun Context.getScreenWidth(): Int {
    val ws = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = ws.defaultDisplay
    val outSize = Point()
    display.getSize(outSize)
    return outSize.x
}

//获取屏幕内容高度
fun Context.getScreeContentHeight(): Int {
    val ws = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = ws.defaultDisplay
    val outSize = Point()
    display.getSize(outSize)
    return outSize.y
}

//屏幕高度=显示的内容+navigation的高度
fun Context.getScreenHeight(): Int {
    return getScreeContentHeight() + getNavigationBarHeight()
}

//复制文本内容
inline fun Context.copyContent(content: String, f: () -> Unit = {}) {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboardManager.primaryClip = ClipData.newPlainText(null, "${content}")
    f.invoke()
}

//粘贴文本内容
fun Context.pasteContent(): String {
    var content: String = ""
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    if (clipboardManager.hasPrimaryClip()) {
        content = clipboardManager.primaryClip.getItemAt(0).text as String
    }
    return content
}

//获取网络信息
fun Context.getNetWorkInfo(): NetworkInfo? {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = cm.activeNetworkInfo
    if (networkInfo != null && networkInfo.isConnected) {
        return networkInfo
    }
    return null
}

//是否连接Wifi
fun Context.isConnectWifi(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = cm.activeNetworkInfo
    if (networkInfo != null && networkInfo.isConnected) {
        val type = networkInfo.type
        if (type == ConnectivityManager.TYPE_WIFI) {
            return true
        }
    }
    return false
}

/**
 * 判断通知开关是否打开
 */
fun Context.isNotificationEnabled(): Boolean {
    return NotificationManagerCompat.from(this).areNotificationsEnabled()
}

/**
 * 获取字体样式
 * @param assetPath xxx.tf
 */
fun Context.getTypeFaceFromAssets(assetPath: String): Typeface? {
    return Typeface.createFromAsset(this.assets, assetPath)
}

/**
 * 获取app的图片
 */
fun Context.getAppIcon(): Int {
    val pm: PackageManager = packageManager
    try {
        val info = pm.getApplicationInfo(this.packageName, 0)
        return info.icon
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return -1
}

/**
 * 获取应用的名字
 */
fun Context.getAppName(): String? {
    val pm: PackageManager = packageManager
    try {
        val info = pm.getApplicationInfo(this.packageName, 0)
        return info.loadLabel(pm).toString()
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return ""
}

/**
 * 打开Activity
 */
inline fun <reified T : Activity> Context.openActivity(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) intent.putExtras(bundle)
    startActivity(intent)
}

/**
 * 获取系统的水波纹效果 有边界
 */
fun Context.getRipple(): Drawable? {
    val typedValue = TypedValue()
    val attribute = intArrayOf(android.R.attr.selectableItemBackground)
    val typedArray = this.theme.obtainStyledAttributes(typedValue.resourceId, attribute)
    val drawable = typedArray.getDrawable(0)
    typedArray.recycle()
    return drawable
}

/**
 * 获取系统的水波纹效果 无边界
 */
fun Context.getRippleBorderLess(): Drawable? {
    val typedValue = TypedValue()
    val attribute = intArrayOf(android.R.attr.selectableItemBackgroundBorderless)
    val typedArray = this.theme.obtainStyledAttributes(typedValue.resourceId, attribute)
    val drawable = typedArray.getDrawable(0)
    typedArray.recycle()
    return drawable
}


