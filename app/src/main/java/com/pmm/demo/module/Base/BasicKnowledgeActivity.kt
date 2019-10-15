package com.pmm.demo.module.Base

import android.os.Bundle
import com.weimu.app.universalview.R
import com.pmm.demo.base.BaseViewActivity
import com.pmm.demo.module.fragment.viewpager.ViewPagerActivity
import com.pmm.demo.module.main.CategoryB
import com.pmm.demo.module.main.CategoryListAdapter
import com.pmm.ui.ktx.init
import com.pmm.ui.ktx.openActivity
import com.pmm.ui.ktx.click
import kotlinx.android.synthetic.main.activity_universal_list.*
import kotlinx.android.synthetic.main.include_recyclerview.recyclerView

/**
 * 基础知识
 */
class BasicKnowledgeActivity : BaseViewActivity() {

    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }

    override fun getLayoutResID(): Int = R.layout.activity_universal_list

    override fun afterViewAttach(savedInstanceState: Bundle?) {

        mToolbar.with(this)
                .centerTitle {
                    this.text = "基础知识"
                }.navigationIcon {
                    this.click { onBackPressed() }
                }

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
