package com.weimu.app.universalview.module.lib3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.app.universalview.module.lib3.eventbus.EventBusActivity
import com.weimu.app.universalview.module.lib3.materialdialog.MaterialDialogActivity
import com.weimu.app.universalview.module.main.CategoryB
import com.weimu.app.universalview.module.main.CategoryListAdapter
import com.weimu.universalview.core.toolbar.ToolBarManager
import com.weimu.universalview.ktx.init
import kotlinx.android.synthetic.main.include_recyclerview.*

class Lib3Activity : BaseViewActivity() {

    private val category = arrayListOf<CategoryB>()
    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(getContext()) }


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, Lib3Activity::class.java)
        }
    }

    override fun getLayoutResID() = R.layout.activity_lib3


    override fun afterViewAttach(savedInstanceState: Bundle?) {
        ToolBarManager.with(this, getContentView())
                .setTitle("实用第三方库")
                .setTitleColor(R.color.white)
                .setNavigationIcon(R.drawable.universal_arrow_back_white)
        initRecy()
    }


    fun initRecy() {
        adapter.onItemClick = { item, position ->
            when (position) {
                0 -> {
                    //MaterialDialog
                    startActivity(MaterialDialogActivity.newIntent(getContext()))
                }
                1 -> {
                    //EventBus
                    startActivity(EventBusActivity.newIntent(getContext()))

                }
            }
        }
        recyclerView.init()
        recyclerView.adapter = adapter



        category.add(CategoryB("MaterialDialog", "MD,Dialog"))
        category.add(CategoryB("EventBus", "事件总线，事件分发"))

        adapter.setDataToAdapter(category)
    }
}
