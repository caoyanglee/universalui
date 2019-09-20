package com.pmm.ui.ktx

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


private var lastClickTime: Long = 0
private var isSingleClick = false//是否已经单击
private var isDoubleClick = false//是否已经双击

/**
 * 双击 - 用于回调
 */
fun Any.doubleClick(singleClick: () -> Unit, doubleClick: () -> Unit, delay: Long = 1000) {
    val curTime = System.currentTimeMillis()
    if (curTime - lastClickTime > delay) {
        if (isSingleClick) return
        isSingleClick = true
        singleClick.invoke()
        lastClickTime = System.currentTimeMillis()
        MainScope().launch {
            kotlinx.coroutines.delay(300)
            isSingleClick = false
        }
    } else {
        MainScope().launch {
            if (isDoubleClick) return@launch
            isDoubleClick = true
            doubleClick.invoke()
            kotlinx.coroutines.delay(301)
            isSingleClick = false
            lastClickTime = 0
            isDoubleClick = false
        }
    }
}



