package com.weimu.universalview.ktx

import android.content.Context
import android.widget.Toast
import com.weimu.universalview.OriginAppData


//吐司通知-普通
fun Any.toast(message: CharSequence, context: Context? = null) {
    try {
        Toast.makeText(
                context ?: OriginAppData.context,
                message,
                if (message.length > 30) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        ).show()
    } catch (e: Exception) {
        //doNothing
    }
}


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



