package com.pmm.ui.core.toolbar


import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt

/**
 * 状态栏操作中心
 */
object StatusBarManager {

    private var statusLightBarColorV5 = Color.rgb(102, 102, 102)//5.0及其5.1亮色状态背景

    /**
     * 修改状态栏为全透明
     */
    fun setTransparencyBar(window: Window?) {
        window?.apply {
            this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            this.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * 修改状态栏 & 导航栏 透明
     */
    fun setStatusNavigationBarTransparent(window: Window?) {
        window?.apply {
            this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            this.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            this.navigationBarColor = Color.TRANSPARENT
            this.statusBarColor = Color.TRANSPARENT
        }
    }


    /**
     * 设置状态栏的背景颜色
     */
    fun setColor(window: Window?, @ColorInt color: Int) {
        window?.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                this.statusBarColor = color
            } else {
                this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                this.statusBarColor = color
            }
        }
    }

    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    fun setLightMode(window: Window?, isFullScreen: Boolean = false): Int {
        var result = 0
        if (window == null) return 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (isFullScreen)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            else
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isFullScreen)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            result = 3
        } else if (MIUISetStatusBarLightMode(window, true, isFullScreen)) {
            result = 1
        } else if (FlymeSetStatusBarLightMode(window, true, isFullScreen)) {
            result = 2
        } else {//5.x版本的手机不支持状态栏里黑色字体，所以状态栏的颜色自己看设置一个
            setColor(window, statusLightBarColorV5)
        }
        return result
    }


    /**
     * 状态栏黑色模式，文字白色文字、图标
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    fun setDarkMode(window: Window, isFullScreen: Boolean = false): Int {
        var result = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (isFullScreen) window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isFullScreen) window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            result = 3
        } else if (MIUISetStatusBarLightMode(window, false, isFullScreen)) {
            result = 1
        } else if (FlymeSetStatusBarLightMode(window, false, isFullScreen)) {
            result = 2
        }
        return result
    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private fun FlymeSetStatusBarLightMode(window: Window?, dark: Boolean, isFullScreen: Boolean): Boolean {
        var result = false
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                if (dark) {
                    value = value or bit
                } else {
                    value = value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
                darkFlag.isAccessible = false
                meizuFlags.isAccessible = false
                result = true
            } catch (e: Exception) {

            }

        }
        return result
    }

    /**
     * 需要MIUIV6以上
     *
     * @param activity
     * @param dark     是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private fun MIUISetStatusBarLightMode(window: Window?, dark: Boolean, isFullScreen: Boolean): Boolean {
        var result = false
        if (window != null) {
            val clazz = window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
                }
                result = true

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        if (isFullScreen)
                            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        else
                            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                    }
                }
            } catch (e: Exception) {

            }

        }
        return result
    }

    /**
     * 隐藏状态栏
     */
    fun hideStatusBar(window: Window) {
        val attrs = window.attributes
        attrs.flags = attrs.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
        window.attributes = attrs
    }

    /**
     * 显示状态栏
     */
    fun showStatusBar(window: Window) {
        val attrs = window.attributes
        attrs.flags = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
        window.attributes = attrs
    }


}

