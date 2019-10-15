package com.pmm.demo.module.java

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.weimu.app.universalview.R
import com.pmm.demo.base.BaseViewActivity
import com.pmm.demo.module.java.pattern.PatternActivity
import com.pmm.demo.base.CategoryB
import com.pmm.demo.base.CategoryListAdapter
import com.pmm.ui.ktx.init
import com.pmm.ui.ktx.click
import com.pmm.ui.ktx.visible
import kotlinx.android.synthetic.main.activity_java.*
import kotlinx.android.synthetic.main.include_recyclerview.*

/**
 * kotlin的一些基础Demo
 */
class JavaActivity : BaseViewActivity() {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, JavaActivity::class.java)
        }
    }

    override fun getLayoutResID() = R.layout.activity_java

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        mToolBar.with(this)
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

        if (mToolBar.getToolBarBgColor() == Color.BLACK) {
            Log.d("weimu", "测试成功")
        }


        initRecy()
    }


    fun initRecy() {
        adapter.onItemClick = { item, position ->
            when (position) {
                0 -> {
                    //正则表达式
                    startActivity(PatternActivity.newIntent(this))
                }
            }
        }
        recyclerView.init()
        recyclerView.adapter = adapter



        category.add(CategoryB("正则表达式", "各种识别模式"))

        adapter.setDataToAdapter(category)
    }

}
