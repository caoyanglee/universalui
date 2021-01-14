package com.pmm.demo.module.datapersistence

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pmm.demo.R
import com.pmm.demo.base.*
import com.pmm.demo.module.datapersistence.cache.CacheActivity
import com.pmm.demo.databinding.ActivityUniversalListBinding
import com.pmm.ui.ktx.init
import com.pmm.ui.ktx.openActivity

/**
 * 数据持久化
 */
class DataPersistenceActivity : BaseViewActivityV2(R.layout.activity_universal_list) {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }
    private val mVB by viewBinding(ActivityUniversalListBinding::bind, R.id.container)

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initToolBar("数据持久化")
        mVB.recyclerView.init()
        mVB.recyclerView.adapter = adapter

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
