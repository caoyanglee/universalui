package com.weimu.app.universalview.module.datapersistence.cache

import android.os.Bundle
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.pmm.ui.ktx.clearAllCache
import com.pmm.ui.ktx.getTotalCacheSize
import com.pmm.ui.ktx.setOnClickListenerPro
import kotlinx.android.synthetic.main.activity_cache.*

class CacheActivity : BaseViewActivity() {

    override fun getLayoutResID(): Int = R.layout.activity_cache

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        super.afterViewAttach(savedInstanceState)

        mToolbar.with(this)
                .centerTitle {
                    this.text = "缓存"
                }.navigationIcon {
                    this.setOnClickListenerPro { onBackPressed() }
                }

        tv_cache.text = getTotalCacheSize()
        tv_cache.setOnClickListenerPro {
            clearAllCache()
        }
    }
}
