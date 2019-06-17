package com.weimu.app.universalview.module.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.universalview.core.dialog.ProgressDialog
import com.weimu.universalview.ktx.*
import kotlinx.android.synthetic.main.activity_test.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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

        test()
    }

    private fun test() {
        val a = "2019-05-09T13:52:56.41+08:00"
        Log.e("c", a)
        val b = a.utc2LocalV2()
        Log.e("c", b)
    }


    fun String.utc2LocalV2(localFormatStr: String = "yyyy-MM-dd HH:mm:ss", utcFormatStr: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'+08:00'"): String {
        if (this.isBlank()) return ""
        val utcFormat = SimpleDateFormat(utcFormatStr, Locale.getDefault()).apply {
            this.timeZone = TimeZone.getTimeZone("UTC")
        }
        val localFormat = SimpleDateFormat(localFormatStr, Locale.getDefault()).apply {
            this.timeZone = TimeZone.getDefault()
        }
        try {
            return localFormat.format(utcFormat.parse(this))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }
}
