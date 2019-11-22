package com.pmm.ui.ktx

import android.annotation.SuppressLint
import android.content.ContextWrapper
import androidx.fragment.app.FragmentActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.afollestad.materialdialogs.datetime.timePicker
import com.afollestad.materialdialogs.input.InputCallback
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.tbruyelle.rxpermissions2.RxPermissions
import java.util.*

/**
 *
 * 请求权限
 * Manifest.permission.WRITE_EXTERNAL_STORAGE
 * Manifest.permission.READ_EXTERNAL_STORAGE
 *
 * 消息例子：没有此权限将无法正常使用软件!\n是否要获取此权限？
 *
 */
@SuppressLint("CheckResult")
fun FragmentActivity.requestPermission(vararg permissions: String,
                                       granted: (() -> Unit)? = null,
                                       content: String = "无此权限app有可能无法正常运行!",
                                       positiveCallBack: (() -> Boolean)? = null,
                                       negativeCallBack: (() -> Unit)? = null) {
    fun showDialog() {
        if (content.isBlank()) return
        MaterialDialog(this).show {
            cancelable(false)
            cornerRadius(4f)
            title(text = "提示")
            message(text = content)
            positiveButton(text = "去开启") {
                if (positiveCallBack?.invoke() == true) return@positiveButton
                this@requestPermission.requestPermission(
                        permissions = *permissions,
                        granted = granted,
                        content = content
                )
            }
            negativeButton(text = "知道了") {
                negativeCallBack?.invoke()
            }
        }
    }

    RxPermissions(this).request(*permissions)
            .subscribe {
                if (it) {
                    granted?.invoke()
                } else {
                    showDialog()
                }
            }
}

/**
 * 显示确认的dialog
 */
fun ContextWrapper.showConfirmDialog(
        title: String = "提示",
        content: String = "提示的内容",
        negativeStr: String = "取消",
        negativeCallBack: ((dialog: MaterialDialog) -> Unit)? = null,
        positiveStr: String = "确认",
        positiveCallBack: ((dialog: MaterialDialog) -> Unit)? = null
) {
    MaterialDialog(this).show {
        cancelable(false)
        cornerRadius(4f)
        title(text = title)
        message(text = content)
        positiveButton(text = positiveStr) {
            it.dismiss()
            positiveCallBack?.invoke(it)
        }
        negativeButton(text = negativeStr) {
            it.dismiss()
            negativeCallBack?.invoke(it)
        }
    }
}

/**
 * 显示列表选择器
 * @param callBack 选择回调
 */
fun ContextWrapper.showListPicker(
        title: String = "列表选择",
        items: List<CharSequence>,
        selectedIndex: Int = 0,
        callBack: ((dialog: MaterialDialog, which: Int, text: CharSequence) -> Unit)
) {
    MaterialDialog(this).show {
        cornerRadius(4f)
        listItemsSingleChoice(items = items, initialSelection = selectedIndex) { dialog, index, text ->
            title(text = title)
            dialog.dismiss()
            callBack.invoke(dialog, index, text)
        }
    }
}

/**
 * 显示时间选择器
 * @param callBack 选择回调
 */
fun ContextWrapper.showTimePicker(callBack: ((hourOfDay: Int, minute: Int) -> Unit)) {
    MaterialDialog(this).show {
        cornerRadius(4f)
        timePicker { dialog, datetime ->
            val hourOfDay = datetime.get(Calendar.HOUR_OF_DAY)
            val minute = datetime.get(Calendar.MINUTE)
            callBack.invoke(hourOfDay, minute)
        }
    }
}

/**
 * 显示日期选择器1
 * @param callBack 选择回调
 */
fun ContextWrapper.showDatePicker(callBack: ((year: Int, month: Int, dayOfMonth: Int) -> Unit)) {
    MaterialDialog(this).show {
        cornerRadius(4f)
        datePicker { dialog, datetime ->
            val year = Calendar.getInstance().get(Calendar.YEAR)
            val month = Calendar.getInstance().get(Calendar.MONTH)
            val dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            callBack.invoke(year, month + 1, dayOfMonth)
        }
    }
}

/**
 * 显示日期时间选择器
 * @param callBack 选择回调
 */
fun ContextWrapper.showDateTimePicker(callBack: ((year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int) -> Unit)) {
    MaterialDialog(this).show {
        cornerRadius(4f)
        dateTimePicker { dialog, datetime ->
            val year = Calendar.getInstance().get(Calendar.YEAR)
            val month = Calendar.getInstance().get(Calendar.MONTH)
            val dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            val hourOfDay = datetime.get(Calendar.HOUR_OF_DAY)
            val minute = datetime.get(Calendar.MINUTE)
            callBack.invoke(year, month + 1, dayOfMonth, hourOfDay, minute)
        }
    }
}

/**
 * 显示输入选择器
 * @param callBack 选择回调
 */
fun ContextWrapper.showInputPicker(title: String, hint: String, prefill: String, callBack: InputCallback) {
    MaterialDialog(this).show {
        cornerRadius(4f)
        title(text = title)
        input(hint = hint, prefill = prefill, callback = callBack)
    }
}
