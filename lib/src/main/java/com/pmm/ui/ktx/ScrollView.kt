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

//设置留白 兼容底部导航条 已经把NavigationBar的高度算进去了
fun ScrollView.setPaddingWithNavigationBar(left: Int, top: Int, right: Int, bottom: Int) {
    this.setPadding(left, top, right, this.context.getNavigationBarHeight() + bottom)
}

//NestedScrollView 初始化
fun NestedScrollView.init() {
    clipToPadding = false
}

//设置留白 兼容底部导航条 已经把NavigationBar的高度算进去了
fun NestedScrollView.setPaddingWithNavigationBar(left: Int, top: Int, right: Int, bottom: Int) {
    this.setPadding(left, top, right, this.context.getNavigationBarHeight() + bottom)
}