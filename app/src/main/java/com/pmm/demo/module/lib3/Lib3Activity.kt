package com.pmm.demo.module.lib3

import android.content.Intent
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pmm.demo.R
import com.pmm.demo.base.*
import com.pmm.demo.databinding.ActivityUniversalListBinding
import com.pmm.demo.module.lib3.eventbus.EventBusActivity
import com.pmm.demo.module.lib3.materialdialog.MaterialDialogActivity
import com.pmm.ui.core.recyclerview.decoration.LinearItemDecoration
import com.pmm.ui.ktx.dip2px
import com.pmm.ui.ktx.init
import com.pmm.ui.ktx.openActivity
import com.pmm.ui.ktx.toast
import org.greenrobot.eventbus.EventBus

class Lib3Activity : BaseViewActivityV2(R.layout.activity_universal_list) {

    private val category = arrayListOf<CategoryB>()
    private val mAdapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }
    private val mVB by viewBinding(ActivityUniversalListBinding::bind, R.id.container)


    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initToolBar("实用第三方库")
        initRecy()
    }


    fun initRecy() {
        mAdapter.onItemClick = { item, position ->
            when (position) {
                0 -> {
                    //MaterialDialog
                    openActivity<MaterialDialogActivity>()
                }
                1 -> {
                    //EventBus
                    openActivity<EventBusActivity>()

                }
            }
        }
        mVB.recyclerView.apply {
            this.init()
            this.addItemDecoration(LinearItemDecoration(
                    context = this@Lib3Activity,
                    dividerSize = dip2px(16f)
            ))
            this.adapter = mAdapter
        }
        category.add(CategoryB("MaterialDialog", "MD,Dialog"))
        category.add(CategoryB("EventBus", "事件总线，事件分发"))
        mAdapter.setDataToAdapter(category)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}
