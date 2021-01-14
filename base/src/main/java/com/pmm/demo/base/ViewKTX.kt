package com.pmm.demo.base

import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.pmm.ui.widget.ToolBarPro

/**
 * Author:你需要一台永动机
 * Date:2019-10-15 16:37
 * Description:
 */


fun AppCompatActivity.initToolBar(title: String? = null) = findViewById<ToolBarPro>(R.id.mToolbar).with(this)
        .centerTitle {
            this.text = intent.getStringExtra("title")
            if (!TextUtils.isEmpty(title)) {
                this.text = title
            }
        }.navigationIcon {}


fun AppCompatActivity.initToolBarWithBack(title: String? = null) = findViewById<ToolBarPro>(R.id.mToolbar).with(this)
        .centerTitle {
            this.text = intent.getStringExtra("title")
            if (!TextUtils.isEmpty(title)) {
                this.text = title
            }
        }.navigationIcon { onBackPressed() }