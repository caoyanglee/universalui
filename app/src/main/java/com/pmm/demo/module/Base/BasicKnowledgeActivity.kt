package com.pmm.demo.module.Base

import android.os.Bundle
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivity
import com.pmm.demo.base.CategoryB
import com.pmm.demo.base.CategoryListAdapter
import com.pmm.demo.base.initToolBar
import com.pmm.metro.Metro
import com.pmm.ui.core.recyclerview.decoration.LinearItemDecoration
import com.pmm.ui.core.toolbar.StatusBarManager
import com.pmm.ui.ktx.click
import com.pmm.ui.ktx.dip2px
import com.pmm.ui.ktx.init
import com.pmm.ui.ktx.toast
import kotlinx.android.synthetic.main.include_recyclerview.recyclerView

/**
 * 基础知识
 */
class BasicKnowledgeActivity : BaseViewActivity() {

    private val adapter: CategoryListAdapter by lazy { CategoryListAdapter(this) }

    override fun getLayoutResID(): Int = R.layout.activity_universal_list

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initToolBar("基础知识").apply {
            StatusBarManager.setTransparencyBar(window)
            StatusBarManager.setLightMode(window, true)
            this.showStatusView = true
            this.menuIcon1 {
                this.setImageResource(R.drawable.ic_label_gray_24dp)
                this.click {
                    toast("哈哈1")
                }
            }

            this.menuIcon2 {
                this.setImageResource(R.drawable.ic_label_gray_24dp)
                this.click {
                    toast("哈哈2")
                }
            }
        }

        recyclerView.init()
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(LinearItemDecoration(
                context = this,
                dividerSize = dip2px(16f)
        ))

        val category = arrayListOf<CategoryB>(
                CategoryB("Fragment", "Fragment的基础使用"),
                CategoryB("Snackbar", "Snackbar的基础使用")
        )
        adapter.setDataToAdapter(category)

        adapter.onItemClick = { item, position ->
            when (item.primaryTitle) {
                "Fragment" -> {
//                    openActivity<ViewPagerActivity>()
                    Metro.with(this).path("/fragment/viewpager").go()
                }
                "Snackbar" -> {
//                    openActivity<ViewPagerActivity>()
                    Metro.with(this).path("/snackbar").go()
                }
            }
        }

    }
}
