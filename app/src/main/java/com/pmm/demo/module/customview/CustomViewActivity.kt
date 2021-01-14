package com.pmm.demo.module.customview

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivityV2
import com.pmm.demo.base.CategoryB
import com.pmm.demo.base.CategoryListAdapter
import com.pmm.demo.base.initToolBar
import com.pmm.demo.databinding.ActivityUniversalListBinding
import com.pmm.ui.ktx.init

class CustomViewActivity : BaseViewActivityV2(R.layout.activity_universal_list) {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }
    private val mVB by viewBinding(ActivityUniversalListBinding::bind, R.id.container)

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initToolBar("自定义视图")

        mVB.recyclerView.init()
        mVB.recyclerView.adapter = adapter

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
