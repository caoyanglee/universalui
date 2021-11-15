@file:Suppress("CHANGING_ARGUMENTS_EXECUTION_ORDER_FOR_NAMED_VARARGS")

package com.pmm.ui.ktx

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContextWrapper
import androidx.fragment.app.FragmentActivity
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.list.listItems
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.permissionx.guolindev.PermissionX
import com.pmm.ui.R


typealias DialogCallBack = (() -> Unit)?//适用各种回调

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
    vararg permissions: String,
    allGrantedCallback: DialogCallBack = null,
    denyCallback: DialogCallBack = null,
    //以下都是权限提示弹窗
    message: String = "无此权限app有可能无法正常运行!",
    positiveStr: String = baseContext.getString(R.string.dialog_action_ok),
    negativeStr: String? = baseContext.getString(R.string.dialog_action_cancel),
) {
    //开始请求权限
    PermissionX.init(this)
        .permissions(*permissions)
        .onExplainRequestReason { scope, deniedList ->
            scope.showRequestReasonDialog(
                deniedList,
                message,
                positiveStr,
                negativeStr
            )
        }
        //.explainReasonBeforeRequest()
        .onForwardToSettings { scope, deniedList ->
            scope.showForwardToSettingsDialog(deniedList, message, positiveStr, negativeStr)
        }
        .request { allGranted, grantedList, deniedList ->
            if (allGranted) {
                allGrantedCallback?.invoke()
            } else {
                denyCallback?.invoke()
            }
        }
}


@SuppressLint("CheckResult")
fun FragmentActivity.requestPermissionWithoutDialog(
    vararg permissions: String,
    allGrantedCallback: (() -> Unit)? = null,
    denyCallback: DialogCallBack = null,
) {
    //开始请求权限
    PermissionX.init(this)
        .permissions(*permissions)
        .request { allGranted, grantedList, deniedList ->
            if (allGranted) {
                allGrantedCallback?.invoke()
            } else {
                denyCallback?.invoke()
            }
        }
}

/**
 * 显示确认的dialog
 */
fun ContextWrapper.showConfirmDialog(
    title: String = baseContext.getString(R.string.dialog_title_default),
    message: String = baseContext.getString(R.string.dialog_message_default),
    bgCornerRadius: Float = 16f,
    cancelable: Boolean = true,
    negativeStr: String? = baseContext.getString(R.string.dialog_action_cancel),
    negativeCallback: DialogCallback? = null,
    positiveStr: String = baseContext.getString(R.string.dialog_action_ok),
    positiveCallback: DialogCallback? = null,
    dismissCallback: DialogCallback? = null
) {
    MaterialDialog(this).show {
        cancelable(cancelable)
        cornerRadius(bgCornerRadius)
        title(text = title)
        message(text = message)
        positiveButton(text = positiveStr) {
            it.dismiss()
            positiveCallback?.invoke(it)
        }
        if (negativeStr != null) {
            negativeButton(text = negativeStr) {
                it.dismiss()
                negativeCallback?.invoke(it)
            }
        }
        dismissCallback?.let { onDismiss(it) }
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
    bgCornerRadius: Float = 16f,
    callBack: ((dialog: MaterialDialog, which: Int, text: CharSequence) -> Unit),
    dismissCallback: DialogCallback? = null
) {
    MaterialDialog(this).show {
        cornerRadius(bgCornerRadius)
        title(text = title)
        listItemsSingleChoice(
            items = items,
            initialSelection = selectedIndex
        ) { dialog, index, text ->
            dialog.dismiss()
            callBack.invoke(dialog, index, text)
        }
        dismissCallback?.let { onDismiss(it) }
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
    bgCornerRadius: Float = 16f,
    callBack: ((dialog: MaterialDialog, which: Int, text: CharSequence) -> Unit),
    dismissCallback: DialogCallback? = null
) {
    MaterialDialog(this).show {
        cornerRadius(bgCornerRadius)
        if (title != null && title.isNotBlank()) title(text = title)
        listItems(items = items) { dialog, index, text ->
            dialog.dismiss()
            callBack.invoke(dialog, index, text)
        }
        dismissCallback?.let { onDismiss(it) }
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
