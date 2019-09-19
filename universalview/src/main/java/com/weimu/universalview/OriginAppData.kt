package com.weimu.universalview

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
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
    }

}
