package com.weimu.app.universalview.module.customview

import android.os.Bundle
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.app.universalview.module.main.CategoryB
import com.weimu.app.universalview.module.main.CategoryListAdapter
import com.weimu.universalview.ktx.*
import kotlinx.android.synthetic.main.activity_universal_list.*
import kotlinx.android.synthetic.main.include_recyclerview.recyclerView

class CustomViewActivity : BaseViewActivity() {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(getContext()) }

    override fun getLayoutResID(): Int = R.layout.activity_universal_list

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        mToolbar.with(this).centerTitle {
            this.text = "自定义视图"
        }.navigationIcon {
            this.setOnClickListenerPro { onBackPressed() }
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
