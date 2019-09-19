package com.weimu.universalview.ktx

import android.content.Context
import android.widget.Toast
import com.weimu.universalview.OriginAppData



private var clickTime: Long = 0

//双击
fun Any.doubleClick(firstClick: () -> Unit = {}, doubleClick: () -> Unit, delay: Long = 2000) {
    if (System.currentTimeMillis() - clickTime > delay) {
        firstClick.invoke()
        clickTime = System.currentTimeMillis()
    } else {
        doubleClick.invoke()
    }
}



