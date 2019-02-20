package com.weimu.universalview.interfaces

import android.text.Editable
import android.text.TextWatcher

/**
 * @project KotLinProject
 * @author 艹羊
 * @date 2017/6/13 下午4:48
 * @description
 */
abstract class MyTextWatcher : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
    }
}