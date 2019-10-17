package com.pmm.demo.module.kotlin

import android.os.Bundle
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivity
import com.pmm.demo.base.initToolBar
import com.pmm.demo.module.kotlin.coroutines.CoroutineActivity
import com.pmm.demo.base.CategoryB
import com.pmm.demo.base.CategoryListAdapter
import com.pmm.ui.ktx.init
import kotlinx.android.synthetic.main.include_recyclerview.recyclerView

/**
 * kotlin的一些基础Demo
 */
class KotlinActivity : BaseViewActivity() {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }

    override fun getLayoutResID() = R.layout.activity_universal_list

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initToolBar("Kotlin")
        initRecy()
    }


    fun initRecy() {
        adapter.onItemClick = { item, position ->
            when (position) {
                0 -> {
                    //协程
                    startActivity(CoroutineActivity.newIntent(this))
                }
            }
        }
        recyclerView.init()
        recyclerView.adapter = adapter



        category.add(CategoryB("协程", "异步处理"))

        adapter.setDataToAdapter(category)
    }

}
