package com.pmm.demo.module.datapersistence.cache

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivityV2
import com.pmm.demo.base.initToolBarWithBack
import com.pmm.demo.databinding.ActivityCacheBinding
import com.pmm.ui.ktx.clearAllCache
import com.pmm.ui.ktx.click
import com.pmm.ui.ktx.getTotalCacheSize

class CacheActivity : BaseViewActivityV2(R.layout.activity_cache) {

    private val mVB by viewBinding(ActivityCacheBinding::bind, R.id.container)

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        super.afterViewAttach(savedInstanceState)
        initToolBarWithBack("缓存")

        mVB.tvCache.text = getTotalCacheSize()
        mVB.tvCache.click {
            clearAllCache()
        }
    }
}
