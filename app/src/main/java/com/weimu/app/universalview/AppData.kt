package com.weimu.app.universalview

import android.app.Activity
import com.weimu.universalib.OriginAppData
import com.weimu.universalib.ktx.dip2px
import com.weimu.universalview.core.toolbar.ToolBarManager
import java.util.*
import kotlin.properties.Delegates


/**
 * Author:你需要一台永动机
 * Date:2018/9/17 18:30
 * Description:
 */
class AppData : OriginAppData() {


    //伴随对象
    companion object {
        var context: AppData by Delegates.notNull()

        private val activityList = Stack<Activity>()

        fun getCurrentActivity() = activityList.peek()!!


        fun closeActivityExceptMain() {
            activityList.filter { it !is MainActivity }.forEach { it.finish() }
        }


        //退出app
        fun exitApp() {
            activityList.forEach { it.finish() }
            //do something
            //LocationCenter.destroy()
        }


    }

    override fun isDebug() = BuildConfig.DEBUG

    override fun onCreate() {
        super.onCreate()
        context = this
        initAppBarConfig()
    }

    private fun initAppBarConfig() {
        ToolBarManager.DefaultConfig.apply {
            toolbarPadding= context.dip2px(20f)
            toolbarHeight= context.dip2px(44f)
        }
    }

}