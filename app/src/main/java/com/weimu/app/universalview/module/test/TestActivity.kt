package com.weimu.app.universalview.module.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.universalview.core.dialog.ProgressDialog
import com.weimu.universalview.ktx.*
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
        mBtn1.setOnClickListenerPro {
            this@TestActivity.showTimePicker { hourOfDay, minute ->
                toastSuccess("$hourOfDay:$minute")
            }
        }

        mBtn2.setOnClickListenerPro {
            this@TestActivity.showDatePicker { year, month, dayOfMonth ->
                toastSuccess("$year-$month-$dayOfMonth")
            }
        }

        mBtn3.setOnClickListenerPro {
            ProgressDialog.show(this, cancelable = true)
        }

        mBtn4.setOnClickListenerPro {
        }
    }
}
