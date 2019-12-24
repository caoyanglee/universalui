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
import com.afollestad.materialdialogs.list.listItems
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.tbruyelle.rxpermissions2.RxPermissions
import com.weimu.universalview.R
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
            cornerRadius(8f)
            title(R.string.dialog_title_default)
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
        title: String = baseContext.getString(R.string.dialog_title_default),
        content: String = baseContext.getString(R.string.dialog_message_default),
        negativeStr: String = baseContext.getString(R.string.dialog_action_cancel),
        negativeCallBack: ((dialog: MaterialDialog) -> Unit)? = null,
        positiveStr: String = baseContext.getString(R.string.dialog_action_ok),
        positiveCallBack: ((dialog: MaterialDialog) -> Unit)? = null
) {
    MaterialDialog(this).show {
        cancelable(false)
        cornerRadius(8f)
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
 * 显示单选选择器
 * @param callBack 选择回调
 */
fun ContextWrapper.showSingleChoicePicker(
        title: String = baseContext.getString(R.string.dialog_title_default),
        items: List<CharSequence>,
        selectedIndex: Int = 0,
        callBack: ((dialog: MaterialDialog, which: Int, text: CharSequence) -> Unit)
) {
    MaterialDialog(this).show {
        cornerRadius(8f)
        title(text = title)
        listItemsSingleChoice(items = items, initialSelection = selectedIndex) { dialog, index, text ->
            dialog.dismiss()
            callBack.invoke(dialog, index, text)
        }
    }
}


/**
 * 显示列表选择器
 * @param callBack 选择回调
 */
fun ContextWrapper.showListPicker(
        title: String = baseContext.getString(R.string.dialog_title_default),
        items: List<CharSequence>,
        callBack: ((dialog: MaterialDialog, which: Int, text: CharSequence) -> Unit)
) {
    MaterialDialog(this).show {
        cornerRadius(8f)
        title(text = title)
        listItems(items = items) { dialog, index, text ->
            dialog.dismiss()
            callBack.invoke(dialog, index, text)
        }

    }
}

/**
 * 显示时间选择器
 * @param callBack 选择回调
 */
fun ContextWrapper.showTimePicker(callBack: ((hourOfDay: Int, minute: Int, datetime: Calendar) -> Unit)) {
    MaterialDialog(this).show {
        cornerRadius(8f)
        timePicker { dialog, datetime ->
            val hourOfDay = datetime.get(Calendar.HOUR_OF_DAY)
            val minute = datetime.get(Calendar.MINUTE)
            callBack.invoke(hourOfDay, minute, datetime)
        }
    }
}

/**
 * 显示日期选择器1
 * @param callBack 选择回调
 */
fun ContextWrapper.showDatePicker(callBack: ((year: Int, month: Int, dayOfMonth: Int, datetime: Calendar) -> Unit)) {
    MaterialDialog(this).show {
        cornerRadius(8f)
        datePicker { dialog, datetime ->
            val year = datetime.get(Calendar.YEAR)
            val month = datetime.get(Calendar.MONTH)
            val dayOfMonth = datetime.get(Calendar.DAY_OF_MONTH)
            callBack.invoke(year, month + 1, dayOfMonth, datetime)
        }
    }
}

/**
 * 显示日期时间选择器
 * @param callBack 选择回调
 */
fun ContextWrapper.showDateTimePicker(callBack: ((year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int, datetime: Calendar) -> Unit)) {
    MaterialDialog(this).show {
        cornerRadius(8f)
        dateTimePicker { dialog, datetime ->
            val year = datetime.get(Calendar.YEAR)
            val month = datetime.get(Calendar.MONTH)
            val dayOfMonth = datetime.get(Calendar.DAY_OF_MONTH)
            val hourOfDay = datetime.get(Calendar.HOUR_OF_DAY)
            val minute = datetime.get(Calendar.MINUTE)
            callBack.invoke(year, month + 1, dayOfMonth, hourOfDay, minute, datetime)
        }
    }
}

/**
 * 显示输入选择器
 * @param callBack 选择回调
 */
fun ContextWrapper.showInputPicker(title: String, hint: String, prefill: String, callBack: InputCallback) {
    MaterialDialog(this).show {
        cornerRadius(8f)
        title(text = title)
        input(hint = hint, prefill = prefill, callback = callBack)
    }
}
