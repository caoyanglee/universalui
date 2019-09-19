package com.weimu.app.universalview.module.kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.app.universalview.module.kotlin.coroutines.CoroutineActivity
import com.weimu.app.universalview.module.main.CategoryB
import com.weimu.app.universalview.module.main.CategoryListAdapter
import com.weimu.universalview.ktx.init
import com.weimu.universalview.ktx.setOnClickListenerPro
import kotlinx.android.synthetic.main.activity_universal_list.*
import kotlinx.android.synthetic.main.include_recyclerview.recyclerView

/**
 * kotlin的一些基础Demo
 */
class KotlinActivity : BaseViewActivity() {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(getContext()) }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, KotlinActivity::class.java)
        }
    }

    override fun getLayoutResID() = R.layout.activity_universal_list

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        mToolbar.with(this)
                .centerTitle { this.text = "Kotlin" }
                .navigationIcon { this.setOnClickListenerPro { onBackPressed() } }
        initRecy()
    }


    fun initRecy() {
        adapter.onItemClick = { item, position ->
            when (position) {
                0 -> {
                    //协程
                    startActivity(CoroutineActivity.newIntent(getContext()))
                }
            }
        }
        recyclerView.init()
        recyclerView.adapter = adapter



        category.add(CategoryB("协程", "异步处理"))

        adapter.setDataToAdapter(category)
    }

}
