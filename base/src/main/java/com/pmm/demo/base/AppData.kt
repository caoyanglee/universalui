package com.pmm.demo.base

import android.app.Application
import android.content.Context
import android.graphics.Color
import androidx.multidex.MultiDex
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.pmm.metro.Metro
import com.pmm.ui.ktx.dip2px
import com.pmm.ui.ktx.getColorPro
import com.pmm.ui.ktx.getDrawablePro
import com.pmm.ui.widget.ToolBarPro
import kotlin.properties.Delegates


/**
 * Author:你需要一台永动机
 * Date:2018/9/17 18:30
 * Description:
 */
class AppData : Application() {


    //伴随对象
    companion object {
        var context: AppData by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        initLogger() //日志
        initAppBarConfig()

        //路由
        Metro.init(this)

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initAppBarConfig() {

        ToolBarPro.GlobalConfig.apply {
            showStatusView = true
            //ToolBar
            toolbarBgColor = context.getColorPro(R.color.colorPrimary)

            //centerTitle
            centerTitleColor = Color.WHITE
            centerTitleSize = 17f

            //navigation
            navigationDrawable = context.getDrawablePro(R.drawable.universal_arrow_back_white)

            //divider
            dividerShow = false
            dividerColor = Color.TRANSPARENT
            dividerSize = context.dip2px(0f)
        }
    }

    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
            .methodCount(2) // (Optional) How many method line to show. Default 2
            .tag("weimu")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()

        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?) = BuildConfig.DEBUG
        })
    }


}