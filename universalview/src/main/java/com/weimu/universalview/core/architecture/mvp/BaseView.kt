package com.weimu.universalview.core.architecture.mvp

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle

/**
 * Author:你需要一台永动机
 * Date:2018/3/8 10:28
 * Description:
 */

interface BaseView {

    fun getContentView(): ViewGroup

}
