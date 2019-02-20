package com.weimu.universalview.helper

import android.os.CountDownTimer
import android.widget.TextView


//广告页倒计时
class SplashCountDownTimer(var tv_send_code: TextView? = null) {

    private var cd: CountDownTimer

    val MSG_COUNT_DOWN = 3//倒计时时间

    var onTimeIsUpListener: (() -> Unit)? = null

    init {
        cd = object : CountDownTimer((MSG_COUNT_DOWN * 1000).toLong(), 1000) {
            override fun onTick(l: Long) {
                val str = "${l / 1000}s 跳过"
                tv_send_code?.text = str
            }

            override fun onFinish() {
                tv_send_code?.text = "0s 跳过"
                endCountTime()
                onTimeIsUpListener?.invoke()
            }
        }
    }


    //开始倒计时
    fun startCountTime() {
        tv_send_code?.text = "跳过"
        cd.start()
    }


    //结束倒计时
    fun endCountTime() {
        cd.cancel()
    }
}
