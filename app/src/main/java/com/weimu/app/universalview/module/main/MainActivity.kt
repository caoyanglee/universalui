package com.weimu.app.universalview.module.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.app.universalview.module.datapersistence.DataPersistenceActivity
import com.weimu.app.universalview.module.java.JavaActivity
import com.weimu.app.universalview.module.kotlin.KotlinActivity
import com.weimu.app.universalview.module.lib3.Lib3Activity
import com.weimu.universalib.ktx.dip2px
import com.weimu.universalib.ktx.toast
import com.weimu.universalview.core.recyclerview.decoration.LinearItemDecoration
import com.weimu.universalview.core.toolbar.StatusBarManager
import com.weimu.universalview.ktx.init
import com.weimu.universalview.ktx.setOnClickListenerPro
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_recyclerview.*

class MainActivity : BaseViewActivity() {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(getContext()) }


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun getLayoutResID() = R.layout.activity_main


    override fun afterViewAttach(savedInstanceState: Bundle?) {
        //ToolBarManager.with(this, contentView).setMTitle("通用Demo")
        toastSuccess("欢迎来到通用UI库")
        initRecy()

        StatusBarManager.setTransparencyBar(window)
        mToolbar.with(this)
                .apply {
                    //ToolBar本身的设置
                    showStatusView = true
                    toolbarHeight = 48f
                    setBackgroundResource(R.color.colorPrimary)
                }
                .navigationIcon {
                    //导航图标
                    setImageResource(R.drawable.universal_arrow_back_white)
                }
                .navigationText {
                    //导航文本
                    text = "登录&注册"
                    setOnClickListenerPro {

                    }
                }
                .centerTitle {
                    //中间标题
                    text = "测试标题"
                }
                .menuIcon1 {
                    //菜单图标1
                    setImageResource(R.drawable.universal_arrow_back_white)
                    setOnClickListenerPro {
                        toast("菜单1")
                    }
                }
                .menuIcon2 {
                    //菜单图标2
                    setImageResource(R.drawable.universal_arrow_back_white)
                    setOnClickListenerPro {
                        toast("菜单1")
                    }
                }
                .menuText1 {
                    //菜单文本1
                    text = "菜单1"
                    setOnClickListenerPro {

                    }
                }
                .menuText2 {
                    //菜单文本2
                    text = "菜单2"
                    setOnClickListenerPro {

                    }
                }
    }


    fun initRecy() {
        adapter.onItemClick = { item, position ->
            when (position) {
                0 -> {
                    //java
                    startActivity(JavaActivity.newIntent(getContext()))
                }
                1 -> {
                    //kotlin
                    startActivity(KotlinActivity.newIntent(getContext()))
                }
                7 -> {
                    //data persistence
                    startActivity(DataPersistenceActivity.newIntent(getContext()))
                }
                9 -> {
                    //lib3
                    startActivity(Lib3Activity.newIntent(getContext()))
                }
            }
        }
        recyclerView.init()
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(LinearItemDecoration(
                context = this,
                dividerSize = dip2px(16f)
        ))
        recyclerView.setPadding(0, dip2px(140f), 0, dip2px(16f))
        category.add(CategoryB("Java", "Java的一些基础Demo"))
        category.add(CategoryB("Kotlin", "Kotlin的一些基础Demo"))
        category.add(CategoryB("RxJava", "RxJava的Demo集合"))
        category.add(CategoryB("JetPack", "Google官方推荐的工具集"))
        category.add(CategoryB("CustomView", "每个安卓开发的必备技术"))
        category.add(CategoryB("Animation", "好的动画总能打动人心"))
        category.add(CategoryB("NDK开发", "Native develop kit"))
        category.add(CategoryB("数据持久化", "数据持久化的一些Demo"))
        category.add(CategoryB("性能优化", "必备~"))
        category.add(CategoryB("实用的第三方库", "必备~"))

        adapter.setDataToAdapter(category)


    }

}
