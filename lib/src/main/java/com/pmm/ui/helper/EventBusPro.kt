package com.pmm.ui.helper

import android.annotation.SuppressLint
import android.os.Handler
import org.greenrobot.eventbus.EventBus


/**
 * Author:你需要一台永动机
 * Date:2018/2/3 15:51
 * Description:
 */

object EventBusPro {

    //注册
    fun register(subscriber: Any) {
        if (!EventBus.getDefault().isRegistered(subscriber)) EventBus.getDefault().register(subscriber)
    }

    //注销
    fun unregister(subscriber: Any) {
        if (EventBus.getDefault().isRegistered(subscriber)) EventBus.getDefault().unregister(subscriber)
    }

    //发送通知
    fun post(obj: Any) {
        EventBus.getDefault().post(obj)
    }

    //发送粘性通知
    fun postSticky(obj: Any) {
        EventBus.getDefault().postSticky(obj)
    }

    //发送延迟通知
    @SuppressLint("CheckResult")
    fun postDelay(event: Any, time: Long) {
        Handler().postDelayed({
            EventBus.getDefault().post(event)
        },time)
    }
}
