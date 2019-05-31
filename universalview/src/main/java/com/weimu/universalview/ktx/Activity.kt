package com.weimu.universalview.ktx

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*


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

//显示时间选择器
fun Activity.showTimePicker(callBack: ((hourOfDay: Int, minute: Int) -> Unit)) {
    val hourOfDayNow = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val minuteNow = Calendar.getInstance().get(Calendar.MINUTE)
    TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        callBack.invoke(hourOfDay, minute)
    }, hourOfDayNow, minuteNow, true).show()
}

//显示日期选择器
fun Activity.showDatePicker(callBack: ((year: Int, month: Int, dayOfMonth: Int) -> Unit)) {
    val yearNow = Calendar.getInstance().get(Calendar.YEAR)
    val monthNow = Calendar.getInstance().get(Calendar.MONTH)
    val dayOfMonthNow = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        callBack.invoke(year, month + 1, dayOfMonth)
    }, yearNow, monthNow, dayOfMonthNow).show()
}