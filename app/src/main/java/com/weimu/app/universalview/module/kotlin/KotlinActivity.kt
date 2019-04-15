package com.weimu.app.universalview.module.kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.app.universalview.module.kotlin.coroutines.CoroutineActivity
import com.weimu.app.universalview.module.main.CategoryB
import com.weimu.app.universalview.module.main.CategoryListAdapter
import com.weimu.universalview.core.toolbar.ToolBarManager
import com.weimu.universalview.ktx.init
import com.weimu.universalview.ktx.setOnClickListenerPro
import kotlinx.android.synthetic.main.include_recyclerview.*

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

    override fun getLayoutResID() = R.layout.activity_kotlin

    override fun afterViewAttach(savedInstanceState: Bundle?) {
//        ToolBarManager.with(this, contentView)
//                .setTitle("Kotlin")
//                .setTitleColor(R.color.white)
//                .setNavigationIcon(R.drawable.universal_arrow_back_white)


        ToolBarManager.with(this, getContentView())
                //中间标题
                .title {
                    this.textSize = 16f
                    this.setTextColor(ContextCompat.getColor(context, R.color.white))
                    this.text = "这是中间标题"
                }
                //左侧文本
                .leftMenuText {
                    this.textSize = 16f
                    this.setTextColor(ContextCompat.getColor(context, R.color.white))
                    this.text = "关闭"
                    this.setOnClickListenerPro {
                        toastSuccess("呵呵哒，要关闭了")
                    }
                }
                //右侧第一个按钮
                .rightMenuText {
                    this.textSize = 16f
                    this.setTextColor(ContextCompat.getColor(context, R.color.white))
                    this.text = "按钮1"
                    this.setOnClickListenerPro {
                        toastSuccess("呵呵哒，干什么1？")
                    }
                }
                //右侧第二个按钮
                .rightMenuText2 {
                    this.textSize = 16f
                    this.setTextColor(ContextCompat.getColor(context, R.color.white))
                    this.text = "按钮2"
                    this.setOnClickListenerPro {
                        toastSuccess("呵呵哒，干什么2？")
                    }
                }



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
