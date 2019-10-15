package com.pmm.demo.module.customview

import android.os.Bundle
import com.pmm.ui.ktx.init
import com.weimu.app.universalview.R
import com.pmm.demo.base.BaseViewActivity
import com.pmm.demo.base.initToolBar
import com.pmm.demo.base.CategoryB
import com.pmm.demo.base.CategoryListAdapter
import kotlinx.android.synthetic.main.include_recyclerview.recyclerView

class CustomViewActivity : BaseViewActivity() {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }

    override fun getLayoutResID(): Int = R.layout.activity_universal_list

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initToolBar("自定义视图")

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
