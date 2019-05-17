package com.weimu.universalview.ktx

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.content.ContextWrapper


/**
 * Author:你需要一台永动机
 * Date:2019/4/15 14:25
 * Description:
 */

//隐藏键盘
fun Activity.hideKeyBoard(targetView: View? = null) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (targetView != null && inputMethodManager.isActive(targetView)) {
        targetView.clearFocus()
    }
    inputMethodManager.hideSoftInputFromWindow(window.decorView.windowToken, 0) //强制隐藏键盘
}

//显示键盘
fun Activity.showKeyBoard(targetView: View? = null) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (targetView != null) {
        targetView.requestFocus()
        inputMethodManager.showSoftInput(targetView, 0) //强制显示键盘
    } else {
        inputMethodManager.showSoftInput(window.decorView, 0) //强制显示键盘
    }

}
