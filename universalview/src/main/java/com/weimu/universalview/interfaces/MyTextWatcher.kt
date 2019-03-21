package com.weimu.universalview.interfaces

import android.text.Editable
import android.text.TextWatcher

/**
 * Author:你需要一台永动机
 * Date:2019/3/21 22:16
 * Description:
 */
interface MyTextWatcher : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {}

}