package com.pmm.ui.helper

import android.os.CountDownTimer
import android.widget.TextView


//广告页倒计时
class SplashCountDownTimer(
        var tv_send_code: TextView? = null,
        val countDownSecond: Int = 3
) {

    private var cd: CountDownTimer


    var onTimeIsUpListener: (() -> Unit)? = null

    init {
        cd = object : CountDownTimer((countDownSecond * 1000).toLong(), 1000) {
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
