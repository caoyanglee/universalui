package com.pmm.ui.core

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt

/**
 * 状态栏操作中心
 * 5.0~5.1 采用半透明的沉浸模式
 * 6.0+ 采用全透明的沉浸模式
 * 6.0~8.0 状态栏有亮色暗色之分
 * 8.0+ 底部导航条有亮色暗色之分
 * 注意：本类的操作应当在super.onCreate()之前调用，慎用FLAG_LAYOUT_NO_LIMITS，否则会导致各种问题
 * @Link https://unicorn-utterances.com/posts/draw-under-navbar-using-react-native/
 */
object StatusNavigationBar {

    /**
     * 修改状态栏为全透明
     */
    fun setStatusBarTransparency(window: Window?) {
        window?.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            } else {
                this.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            } else {
                this.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                this.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.navigationBarColor = Color.TRANSPARENT
                this.statusBarColor = Color.TRANSPARENT
            }
            this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
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


    /**
     * 修改状态栏的颜色为白色
     */
    fun change2LightStatusBar(window: Window?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        val decorView = window?.decorView ?: return
        decorView.systemUiVisibility = decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    /**
     * 修改状态栏的颜色为黑色，也就是Dark模式
     */
    fun change2DarkStatusBar(window: Window?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        val decorView = window?.decorView ?: return
        decorView.systemUiVisibility = decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }


    /**
     * 修改底部操作栏的颜色为白色
     */
    fun change2LightNavigationBar(window: Window?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        val decorView = window?.decorView ?: return
        decorView.systemUiVisibility = decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }

    /**
     * 修改底部操作栏的颜色为黑色，也就是Dark模式
     */
    fun change2DarkNavigationBar(window: Window?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        val decorView = window?.decorView ?: return
        decorView.systemUiVisibility = decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
    }

}

