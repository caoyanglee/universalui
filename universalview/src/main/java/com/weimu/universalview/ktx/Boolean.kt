package com.weimu.universalview.ktx

import android.widget.CheckBox

/**
 * Author:你需要一台永动机
 * Date:2018/11/7 01:58
 * Description:
 */

/**
 * 检查是否False是的话谈吐司
 */
fun Boolean.checkIsFalseAndToast(toastMessage: String = ""): Boolean {
    if (!this) {
        toast(toastMessage)
        return true
    }
    return false
}