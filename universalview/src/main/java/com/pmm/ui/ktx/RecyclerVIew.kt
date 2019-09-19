package com.pmm.ui.ktx

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.pmm.ui.core.recyclerview.layoutmanager.LinearLayoutManagerPro

/**
 * Author:你需要一台永动机
 * Date:2018/10/10 13:45
 * Description:
 */

//初始化列表
fun RecyclerView.init(): RecyclerView {
    this.itemAnimator = DefaultItemAnimator().apply {
        addDuration = 300
        removeDuration = 300
    }//设置Item增加、移除动画
    //取消刷新闪烁
    (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    (this.itemAnimator as SimpleItemAnimator).changeDuration = 0
    this.layoutManager = LinearLayoutManagerPro(context, LinearLayoutManager.VERTICAL, false)
    return this
}