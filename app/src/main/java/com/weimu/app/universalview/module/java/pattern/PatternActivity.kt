package com.weimu.app.universalview.module.java.pattern

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.universalview.core.toolbar.ToolBarManager
import java.util.regex.Pattern

/**
 * Java的正则表达式
 */
class PatternActivity : BaseViewActivity() {
    override fun getLayoutResID(): Int = R.layout.activity_pattern

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, PatternActivity::class.java)
        }
    }


    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initViews()
        initContent()
    }


    private fun initViews() {
        ToolBarManager.with(this, getContentView())
                .setTitle("正则表达式")
                .setTitleColor(R.color.white)
                .setNavigationIcon(R.drawable.universal_arrow_back_white)
    }

    private fun initContent() {
        val content = "caoyanglee123_"
        val pattern = "^[A-Za-z_0-9]+$"
        val isMatch = Pattern.matches(pattern, content)
        Log.e(this::class.java.simpleName, "是否校验成功 isMatch=${isMatch}")
    }

}
