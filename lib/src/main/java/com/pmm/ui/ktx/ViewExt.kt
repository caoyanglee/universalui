package com.pmm.ui.ktx

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * 单击 单参
 */

inline fun View.click(crossinline click: ((View) -> Unit)) {
    this.click(click, 600)
}

/**
 * 单击 双参
 */
inline fun View.click(crossinline click: ((View) -> Unit), delay: Long = 600) {
    var isSingleClick = false//是否正在处理事件
    this.setOnClickListener {
        MainScope().launch {
            if (isSingleClick) return@launch
            click.invoke(this@click)
            isSingleClick = true
            delay(delay)
            isSingleClick = false
        }
    }
}

/**
 * 长按
 */
inline fun View.clickLong(crossinline longClick: ((View) -> Unit)) {
    this.setOnLongClickListener {
        longClick.invoke(this@clickLong)
        true
    }
}


/**
 * 双击
 */
inline fun View.clickDouble(crossinline singleClick: () -> Unit, crossinline doubleClick: () -> Unit, delay: Long = 1000) {
    var lastClickTime: Long = 0
    var isSingleClick = false//是否已经单击
    var isDoubleClick = false//是否已经双击
    this.setOnClickListener {
        val curTime = System.currentTimeMillis()
        if (curTime - lastClickTime > delay) {
            if (isSingleClick) return@setOnClickListener
            isSingleClick = true
            singleClick.invoke()
            lastClickTime = System.currentTimeMillis()
            MainScope().launch {
                delay(300)
                isSingleClick = false
            }
        } else {
            MainScope().launch {
                if (isDoubleClick) return@launch
                isDoubleClick = true
                doubleClick.invoke()
                delay(301)
                isSingleClick = false
                lastClickTime = 0
                isDoubleClick = false
            }
        }
    }
}


//设置宽度和高度
fun View.setViewLayoutParams(width: Int, height: Int) {
    val p = this.layoutParams
    p.width = width
    p.height = height
    this.layoutParams = p
}

//设置高度
fun View.setHeight(height: Int) {
    this.setViewLayoutParams(-1, height)
}

//设置宽度
fun View.setWidth(width: Int) {
    this.setViewLayoutParams(width, -1)
}

//设置外边界
fun View.setMargins(l: Int? = null, t: Int? = null, r: Int? = null, b: Int? = null) {
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        val left = l ?: p.leftMargin
        val top = t ?: p.topMargin
        val right = r ?: p.rightMargin
        val bottom = b ?: p.bottomMargin
        p.setMargins(left, top, right, bottom)
        this.requestLayout()
    }
}


//请求获取焦点
fun View.focus() {
    this.isFocusable = true
    this.isFocusableInTouchMode = true
    this.requestFocus()
}

//请求去除焦点
fun View.unFocus() {
    this.isFocusable = false
    this.isFocusableInTouchMode = false
    this.clearFocus()
}

//解析xml视图
fun ViewGroup.inflate(layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)

fun Context.inflate(layoutRes: Int): View = LayoutInflater.from(this).inflate(layoutRes, null, false)

//获取当前视图对应的Bitmap
@Deprecated("图片超过屏幕时会crash @see View.screenShot()")
fun View.getBitmap(handle: (viewBitmap: Bitmap) -> Unit) {
    isDrawingCacheEnabled = true
    buildDrawingCache()
    val viewBitmap = drawingCache
    handle(viewBitmap)
    isDrawingCacheEnabled = false
}


/**
 * 替代getDrawingCache方法
 *
 */
fun View.screenShot(handle: (viewBitmap: Bitmap) -> Unit) {
    try {
        val screenshot: Bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
        val c = Canvas(screenshot)
        c.translate((-this.scrollX).toFloat(), (-this.scrollY).toFloat())
        this.draw(c)
        handle(screenshot)
    } catch (e: Exception) {
        System.gc()
        context.toast("保存错误，请重新点击")
    }
}

//显示
fun View?.visible() {
    this?.visibility = View.VISIBLE
}

//隐藏-占位
fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

//隐藏-不占位
fun View?.gone() {
    this?.visibility = View.GONE
}


//隐藏所有视图  无占位
fun hideAllViews(vararg views: View?) {
    for (view in views) {
        view?.visibility = View.GONE
    }
}


//隐藏所有视图  有占位
fun inVisibleAllViews(vararg views: View?) {
    for (view in views) {
        view?.visibility = View.INVISIBLE
    }
}


//显示所有视图
fun showAllViews(vararg views: View?) {
    for (view in views) {
        view?.visibility = View.VISIBLE
    }
}

//显示视图 one by one
fun showAllViewOneByOne(timeMillis: Long, vararg views: View?) {
    if (views.isEmpty()) return
    views[0]?.visibility = View.VISIBLE
    for (i in 1 until views.size) {
        val view = views[i]
        Handler().postDelayed({ view?.visibility = View.VISIBLE }, timeMillis * i)
    }
}


//动态修改View的背景 颜色，圆角
class ViewBgOption {
    var color: Int = -1
    var radius: Float = -1f
}

//修改背景
fun View?.bg(option: ViewBgOption) {
    if (this == null) return
    var bg = this.background
    if (bg !is GradientDrawable) {
        bg = GradientDrawable()
    }
    val targetBg = bg as GradientDrawable
    if (option.color != -1)
        targetBg.setColor(option.color)
    if (option.radius != -1f)
        targetBg.cornerRadius = option.radius
    this.background = targetBg
}

//是否是显示状态 VISIBLE
fun View.isVisible() = this.visibility == View.VISIBLE

//是否是显示状态 GONE
fun View.isGone() = this.visibility == View.GONE

//是否是显示状态 INVISIBLE
fun View.isInvisible() = this.visibility == View.INVISIBLE

//设置ViewPager的默认显示位置 通过反射 防止第一个Fragment加载
fun ViewPager.setDefaultItem(item: Int = 0, smoothScroll: Boolean = true) {
    try {
        val c = Class.forName("androidx.viewpager.widget")
        val field = c.getDeclaredField("mCurItem")
        field.isAccessible = true
        field.setInt(this, item)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    this.setCurrentItem(item, smoothScroll)
}


/**
 * 展示SnackBar的MD2样式
 * @param marginBottom 距离底部的margin
 */
fun Snackbar.showMD2(marginBottom: Int = this.context.dip2px(16f)) {
    val context = this.context
    val snackbarView = this.view as FrameLayout//获取SnackBar布局View实例
    snackbarView.bg(ViewBgOption().apply {
        this.radius = context.dip2px(8f).toFloat()
    })
    snackbarView.setMargins(
            b = marginBottom,
            l = context.dip2px(16f),
            r = context.dip2px(16f)
    )
    this.show()
}