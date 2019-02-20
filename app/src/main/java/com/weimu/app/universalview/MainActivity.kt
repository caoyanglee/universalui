package com.weimu.app.universalview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.weimu.universalib.ktx.getColorPro
import com.weimu.universalib.ktx.toast
import com.weimu.universalview.core.activity.BaseActivity
import com.weimu.universalview.core.toolbar.ToolBarManager
import com.weimu.universalview.ktx.setMargins
import com.weimu.universalview.ktx.setOnClickListenerPro
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun getLayoutResID(): Int = R.layout.activity_main

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        ToolBarManager.with(this, getContentView())
                .setNavigationIcon(R.drawable.ic_nav_back_white)
                .bg {
                    setBackgroundColor(getColorPro(R.color.black))
                }
                .title {
                    text = "测试ToolBar"
                    setTextColor(getColorPro(R.color.white))
                }
                .rightMenuIcon {
                    setImageResource(R.drawable.ic_nav_back_white)
                }
                .rightMenuIcon2 {
                    setImageResource(R.drawable.ic_nav_back_white)
                }
//                .rightMenuText {
//                    text="测试"
//                    setTextColor(getColorPro(R.color.white))
//                }
//                .rightMenuText2 {
//                    text="通知"
//                    setTextColor(getColorPro(R.color.white))
//                }
    }
}
