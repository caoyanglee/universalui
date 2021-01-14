package com.pmm.demo.advanced

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pmm.demo.advanced.databinding.ActivityAndroidAdvancedBinding
import com.pmm.demo.base.*
import com.pmm.metro.Metro
import com.pmm.metro.annotatoin.Station
import com.pmm.ui.ktx.init

@Station("/android/advanced")
class AndroidAdvancedAy : BaseViewActivityV2(R.layout.activity_android_advanced) {
    private val mVB by viewBinding(ActivityAndroidAdvancedBinding::bind, R.id.container)

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initToolBar()
        initRecy()
    }


    fun initRecy() {
        adapter.onItemClick = { item, position ->
            when (position) {
                0 -> {
                    Metro.with(this).path("/android/advanced/binder")
                            .put("title", item.primaryTitle)
                            .go()
                }
            }
        }
        mVB.recyclerView.init()
        mVB.recyclerView.adapter = adapter
        category.add(CategoryB("Android Binder机制", "Service的调用"))
        adapter.setDataToAdapter(category)
    }

}
