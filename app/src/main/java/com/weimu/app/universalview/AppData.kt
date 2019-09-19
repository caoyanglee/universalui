package com.weimu.app.universalview

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import com.weimu.app.universalview.module.main.MainActivity
import com.weimu.universalview.OriginAppData
import com.weimu.universalview.interfaces.MyActivityLifeCycleCallbacks
import com.weimu.universalview.ktx.dip2px
import com.weimu.universalview.ktx.getColorPro
import com.weimu.universalview.ktx.getDrawablePro
import com.weimu.universalview.widget.ToolBarPro
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
    }

    override fun isDebug() = BuildConfig.DEBUG

    override fun onCreate() {
        super.onCreate()
        context = this
        initAppBarConfig()
    }

    private fun initAppBarConfig() {

        ToolBarPro.GlobalConfig.apply {
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
}