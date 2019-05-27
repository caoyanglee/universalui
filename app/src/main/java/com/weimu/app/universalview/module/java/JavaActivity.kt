package com.weimu.app.universalview.module.java

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.app.universalview.module.java.pattern.PatternActivity
import com.weimu.app.universalview.module.main.CategoryB
import com.weimu.app.universalview.module.main.CategoryListAdapter
import com.weimu.universalview.core.toolbar.StatusBarManager
import com.weimu.universalview.core.toolbar.ToolBarManager
import com.weimu.universalview.ktx.init
import com.weimu.universalview.ktx.setOnClickListenerPro
import kotlinx.android.synthetic.main.activity_java.*
import kotlinx.android.synthetic.main.include_recyclerview.*

/**
 * kotlin的一些基础Demo
 */
class JavaActivity : BaseViewActivity() {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(getContext()) }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, JavaActivity::class.java)
        }
    }

    override fun getLayoutResID() = R.layout.activity_java

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        mToolBar.apply { }
                .navigationIcon {
                    this.setImageResource(R.drawable.universal_arrow_back_white)
                    this.setOnClickListenerPro { onBackPressed() }
                }
                .centerTitle {
                    this.text = "Java"
                }

        if (mToolBar.getToolBarBgColor()== Color.BLACK){
            Log.d("weimu","测试成功")
        }


        initRecy()
    }


    fun initRecy() {
        adapter.onItemClick = { item, position ->
            when (position) {
                0 -> {
                    //正则表达式
                    startActivity(PatternActivity.newIntent(getContext()))
                }
            }
        }
        recyclerView.init()
        recyclerView.adapter = adapter



        category.add(CategoryB("正则表达式", "各种识别模式"))

        adapter.setDataToAdapter(category)
    }

}
