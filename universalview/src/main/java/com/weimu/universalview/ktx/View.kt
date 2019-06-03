package com.weimu.universalview.ktx

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


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
fun View.requestFocus() {
    this.isFocusable = true
    this.isFocusableInTouchMode = true
    this.requestFocus()
}

//请求去除焦点
fun View.clearFocus(view: View) {
    view.isFocusable = false
    view.isFocusableInTouchMode = false
    view.clearFocus()
}

//解析xml视图
fun ViewGroup.inflate(layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)

fun Context.inflate(layoutRes: Int): View = LayoutInflater.from(this).inflate(layoutRes, null, false)

//防止重复点击1
fun View.setOnClickListenerPro(singleClick: View.OnClickListener) {
    var isDoing = false//是否正在处理事件
    this.setOnClickListener {
        if (isDoing) return@setOnClickListener
        singleClick.onClick(this)
        isDoing = true
        Observable.timer(600, TimeUnit.MILLISECONDS).subscribe { isDoing = false }
    }
}


//防止重复点击2
fun View.setOnClickListenerPro(onclick: ((View) -> Unit)?) {
    var isDoing = false//是否正在处理事件
    this.setOnClickListener {
        if (isDoing) return@setOnClickListener
        onclick?.invoke(this)
        isDoing = true
        Observable.timer(600, TimeUnit.MILLISECONDS).subscribe { isDoing = false }
    }
}

//获取当前视图对应的Bitmap
@Deprecated("图片超过屏幕时会crash")
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
        toast("保存错误，请重新点击", context)
    }
}

//显示
fun View.visible() {
    this.visibility = View.VISIBLE
}

//隐藏-占位
fun View.invisible() {
    this.visibility = View.INVISIBLE
}

//隐藏-不占位
fun View.gone() {
    this.visibility = View.GONE
}


//隐藏所有视图  无占位
fun hideAllViews(vararg views: View) {
    for (view in views) {
        view.visibility = View.GONE
    }
}


//隐藏所有视图  有占位
fun inVisibleAllViews(vararg views: View) {
    for (view in views) {
        view.visibility = View.INVISIBLE
    }
}


//显示所有视图
fun showAllViews(vararg views: View) {
    for (view in views) {
        view.visibility = View.VISIBLE
    }
}

//显示视图 one by one
fun showAllViewOneByOne(timeMillis: Long, vararg views: View) {
    if (views.isEmpty()) return
    views[0].visibility = View.VISIBLE
    for (i in 1 until views.size) {
        val view = views[i]
        Handler().postDelayed({ view.visibility = View.VISIBLE }, timeMillis * i)
    }
}


//动态修改View的背景 颜色，圆角
class ViewBgOption {
    var color: Int = -1
    var radius: Float = -1f
}

//修改背景
fun View.bg(option: ViewBgOption) {
    var bg = background
    if (bg !is GradientDrawable) {
        bg = GradientDrawable()
    }
    val targetBg = bg as GradientDrawable
    if (option.color != -1)
        targetBg.setColor(option.color)
    if (option.radius != -1f)
        targetBg.cornerRadius = option.radius
    background = targetBg
}


//获取drawable
fun Context.getDrawablePro(@DrawableRes id: Int): Drawable? {
    val drawable = ContextCompat.getDrawable(this, id)
    drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight) //设置边界
    return drawable
}

//是否是显示状态 VISIBLE
fun View.isVisible() = this.visibility == View.VISIBLE

//是否是显示状态 GONE
fun View.isGone() = this.visibility == View.GONE

//是否是显示状态 INVISIBLE
fun View.isInvisible() = this.visibility == View.INVISIBLE














