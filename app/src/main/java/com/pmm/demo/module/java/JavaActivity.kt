package com.pmm.demo.module.java

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivityV2
import com.pmm.demo.base.CategoryB
import com.pmm.demo.base.CategoryListAdapter
import com.pmm.demo.databinding.ActivityJavaBinding
import com.pmm.demo.module.java.pattern.PatternActivity
import com.pmm.ui.ktx.click
import com.pmm.ui.ktx.init
import com.pmm.ui.ktx.openActivity
import com.pmm.ui.ktx.visible

/**
 * kotlin的一些基础Demo
 */
class JavaActivity : BaseViewActivityV2(R.layout.activity_java) {
    private val mVB by viewBinding(ActivityJavaBinding::bind, R.id.container)
    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }


    override fun afterViewAttach(savedInstanceState: Bundle?) {
        mVB.mToolBar.with(this)
                .navigationIcon {
                    this.setImageResource(R.drawable.universal_arrow_back_white)
                    this.click { onBackPressed() }
                }
                .centerTitle {
                    this.text = "Java"
                }
                .divider {
                    this.visible()
                }

        initRecy()
    }


    fun initRecy() {
        adapter.onItemClick = { item, position ->
            when (position) {
                0 -> {
                    //正则表达式
                    openActivity<PatternActivity>()
                }
            }
        }
        mVB.includeList.recyclerView.init()
        mVB.includeList.recyclerView.adapter = adapter



        category.add(CategoryB("正则表达式", "各种识别模式"))

        adapter.setDataToAdapter(category)
    }

}
