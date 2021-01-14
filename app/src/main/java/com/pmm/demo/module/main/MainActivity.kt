package com.pmm.demo.module.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.logger.Logger
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivityV2
import com.pmm.demo.base.CategoryB
import com.pmm.demo.base.CategoryListAdapter
import com.pmm.demo.databinding.ActivityMainBinding
import com.pmm.demo.module.base.BasicKnowledgeActivity
import com.pmm.demo.module.datapersistence.DataPersistenceActivity
import com.pmm.demo.module.java.JavaActivity
import com.pmm.demo.module.kotlin.KotlinActivity
import com.pmm.demo.module.lib3.Lib3Activity
import com.pmm.demo.module.test.TestActivity
import com.pmm.metro.Metro
import com.pmm.ui.core.recyclerview.decoration.LinearItemDecoration
import com.pmm.ui.helper.security.AESHelper
import com.pmm.ui.ktx.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseViewActivityV2(R.layout.activity_main) {

    private val category = arrayListOf<CategoryB>()
    private val mAdapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }
    private val mVM by lazy { ViewModelProvider(this).get(MainVM::class.java) }

    // Without reflection
    private val mVB by viewBinding(ActivityMainBinding::bind, R.id.container)

    override fun afterViewAttach(savedInstanceState: Bundle?) {

        //ToolBarManager.with(this, contentView).setMTitle("通用Demo")

        MainScope().launch {
            delay(300)
            Snackbar.make(getContentView(), "欢迎来到通用UI库", Snackbar.LENGTH_SHORT).showMD2()
        }

        mVB.tvTitle.click {
            openActivity<TestActivity>()
        }

        initRecy()

        val result = AESHelper.encrypt("我是谁", "fhdsjfkdlsjfjdsl")
        Logger.e("result=$result")

        val origin = AESHelper.decrypt("$result", "fhdsjfkdlsjfjdsl")
        Logger.e("origin=$origin")
    }


    fun initRecy() {
        mAdapter.onItemClick = { item, position ->
            when (item.primaryTitle) {
                "Android基础" -> {
                    openActivity<BasicKnowledgeActivity>()
                }
                "Android进阶" -> {
                    Metro.with(this).path("/android/advanced")
                            .put("title", item.primaryTitle)
                            .go()
                }
                "Java" -> {
                    //java
                    openActivity<JavaActivity>()
                }
                "Kotlin" -> {
                    //kotlin
                    openActivity<KotlinActivity>()
                }
                "数据持久化" -> {
                    //data persistence
                    openActivity<DataPersistenceActivity>()
                }
                "实用的第三方库" -> {
                    //lib3
                    openActivity<Lib3Activity>()
                }
            }
        }
        mVB.includeList.recyclerView.apply {
            this.init()
            this.setPadding(0, dip2px(10f), 0, getNavigationBarHeight())
            this.adapter = mAdapter
            this.addItemDecoration(LinearItemDecoration(
                    context = this@MainActivity,
                    dividerSize = dip2px(16f)
            ))
        }

        category.add(CategoryB("Android基础", "Android的一些基础Demo"))
        category.add(CategoryB("Android进阶", "Android的一些进阶Demo"))
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

        mAdapter.setDataToAdapter(category)


    }

}
