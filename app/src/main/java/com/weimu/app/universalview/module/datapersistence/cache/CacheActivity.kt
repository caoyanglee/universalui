package com.weimu.app.universalview.module.datapersistence.cache

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.universalview.core.toolbar.ToolBarManager
import com.weimu.universalview.ktx.clearAllCache
import com.weimu.universalview.ktx.getTotalCacheSize
import com.weimu.universalview.ktx.setOnClickListenerPro
import kotlinx.android.synthetic.main.activity_cache.*

class CacheActivity : BaseViewActivity() {

    companion object {
        fun newIntent(context: Context) = Intent(context, CacheActivity::class.java)
    }

    override fun getLayoutResID(): Int = R.layout.activity_cache

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        super.afterViewAttach(savedInstanceState)

        ToolBarManager.with(this, getContentView())
                .setTitle("缓存")
                .setTitleColor(R.color.white)
                .setNavigationIcon(R.drawable.universal_arrow_back_white)


        tv_cache.text = getTotalCacheSize()
        tv_cache.setOnClickListenerPro {
            clearAllCache()
        }
    }
}
