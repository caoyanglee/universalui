package com.weimu.app.universalview.module.datapersistence

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.app.universalview.module.datapersistence.cache.CacheActivity
import com.weimu.app.universalview.module.main.CategoryB
import com.weimu.app.universalview.module.main.CategoryListAdapter
import com.weimu.universalview.core.toolbar.ToolBarManager
import com.weimu.universalview.ktx.init
import kotlinx.android.synthetic.main.include_recyclerview.*

/**
 * 数据持久化
 */
class DataPersistenceActivity : BaseViewActivity() {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(getContext()) }

    companion object {
        fun newIntent(context: Context) = Intent(context, DataPersistenceActivity::class.java)
    }

    override fun getLayoutResID(): Int = R.layout.activity_data_persistence


    override fun afterViewAttach(savedInstanceState: Bundle?) {
        ToolBarManager.with(this, getContentView())
                .setTitle("数据持久化")
                .setTitleColor(R.color.white)
                .setNavigationIcon(R.drawable.universal_arrow_back_white)
        initRecy()
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



        category.add(CategoryB("缓存", "各种缓存操作"))

        adapter.setDataToAdapter(category)
    }
}
