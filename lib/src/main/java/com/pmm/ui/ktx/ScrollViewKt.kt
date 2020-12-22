package com.pmm.ui.ktx

import android.widget.ScrollView
import androidx.core.widget.NestedScrollView

/**
 * Author:你需要一台永动机
 * Date:2020/5/7 10:21
 * Description:
 */

//ScrollView 初始化
fun ScrollView.init() {
    clipToPadding = false
}


//NestedScrollView 初始化
fun NestedScrollView.init() {
    clipToPadding = false
}
