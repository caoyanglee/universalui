package com.pmm.demo.base

import android.app.Activity
import android.text.TextUtils
import com.pmm.ui.widget.ToolBarPro

/**
 * Author:你需要一台永动机
 * Date:2019-10-15 16:37
 * Description:
 */


fun Activity.initToolBar(centerTitle: String? = null) = findViewById<ToolBarPro>(R.id.mToolbar).with(this)
        .centerTitle {
            this.text = intent.getStringExtra("title")
            if (!TextUtils.isEmpty(centerTitle)) {
                this.text = centerTitle
            }
        }.navigationIcon {}