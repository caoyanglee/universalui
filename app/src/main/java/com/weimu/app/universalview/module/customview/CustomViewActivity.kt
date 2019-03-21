package com.weimu.app.universalview.module.customview

import android.os.Bundle
import android.widget.TextView
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.app.universalview.module.datapersistence.cache.CacheActivity
import com.weimu.app.universalview.module.main.CategoryB
import com.weimu.app.universalview.module.main.CategoryListAdapter
import com.weimu.universalview.core.toolbar.ToolBarManager
import com.weimu.universalview.ktx.init
import com.weimu.universalview.ktx.setDrawables
import com.weimu.universalview.ktx.setTextColorV2
import kotlinx.android.synthetic.main.include_recyclerview.*

class CustomViewActivity : BaseViewActivity() {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(getContext()) }

    override fun getLayoutResID(): Int = R.layout.activity_custom_view

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        ToolBarManager.with(this, getContentView())
                .leftMenuIcon {
                    this.setImageResource(R.drawable.universal_arrow_back_white)
                }
                .title {
                    this.text = "数据持久化"
                    this.setTextColorV2(R.color.white)
                }
        initRecy()

        TextView(getContext()).setDrawables()
    }

    fun initRecy() {
        adapter.onItemClick = { item, position ->
            when (position) {
                0 -> {
                    //缓存
                    startActivity(CacheActivity.newIntent(getContext()))
                }
            }
        }
        recyclerView.init()
        recyclerView.adapter = adapter



        category.add(CategoryB("tableView", "tableView"))

        adapter.setDataToAdapter(category)
    }
}
