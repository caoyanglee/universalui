package com.pmm.demo.module.Base

import android.os.Bundle
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivity
import com.pmm.demo.module.fragment.viewpager.ViewPagerActivity
import com.pmm.demo.base.CategoryB
import com.pmm.demo.base.CategoryListAdapter
import com.pmm.demo.base.initToolBar
import com.pmm.ui.ktx.init
import com.pmm.ui.ktx.openActivity
import kotlinx.android.synthetic.main.include_recyclerview.recyclerView

/**
 * 基础知识
 */
class BasicKnowledgeActivity : BaseViewActivity() {

    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }

    override fun getLayoutResID(): Int = R.layout.activity_universal_list

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initToolBar("基础知识")

        recyclerView.init()
        recyclerView.adapter = adapter

        val category = arrayListOf<CategoryB>(
                CategoryB("Fragment", "Fragment的基础使用")
        )
        adapter.setDataToAdapter(category)

        adapter.onItemClick = { item, position ->
            when (item.primaryTitle) {
                "Fragment" -> {
                    openActivity<ViewPagerActivity>()
                }
            }
        }

    }
}
