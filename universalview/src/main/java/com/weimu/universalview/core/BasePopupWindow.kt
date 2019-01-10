package com.weimu.universalview.core

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.weimu.universalib.R


/**
 * @author 艹羊
 * @project Checaiduo_Android
 * @date 2017/5/31 下午6:08
 * @description
 */

open class BasePopupWindow(val context: Context) : PopupWindow() {
    private val mShowAlpha = 0.88f
    private var mBackgroundDrawable: Drawable? = null
    protected var useDefaultMask = false

    init {
        initBasePopupWindow()
    }

    override fun setOutsideTouchable(touchable: Boolean) {
        super.setOutsideTouchable(touchable)
        if (touchable) {
            if (mBackgroundDrawable == null) {
                mBackgroundDrawable = ColorDrawable(0x00000000)
            }
            super.setBackgroundDrawable(mBackgroundDrawable)
        } else {
            super.setBackgroundDrawable(null)
        }
    }

    override fun setBackgroundDrawable(background: Drawable) {
        mBackgroundDrawable = background
        isOutsideTouchable = isOutsideTouchable
    }

    /**
     * 初始化BasePopupWindow的一些信息
     */
    private fun initBasePopupWindow() {
        animationStyle = R.style.MyPopUpWindow
        height = ViewGroup.LayoutParams.MATCH_PARENT
        width = ViewGroup.LayoutParams.MATCH_PARENT
        isOutsideTouchable = true  //默认设置outside点击无响应
        isFocusable = true
        isClippingEnabled = false//是否浸入状态栏
    }

    override fun setContentView(contentView: View?) {
        if (contentView != null) {
            contentView.measure(View.MeasureSpec.AT_MOST, View.MeasureSpec.UNSPECIFIED)
            super.setContentView(contentView)
            addKeyListener(contentView)
        }
    }

    override fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        super.showAtLocation(parent, gravity, x, y)
        if (useDefaultMask) addMaskToWindow()
//        showAnimator().start()
    }

    override fun showAsDropDown(anchor: View) {
        super.showAsDropDown(anchor)
        if (useDefaultMask) addMaskToWindow()
//        showAnimator().start()
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int) {
        super.showAsDropDown(anchor, xoff, yoff)
        if (useDefaultMask) addMaskToWindow()
//        showAnimator().start()
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int, gravity: Int) {
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        if (useDefaultMask) addMaskToWindow()
//        showAnimator().start()
    }

    override fun dismiss() {
        super.dismiss()
        if (useDefaultMask) removeMask()
    }

    private fun addMaskToWindow() {
        if (context is Activity) {
            val window = context.window
            val lp = window.attributes
            lp.alpha = 0.6f
            window.setAttributes(lp)
        }
    }

    private fun removeMask() {
        if (context is Activity) {
            val window = context.window
            val lp = window.attributes
            lp.alpha = 1f
            window.setAttributes(lp)
        }
    }

    /**
     * 窗口显示，窗口背景透明度渐变动画
     */
    private fun showAnimator(): ValueAnimator {
        val animator = ValueAnimator.ofFloat(1.0f, mShowAlpha)
        animator.addUpdateListener { animation ->
            val alpha = animation.animatedValue as Float
            //setWindowBackgroundAlpha(alpha)
        }
        animator.duration = 360
        return animator
    }

    /**
     * 窗口隐藏，窗口背景透明度渐变动画
     */
    private fun dismissAnimator(): ValueAnimator {
        val animator = ValueAnimator.ofFloat(mShowAlpha, 1.0f)
        animator.addUpdateListener { animation ->
            val alpha = animation.animatedValue as Float
            setWindowBackgroundAlpha(alpha)
        }
        animator.duration = 320
        return animator
    }

    /**
     * 为窗体添加outside点击事件
     */
    private fun addKeyListener(contentView: View?) {
        if (contentView != null) {
            contentView.isFocusable = true
            contentView.isFocusableInTouchMode = true
            contentView.setOnKeyListener(View.OnKeyListener { view, keyCode, event ->
                when (keyCode) {
                    KeyEvent.KEYCODE_BACK -> {
                        dismiss()
                        return@OnKeyListener true
                    }
                    else -> {
                    }
                }
                false
            })
        }
    }

    /**
     * 控制窗口背景的不透明度
     */
    private fun setWindowBackgroundAlpha(alpha: Float) {
        val window = (context as Activity).window
        val layoutParams = window.attributes
        layoutParams.alpha = alpha
        window.attributes = layoutParams
    }
}
