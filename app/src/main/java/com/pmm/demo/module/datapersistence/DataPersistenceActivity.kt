package com.pmm.demo.module.datapersistence

import android.os.Bundle
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivity
import com.pmm.demo.module.datapersistence.cache.CacheActivity
import com.pmm.demo.base.CategoryB
import com.pmm.demo.base.CategoryListAdapter
import com.pmm.demo.base.initToolBar
import com.pmm.ui.ktx.init
import com.pmm.ui.ktx.openActivity
import kotlinx.android.synthetic.main.include_recyclerview.recyclerView

/**
 * 数据持久化
 */
class DataPersistenceActivity : BaseViewActivity() {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }


    override fun getLayoutResID(): Int = R.layout.activity_universal_list


    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initToolBar("数据持久化")
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
