package com.pmm.demo.module.lib3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivity
import com.pmm.demo.module.lib3.eventbus.EventBusActivity
import com.pmm.demo.module.lib3.materialdialog.MaterialDialogActivity
import com.pmm.demo.base.CategoryB
import com.pmm.demo.base.CategoryListAdapter
import com.pmm.demo.base.initToolBar
import com.pmm.ui.core.recyclerview.decoration.LinearItemDecoration
import com.pmm.ui.ktx.dip2px
import com.pmm.ui.ktx.init
import kotlinx.android.synthetic.main.include_recyclerview.recyclerView

class Lib3Activity : BaseViewActivity() {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, Lib3Activity::class.java)
        }
    }

    override fun getLayoutResID() = R.layout.activity_universal_list


    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initToolBar("实用第三方库")
        initRecy()
    }


    fun initRecy() {
        adapter.onItemClick = { item, position ->
            when (position) {
                0 -> {
                    //MaterialDialog
                    startActivity(MaterialDialogActivity.newIntent(this))
                }
                1 -> {
                    //EventBus
                    startActivity(EventBusActivity.newIntent(this))

                }
            }
        }
        recyclerView.init()
        recyclerView.addItemDecoration(LinearItemDecoration(
                context = this,
                dividerSize = dip2px(16f)
        ))
        recyclerView.adapter = adapter



        category.add(CategoryB("MaterialDialog", "MD,Dialog"))
        category.add(CategoryB("EventBus", "事件总线，事件分发"))

        adapter.setDataToAdapter(category)
    }
}
