package com.weimu.universalview.helper

import android.os.CountDownTimer
import android.widget.TextView


//短信倒计时
class SMSCountDownTimer(
        var tv_send_code: TextView? = null,
        val countDownSecond: Int = 60
) {

    private var cd: CountDownTimer
    var getCodeIng = false//正在获取验证码中

    init {
        cd = object : CountDownTimer((countDownSecond * 1000).toLong(), 1000) {
            override fun onTick(l: Long) {
                val str = "重新发送(${l / 1000}s)"
                tv_send_code?.text = str
            }

            override fun onFinish() {
                endCountTime()
            }
        }
    }

    //开始倒计时
    fun startCountTime() {
        getCodeIng = true
        tv_send_code?.isEnabled = false
        cd.start()
    }


    //结束倒计时
    fun endCountTime() {
        cd.cancel()
        getCodeIng = false
        tv_send_code?.text = "获取验证码"
        tv_send_code?.isEnabled = true
    }
}
