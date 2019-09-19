package com.weimu.universalview

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import kotlin.properties.Delegates

/**
 * Author:你需要一台永动机
 * Date:2018/4/10 10:15
 * Description:
 */
abstract class OriginAppData : Application() {

    //伴随对象
    companion object {
        var context: OriginAppData by Delegates.notNull()
    }

    abstract fun isDebug(): Boolean//是否是Debug

    override fun onCreate() {
        super.onCreate()
        context = this
        //日志
        initLogger()
    }

    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2) // (Optional) How many method line to show. Default 2
                .tag("weimu")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()

        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?) = isDebug()
        })
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


}
