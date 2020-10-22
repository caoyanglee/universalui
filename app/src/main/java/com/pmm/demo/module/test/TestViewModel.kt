package com.pmm.demo.module.test

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.pmm.ui.ktx.toast

/**
 * Author:你需要一台永动机
 * Date:2020/10/22 11:19
 * Description:
 */
class TestViewModel(context: Application) : AndroidViewModel(context) {

    fun test(context: Context) {
        context.toast("测试")
    }
}