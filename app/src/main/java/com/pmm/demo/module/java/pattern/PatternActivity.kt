package com.pmm.demo.module.java.pattern

import android.os.Bundle
import android.util.Log
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivityV2
import com.pmm.demo.base.initToolBarWithBack
import java.util.regex.Pattern

/**
 * Java的正则表达式
 */
class PatternActivity : BaseViewActivityV2(R.layout.activity_pattern) {

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initViews()
        initContent()
    }


    private fun initViews() {
        initToolBarWithBack("正则表达式")
    }

    private fun initContent() {
        val content = "caoyanglee123_"
        val pattern = "^[A-Za-z_0-9]+$"
        val isMatch = Pattern.matches(pattern, content)
        Log.e(this::class.java.simpleName, "是否校验成功 isMatch=${isMatch}")
    }

}
