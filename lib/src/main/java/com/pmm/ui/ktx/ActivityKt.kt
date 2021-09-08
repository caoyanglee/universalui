package com.pmm.ui.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * 隐藏键盘
 * @param targetView 目标视图
 */
fun Activity.hideKeyBoard(targetView: View? = null) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (targetView != null && inputMethodManager.isActive(targetView)) {
        targetView.clearFocus()
    }
    inputMethodManager.hideSoftInputFromWindow(window.decorView.windowToken, 0) //强制隐藏键盘
}

/**
 * 显示键盘
 * @param 目标视图
 */
fun Activity.showKeyBoard(targetView: View? = null) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (targetView != null) {
        targetView.requestFocus()
        inputMethodManager.showSoftInput(targetView, 0) //强制显示键盘
    } else {
        inputMethodManager.showSoftInput(window.decorView, 0) //强制显示键盘
    }

}

/**
 * 打开Activity
 */
inline fun <reified T : Activity> Activity.openActivity(
    bundle: Bundle? = null,
    requestCode: Int? = null,
    enterAnim: Int? = null,
    exitAnim: Int? = null
) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) intent.putExtras(bundle)
    if (requestCode != null)
        startActivityForResult(intent, requestCode)
    else
        startActivity(intent)
    if (enterAnim != null && exitAnim != null) overridePendingTransition(enterAnim, exitAnim)
}


//是否位于栈底
fun Activity.isOnTaskRoot(): Boolean {
    if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
        finish()
        return false
    }
    return true
}

