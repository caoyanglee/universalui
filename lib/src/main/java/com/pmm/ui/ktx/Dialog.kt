@file:Suppress("CHANGING_ARGUMENTS_EXECUTION_ORDER_FOR_NAMED_VARARGS")

package com.pmm.ui.ktx

import android.annotation.SuppressLint
import android.content.ContextWrapper
import androidx.fragment.app.FragmentActivity
import com.afollestad.assent.Permission
import com.afollestad.assent.askForPermissions
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.list.listItems
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.pmm.ui.R

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
fun FragmentActivity.requestPermission(
        vararg permissions: Permission,
        allGrantedCallBack: (() -> Unit)? = null,
        allDeniedCallBack: (() -> Boolean)? = null,
        permanentlyDeniedCallBack: (() -> Boolean)? = null,
        //以下都是权限提示弹窗
        message: String = "无此权限app有可能无法正常运行!",
        positiveStr: String = baseContext.getString(R.string.dialog_action_ok),
        positiveCallBack: (() -> Boolean)? = null,
        negativeStr: String? = baseContext.getString(R.string.dialog_action_cancel),
        negativeCallBack: (() -> Unit)? = null,
) {
    val activity = this

    //权限弹窗
    fun showDialog(isPermanentlyDenied: Boolean) {
        MaterialDialog(this).show {
            cancelable(false)
            cornerRadius(8f)
            title(R.string.dialog_title_default)
            message(text = message)
            positiveButton(text = positiveStr) {
                if (positiveCallBack?.invoke() == true) return@positiveButton
                if (isPermanentlyDenied)
                    activity.openAppInfoPage()
                else
                    activity.requestPermission(
                            permissions = permissions,
                            allGrantedCallBack = allGrantedCallBack,
                            permanentlyDeniedCallBack = permanentlyDeniedCallBack,
                            message = message,
                            positiveStr = positiveStr,
                            positiveCallBack = positiveCallBack,
                            negativeStr = negativeStr,
                            negativeCallBack = negativeCallBack
                    )
            }
            negativeButton(text = negativeStr) {
                negativeCallBack?.invoke()
            }
        }
    }
    //开始请求权限
    askForPermissions(*permissions) {
        when {
            it.isAllGranted(*permissions) -> {
                allGrantedCallBack?.invoke()
            }
            it.permanentlyDenied().isNotEmpty() -> {
                if (permanentlyDeniedCallBack?.invoke() == true) return@askForPermissions
                showDialog(true)
            }
            else -> {
                if (allDeniedCallBack?.invoke() == true) return@askForPermissions
                showDialog(false)
            }
        }
    }

}

/**
 * 显示确认的dialog
 */
fun ContextWrapper.showConfirmDialog(
        title: String = baseContext.getString(R.string.dialog_title_default),
        message: String = baseContext.getString(R.string.dialog_message_default),
        cancelable: Boolean = true,
        negativeStr: String? = baseContext.getString(R.string.dialog_action_cancel),
        negativeCallBack: ((dialog: MaterialDialog) -> Unit)? = null,
        positiveStr: String = baseContext.getString(R.string.dialog_action_ok),
        positiveCallBack: ((dialog: MaterialDialog) -> Unit)? = null,
        dismissCallBack: (() -> Unit)? = null
) {
    MaterialDialog(this).show {
        cancelable(cancelable)
        cornerRadius(8f)
        title(text = title)
        message(text = message)
        positiveButton(text = positiveStr) {
            it.dismiss()
            positiveCallBack?.invoke(it)
        }
        if (negativeStr != null) {
            negativeButton(text = negativeStr) {
                it.dismiss()
                negativeCallBack?.invoke(it)
            }
        }
        onDismiss {
            dismissCallBack?.invoke()
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
        callBack: ((dialog: MaterialDialog, which: Int, text: CharSequence) -> Unit),
        dismissCallBack: (() -> Unit)? = null
) {
    MaterialDialog(this).show {
        cornerRadius(8f)
        title(text = title)
        listItemsSingleChoice(items = items, initialSelection = selectedIndex) { dialog, index, text ->
            dialog.dismiss()
            callBack.invoke(dialog, index, text)
        }
        onDismiss {
            dismissCallBack?.invoke()
        }
    }
}


/**
 * 显示列表选择器
 * @param title getString(R.string.dialog_title_default)
 * @param callBack 选择回调
 */
fun ContextWrapper.showListPicker(
        title: String? = null,
        items: List<CharSequence>,
        callBack: ((dialog: MaterialDialog, which: Int, text: CharSequence) -> Unit),
        dismissCallBack: (() -> Unit)? = null
) {
    MaterialDialog(this).show {
        cornerRadius(8f)
        if (title != null && title.isNotBlank()) title(text = title)
        listItems(items = items) { dialog, index, text ->
            dialog.dismiss()
            callBack.invoke(dialog, index, text)
        }
        onDismiss {
            dismissCallBack?.invoke()
        }
    }
}

///**
// * 显示时间选择器
// * @param callBack 选择回调
// */
//fun ContextWrapper.showTimePicker(callBack: ((hourOfDay: Int, minute: Int, datetime: Calendar) -> Unit)) {
//    MaterialDialog(this).show {
//        cornerRadius(8f)
//        timePicker { dialog, datetime ->
//            val hourOfDay = datetime.get(Calendar.HOUR_OF_DAY)
//            val minute = datetime.get(Calendar.MINUTE)
//            callBack.invoke(hourOfDay, minute, datetime)
//        }
//    }
//}
//
///**
// * 显示日期选择器1
// * @param callBack 选择回调
// */
//fun ContextWrapper.showDatePicker(callBack: ((year: Int, month: Int, dayOfMonth: Int, datetime: Calendar) -> Unit)) {
//    MaterialDialog(this).show {
//        cornerRadius(8f)
//        datePicker { dialog, datetime ->
//            val year = datetime.get(Calendar.YEAR)
//            val month = datetime.get(Calendar.MONTH)
//            val dayOfMonth = datetime.get(Calendar.DAY_OF_MONTH)
//            callBack.invoke(year, month + 1, dayOfMonth, datetime)
//        }
//    }
//}
//
///**
// * 显示日期时间选择器
// * @param callBack 选择回调
// */
//fun ContextWrapper.showDateTimePicker(callBack: ((year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int, datetime: Calendar) -> Unit)) {
//    MaterialDialog(this).show {
//        cornerRadius(8f)
//        dateTimePicker { dialog, datetime ->
//            val year = datetime.get(Calendar.YEAR)
//            val month = datetime.get(Calendar.MONTH)
//            val dayOfMonth = datetime.get(Calendar.DAY_OF_MONTH)
//            val hourOfDay = datetime.get(Calendar.HOUR_OF_DAY)
//            val minute = datetime.get(Calendar.MINUTE)
//            callBack.invoke(year, month + 1, dayOfMonth, hourOfDay, minute, datetime)
//        }
//    }
//}
//
///**
// * 显示输入选择器
// * @param callBack 选择回调
// */
//fun ContextWrapper.showInputPicker(title: String, hint: String, prefill: String, callBack: InputCallback) {
//    MaterialDialog(this).show {
//        cornerRadius(8f)
//        title(text = title)
//        input(hint = hint, prefill = prefill, callback = callBack)
//    }
//}
