package com.pmm.demo.module.customview

import android.os.Bundle
import com.pmm.ui.ktx.init
import com.pmm.ui.ktx.click
import com.weimu.app.universalview.R
import com.pmm.demo.base.BaseViewActivity
import com.pmm.demo.module.main.CategoryB
import com.pmm.demo.module.main.CategoryListAdapter
import kotlinx.android.synthetic.main.activity_universal_list.*
import kotlinx.android.synthetic.main.include_recyclerview.recyclerView

class CustomViewActivity : BaseViewActivity() {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }

    override fun getLayoutResID(): Int = R.layout.activity_universal_list

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        mToolbar.with(this).centerTitle {
            this.text = "自定义视图"
        }.navigationIcon {
            this.click { onBackPressed() }
        }

        recyclerView.init()
        recyclerView.adapter = adapter

        val category = arrayListOf<CategoryB>(
                CategoryB("tableView", "tableView")
        )
        adapter.setDataToAdapter(category)

        adapter.onItemClick = { item, position ->
            when (item.primaryTitle) {
                "tableView" -> {

                }
            }
        }
    }
}
