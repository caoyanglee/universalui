package com.weimu.app.universalview

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.style.AbsoluteSizeSpan
import com.weimu.app.universalview.adapter.StringAdapter
import com.weimu.universalib.ktx.dip2px
import com.weimu.universalib.ktx.getColorPro
import com.weimu.universalview.core.activity.BaseActivity
import com.weimu.universalview.core.toolbar.StatusBarManager
import com.weimu.universalview.core.toolbar.ToolBarManager
import com.weimu.universalview.ktx.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val adapter by lazy { StringAdapter(getContext()) }

    override fun getLayoutResID(): Int = R.layout.activity_main

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        StatusBarManager.setColor(window, getColorPro(R.color.white))
        StatusBarManager.setLightMode(window)
        ToolBarManager.with(this, getContentView())
                .setNavigationIcon(R.drawable.ic_nav_back_white)
                .leftMenuText {
                    text = "呵呵哒"
                }
                .bg {
                    setBackgroundColor(getColorPro(R.color.white))
                }
                .title {
                    text = "测试ToolBar"
                    setTextColor(getColorPro(R.color.white))
                }
                .rightMenuIcon {
                    setImageResource(R.drawable.ic_nav_back_white)
                    setOnClickListenerPro {
                        adapter.setDataToAdapter(arrayListOf("1", "2", "3"))
                    }
                }
//                .rightMenuText {
//                    text="测试"
//                    setTextColor(getColorPro(R.color.white))
//                }
//                .rightMenuText2 {
//                    text="通知"
//                    setTextColor(getColorPro(R.color.white))
//                }

        recy_title.init()
        recy_title.adapter = adapter

        adapter.setDataToAdapter(arrayListOf("1", "2"))


        //增加 TextView显示 图片 CenterAlignImageSpan
        tv_test.setSpannableString(
                SpannableParam("这是 "),
                SpannableParam("大文字 ", arrayListOf(
                        AbsoluteSizeSpan(20, true)
                )),
                SpannableParam("图片", arrayListOf(
                        CenterAlignImageSpan(getContext().getDrawablePro(R.drawable.universal_ic_edit_clear))
                ))
        )
        //增加 动态设置TextView的drawables
        tv_test.setDrawables(
                leftImage = R.drawable.address_ic_edit,
                topImage = R.drawable.address_ic_edit,
                rightImage = R.drawable.address_ic_edit,
                bottomImage = R.drawable.address_ic_edit,
                drawablePadding = getContext().dip2px(8f)
        )
    }
}
