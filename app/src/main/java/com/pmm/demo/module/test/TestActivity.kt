package com.pmm.demo.module.test

import android.app.Activity
import android.os.Bundle
import com.pmm.ui.ktx.click
import com.weimu.app.universalview.R
import com.pmm.demo.base.BaseViewActivity
import kotlinx.android.synthetic.main.activity_test.*


class TestActivity : BaseViewActivity() {

    override fun getLayoutResID(): Int = R.layout.activity_test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        var loader = MainActivity::class.java.classLoader
//        while (loader != null) {
//            Log.d("pmm", loader.toString())//1
//            loader = loader.parent
//            ClassLoader.getSystemClassLoader()
//        }

        mBtn1.click {
            setResult(Activity.RESULT_OK)
            onBackPressed()
        }

    }
}
