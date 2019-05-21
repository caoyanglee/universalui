package com.weimu.universalview.helper

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit


/**
 * Author:你需要一台永动机
 * Date:2018/2/3 15:51
 * Description:
 */

object EventBusPro {

    //注册
    fun register(subscriber: Any) {
        if (!EventBus.getDefault().isRegistered(subscriber))
            EventBus.getDefault().register(subscriber)
    }

    //注销
    fun unregister(subscriber: Any) {
        if (EventBus.getDefault().isRegistered(subscriber))
            EventBus.getDefault().unregister(subscriber)
    }

    //发送通知
    fun post(obj: Any) {
        EventBus.getDefault().post(obj)
    }

    //发送延迟通知
    @SuppressLint("CheckResult")
    fun postDelay(`object`: Any, time: Long) {
        Observable.timer(time, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { EventBus.getDefault().post(`object`) }
    }
}
