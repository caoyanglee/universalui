package com.weimu.universalview.core.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.ViewGroup
import com.weimu.universalib.R
import com.weimu.universalview.core.dialog.BaseDialog

/**
 * Author:你需要一台永动机
 * Date:2018/4/16 19:22
 * Description:
 */
abstract class BottomUpDialog : BaseDialog() {

    //位于底部
    override fun onStart() {
        super.onStart()
        val win = dialog.window
        win!!.attributes.windowAnimations = R.style.BottomToUpDialog//动画
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)

        val params = win.attributes
        params.gravity = Gravity.BOTTOM
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        win.attributes = params
    }
}