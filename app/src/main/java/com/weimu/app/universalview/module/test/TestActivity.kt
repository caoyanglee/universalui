package com.weimu.app.universalview.module.test

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.app.universalview.view.dialog.FullScreenDialog
import com.weimu.universalib.ktx.formatDate
import com.weimu.universalib.ktx.getCurrentTimeStamp
import com.weimu.universalview.ktx.hideKeyBoard
import com.weimu.universalview.ktx.setOnClickListenerPro
import com.weimu.universalview.ktx.showKeyBoard
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseViewActivity() {


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, TestActivity::class.java)
        }
    }

    override fun getLayoutResID(): Int = R.layout.activity_test

    override fun beforeViewAttach(savedInstanceState: Bundle?) {}

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        mBtnShowHtml.setOnClickListenerPro {
            FullScreenDialog().show(this@TestActivity)
//            showKeyBoard(mEtTest)
            getCurrentTimeStamp().formatDate()
        }

        mBtnAddImage.setOnClickListenerPro {
            hideKeyBoard(mEtTest)
        }
    }
}
