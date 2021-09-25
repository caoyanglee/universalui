package com.pmm.ui.ktx

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.Display
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.pmm.ui.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


/**
 * Author:你需要一台永动机
 * Date:2019-05-17 11:51
 * Description:
 */

/**
 * 吐司通知
 * @param message 吐司内容
 * @param forceLongShow 强制长显示
 */
fun Context?.toast(message: CharSequence, forceLongShow: Boolean = false) {
    try {
        if (message.isBlank() || this == null) return//过滤
        val duration = if (forceLongShow || message.length > 30) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        Toast.makeText(this, message, duration).show()
    } catch (e: Exception) {
        //doNothing
    }
}

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
    val context = this
    var result = 0
    val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resId > 0) {
        result = context.resources.getDimensionPixelOffset(resId)
    }
    if (result <= 0) {
        result = context.resources.getDimensionPixelOffset(R.dimen.dimen_24dp)
    }
    return result
}

//Navigation的高度
fun Activity.getNavigationBarHeight(): Int {
    val activity = this
    val display: Display = activity.windowManager.defaultDisplay
    val size = Point()
    val realSize = Point()
    display.getSize(size)
    display.getRealSize(realSize)
    val resources: Resources = activity.resources
    val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    val height: Int = resources.getDimensionPixelSize(resourceId)
    //超出系统默认的导航栏高度以上，则认为存在虚拟导航
    return if (realSize.y - size.y > height - 10) {
        height
    } else 0
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
fun Context.getScreenContentHeight(): Int {
    val ws = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = ws.defaultDisplay
    val outSize = Point()
    display.getSize(outSize)
    return outSize.y
}


/**
 * 复制文本内容
 * @param content 如果为空，相当于把当前剪贴板置空
 */
inline fun Context.setClipContent(content: String?, finish: () -> Unit = {}) {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, content))
    finish.invoke()
}

/**
 * 粘贴文本内容
 * android Q的规定如下
 * 只有默认输入法(IME)或者是目前处于焦点的应用, 才能访问到剪贴板数据.
 * 而在onCreate或者onResume方法时，View可能还处于申请获取焦点状态，导致获取不到剪切板数据，所以当所有View都绘制完毕后，就可以获取到剪切板数据了。
 */
fun Context.getClipContent(): String? {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    if (clipboardManager.hasPrimaryClip()) {
        return clipboardManager.primaryClip?.getItemAt(0)?.text?.toString()
    }
    return null
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
fun Context.getAppName(): String {
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

/**
 * 获取drawable
 */
fun Context.getDrawablePro(@DrawableRes id: Int): Drawable? {
    val drawable = ContextCompat.getDrawable(this, id)
    drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight) //设置边界
    return drawable
}

/**
 * 删除Glide缓存
 */
fun Context.clearGlideCache() {
    val that = this
    //glide
    Glide.get(this).clearMemory()//必须要主线程内执行
    MainScope().launch(Dispatchers.Default) {
        Glide.get(that).clearDiskCache()//必须在子线程内执行
    }
}

//检查当前系统是否已开启暗黑模式
fun Context.isDarkMode(): Boolean {
    val mode: Int = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return mode == Configuration.UI_MODE_NIGHT_YES
}

//获取attr的颜色
@CheckResult
fun Context.resolveColor(
    @AttrRes attr: Int,
    fallback: (() -> Int)? = null,
): Int {
    val a = theme.obtainStyledAttributes(intArrayOf(attr))
    try {
        val result = a.getColor(0, 0)
        if (result == 0 && fallback != null) {
            return fallback()
        }
        return result
    } finally {
        a.recycle()
    }
}

//获取attr的图片
@CheckResult
fun Context.resolveDrawable(
    @AttrRes attr: Int,
    fallback: (() -> Drawable?)? = null,
): Drawable? {
    val a = theme.obtainStyledAttributes(intArrayOf(attr))
    try {
        val result = a.getDrawable(0)
        if (result != null && fallback != null) {
            return fallback()
        }
        return result
    } finally {
        a.recycle()
    }
}

//获取图片的染色图
@CheckResult
fun Context.drawable(
    @DrawableRes drawable: Int,
    @ColorInt tint: Int? = null,
): Drawable {
    val result = ContextCompat.getDrawable(this, drawable)!!
    if (tint != null) {
        return DrawableCompat.wrap(result)
            .apply { DrawableCompat.setTint(this, tint) }
    }
    return result
}

/**
 * 增加通知栏的频道 通用库更新后可删除
 * @param importance NotificationManager.IMPORTANCE_LOW
 */
fun Context.createNotificationChannel(
    channelId: String,
    channelName: String,
    channelDesc: String = "",
    importance: Int = 0,
    enableVibration: Boolean = true,
    lightColor: Int = Color.GREEN,
    showBadge: Boolean = false,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // 通知渠道的id
        // 用户可以看到的通知渠道的名字.
        val name = channelName
        // 用户可以看到的通知渠道的描述
        val description = channelDesc
        val mChannel = NotificationChannel(channelId, name, importance)
        // 配置通知渠道的属性
        mChannel.description = description
        // 设置通知出现时的闪灯（如果 android 设备支持的话）
        mChannel.enableLights(true)
        mChannel.lightColor = lightColor
        // 设置通知出现时的震动（如果 android 设备支持的话）
        mChannel.enableVibration(enableVibration)
        mChannel.setShowBadge(showBadge)
        //mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        //最后在NotificationManager中创建该通知渠道
        mNotificationManager.createNotificationChannel(mChannel)
    }
}

/**
 * 是否是竖屏
 */
fun Context.isPortrait() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

/**
 * 是否是横屏
 */
fun Context.isLandScape() =
    resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

/**
 * 显示华为手机的角标
 */
private var mIsSupportedBade = true //是否支持华为的角标
fun Context.showHuaWeiBadge(num: Int) {
    if (mIsSupportedBade) {
        try {
            val bundle = Bundle()
            bundle.putString("package", "com.pmm.remember")
            bundle.putString("class", "com.pmm.remember.ui.splash.SplashAy")
            bundle.putInt("badgenumber", num)
            this.contentResolver.call(
                Uri.parse("content://com.huawei.android.launcher.settings/badge/"),
                "change_badge",
                null,
                bundle
            )
        } catch (e: Exception) {
            mIsSupportedBade = false //此处为是否显示角标
        }
    }
}