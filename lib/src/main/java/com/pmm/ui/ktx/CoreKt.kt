package com.pmm.ui.ktx

import android.content.ContentResolver
import android.graphics.Color
import android.provider.Settings
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * Author:你需要一台永动机
 * Date:2020/5/23 11:02
 * Description:
 */

//是否开启 不保留活动
fun ContentResolver.isAlwaysFinishActivities(): Boolean {
    val isAlways = Settings.Global.getInt(this, Settings.Global.ALWAYS_FINISH_ACTIVITIES, 0)//开发者选项 不保留活动
    return isAlways == 1
}


private var lastClickTime: Long = 0
private var isSingleClick = false//是否已经单击
private var isDoubleClick = false//是否已经双击

/**
 * 双击 - 用于回调
 */
fun doubleClick(singleClick: () -> Unit, doubleClick: () -> Unit, delay: Long = 1000) {
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


/**
 * 是否是亮色
 */
fun Int.isLightColor(): Boolean {
    val darkness = 1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
    return darkness < 0.5
}




