package com.weimu.app.universalview.module.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseViewActivity() {


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, TestActivity::class.java)
        }
    }

    override fun getLayoutResID(): Int = R.layout.activity_test

    override fun beforeViewAttach(savedInstanceState: Bundle?) {

    }

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        lifecycle.addObserver(mRichEditText)
    }
}
