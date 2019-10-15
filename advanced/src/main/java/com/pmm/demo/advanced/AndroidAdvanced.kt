package com.pmm.demo.advanced

import android.os.Bundle
import com.pmm.demo.base.BaseViewActivity
import com.pmm.demo.base.CategoryB
import com.pmm.demo.base.CategoryListAdapter
import com.pmm.demo.base.initToolBar
import com.pmm.metro.Metro
import com.pmm.metro.Station
import com.pmm.ui.ktx.init
import kotlinx.android.synthetic.main.activity_android_advanced.*

@Station("/android/advanced")
class AndroidAdvanced : BaseViewActivity() {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }

    override fun getLayoutResID() = R.layout.activity_android_advanced

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initToolBar()
        initRecy()
    }


    fun initRecy() {
        adapter.onItemClick = { item, position ->
            when (position) {
                0 -> {
                    Metro.with(this).path("/android/advanced/binder")
                            .attribute("title", item.primaryTitle)
                            .go()
                }
            }
        }
        recyclerView.init()
        recyclerView.adapter = adapter



        category.add(CategoryB("Android Binder机制", "Service的调用"))

        adapter.setDataToAdapter(category)
    }

}
