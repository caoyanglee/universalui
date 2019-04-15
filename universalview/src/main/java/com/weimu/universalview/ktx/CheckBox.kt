package com.weimu.universalview.ktx

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.annotation.DrawableRes
import android.widget.CheckBox
import com.weimu.universalib.ktx.dip2px

/**
 * Author:你需要一台永动机
 * Date:2018/11/1 22:35
 * Description:
 */

//自定义CheckBox的左侧按钮
fun CheckBox.modifyLeftDrawable(@DrawableRes resId: Int = -1) {
    if (resId == -1) throw IllegalArgumentException("I need the resId~")
    this.setButtonDrawable(resId)
    //设置默认左Icon为透明
    val drawable = ColorDrawable(Color.TRANSPARENT)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight) //设置边界
    this.setCompoundDrawables(drawable, null, null, null)
    this.compoundDrawablePadding = context.dip2px(5f)
}

/**
 *检查是否False是的话谈吐司
 */
fun CheckBox.checkIsFalseAndToast(toastMessage: String = ""): Boolean {
    if (!this.isChecked) {
        this.requestFocus()
        toast(toastMessage)
        return true
    }
    return false
}