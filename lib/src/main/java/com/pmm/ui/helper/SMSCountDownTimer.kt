package com.pmm.ui.helper

import android.content.Context
import android.os.CountDownTimer
import android.widget.TextView
import com.pmm.ui.R


//短信倒计时
class SMSCountDownTimer(
        var context:Context,
        var tv_send_code: TextView? = null,
        val countDownSecond: Int = 60
) {

    private var cd: CountDownTimer
    var getCodeIng = false//正在获取验证码中

    init {
        cd = object : CountDownTimer((countDownSecond * 1000).toLong(), 1000) {
            override fun onTick(l: Long) {
                val str = "${context.getString(R.string.sms_get_to_resend)}(${l / 1000}s)"
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
        tv_send_code?.text = context.getString(R.string.sms_get_verify_code)
        tv_send_code?.isEnabled = true
    }
}
