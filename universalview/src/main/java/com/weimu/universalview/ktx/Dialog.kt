package com.weimu.universalview.ktx

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContextWrapper
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
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
        MaterialDialog.Builder(this)
                .cancelable(false)
                .backgroundColor(ContextCompat.getColor(this, R.color.white))
                .title("提示")
                .content(content)
                .positiveText("去开启")
                .negativeText("知道了")
                .onPositive { dialog, which ->
                    if (positiveCallBack?.invoke() == true) return@onPositive
                    this.requestPermission(
                            permissions = *permissions,
                            granted = granted,
                            content = content
                    )

                }
                .onNegative { dialog, which -> negativeCallBack?.invoke() }
                .show()
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
    MaterialDialog.Builder(this)
            .title(title)
            .content(content)
            .positiveText(positiveStr)
            .negativeText(negativeStr)
            .onPositive { dialog, which ->
                dialog.dismiss()
                positiveCallBack?.invoke(dialog)
            }
            .onNegative { dialog, which ->
                dialog.dismiss()
                negativeCallBack?.invoke(dialog)
            }
            .show()
}

/**
 * 显示列表选择器
 * @param callBack 选择回调
 */
fun ContextWrapper.showListPicker(
        title: String = "列表选择",
        vararg items: String,
        selectedIndex: Int = 0,
        callBack: ((dialog: MaterialDialog, which: Int, text: CharSequence) -> Unit)
) {
    MaterialDialog.Builder(this)
            .title(title)
            .items(*items)
            .itemsCallbackSingleChoice(selectedIndex) { dialog, itemView, which, text ->
                dialog.dismiss()
                callBack.invoke(dialog, which, text)
                true
            }.show()
}

/**
 * 显示时间选择器
 * @param callBack 选择回调
 */
fun ContextWrapper.showTimePicker(callBack: ((hourOfDay: Int, minute: Int) -> Unit)) {
    val hourOfDayNow = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val minuteNow = Calendar.getInstance().get(Calendar.MINUTE)
    TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        callBack.invoke(hourOfDay, minute)
    }, hourOfDayNow, minuteNow, true).show()
}

/**
 * 显示日期选择器
 * @param callBack 选择回调
 */
fun ContextWrapper.showDatePicker(callBack: ((year: Int, month: Int, dayOfMonth: Int) -> Unit)) {
    val yearNow = Calendar.getInstance().get(Calendar.YEAR)
    val monthNow = Calendar.getInstance().get(Calendar.MONTH)
    val dayOfMonthNow = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        callBack.invoke(year, month + 1, dayOfMonth)
    }, yearNow, monthNow, dayOfMonthNow).show()
}

