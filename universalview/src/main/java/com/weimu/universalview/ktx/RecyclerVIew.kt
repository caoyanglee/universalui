package com.weimu.universalview.ktx

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import com.weimu.universalview.core.recyclerview.layoutmanager.LinearLayoutManagerPro

/**
 * Author:你需要一台永动机
 * Date:2018/10/10 13:45
 * Description:
 */

//初始化列表
fun RecyclerView.init() {
    this.itemAnimator = DefaultItemAnimator().apply {
        addDuration = 300
        removeDuration = 300
    }//设置Item增加、移除动画
    //取消刷新闪烁
    (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    (this.itemAnimator as SimpleItemAnimator).changeDuration = 0
    this.layoutManager = LinearLayoutManagerPro(context, LinearLayoutManager.VERTICAL, false)
}