package com.pmm.demo.module.kotlin

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pmm.demo.R
import com.pmm.demo.base.*
import com.pmm.demo.databinding.ActivityUniversalListBinding
import com.pmm.demo.module.kotlin.coroutines.CoroutineActivity
import com.pmm.ui.ktx.init
import com.pmm.ui.ktx.openActivity

/**
 * kotlin的一些基础Demo
 */
class KotlinActivity : BaseViewActivityV2(R.layout.activity_universal_list) {

    private val category = arrayListOf<CategoryB>()
    private val mAdapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }
    private val mVB by viewBinding(ActivityUniversalListBinding::bind, R.id.container)

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initToolBar("Kotlin")
        initRecy()
    }


    fun initRecy() {
        mAdapter.onItemClick = { item, position ->
            when (position) {
                0 -> {
                    //协程
                    openActivity<CoroutineActivity>()
                }
            }
        }
        mVB.recyclerView.init()
        mVB.recyclerView.adapter = mAdapter



        category.add(CategoryB("协程", "异步处理"))

        mAdapter.setDataToAdapter(category)
    }

}
