package com.pmm.ui.types

import android.view.inputmethod.EditorInfo

/**
 * Author:你需要一台永动机
 * Date:2020-01-10 00:21
 * Description:
 */
enum class InputType(var code: Int) {
    TEXT(0), TEXT_PASSWORD(1), NUMBER(2), NUMBER_DECIMAL(3)
}

//转换输入类型
fun Int.translateInputType(): Int = when (this) {
    InputType.TEXT.code -> EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_NULL
    InputType.TEXT_PASSWORD.code -> EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_PASSWORD//密码
    InputType.NUMBER.code -> EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_SIGNED//数字 int
    InputType.NUMBER_DECIMAL.code -> EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL//数字 double
    else -> EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_NULL
}