package com.weimu.app.universalview.module.datapersistence

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.app.universalview.module.datapersistence.cache.CacheActivity
import com.weimu.app.universalview.module.fragment.viewpager.ViewPagerActivity
import com.weimu.app.universalview.module.main.CategoryB
import com.weimu.app.universalview.module.main.CategoryListAdapter
import com.weimu.universalview.core.toolbar.ToolBarManager
import com.weimu.universalview.ktx.init
import com.weimu.universalview.ktx.openActivity
import kotlinx.android.synthetic.main.activity_universal_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_recyclerview.recyclerView

/**
 * 数据持久化
 */
class DataPersistenceActivity : BaseViewActivity() {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(getContext()) }


    override fun getLayoutResID(): Int = R.layout.activity_universal_list


    override fun afterViewAttach(savedInstanceState: Bundle?) {

        mToolbar.with(this).centerTitle {
            this.text = "数据持久化"
        }

        recyclerView.init()
        recyclerView.adapter = adapter

        val category = arrayListOf<CategoryB>(
                CategoryB("缓存", "各种缓存操作")
        )
        adapter.setDataToAdapter(category)

        adapter.onItemClick = { item, position ->
            when (item.primaryTitle) {
                "缓存" -> {
                    openActivity<CacheActivity>()
                }
            }
        }

    }

}
