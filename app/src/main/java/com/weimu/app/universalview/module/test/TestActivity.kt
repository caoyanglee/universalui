package com.weimu.app.universalview.module.test

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.app.universalview.module.fragment.viewpager.ViewPagerActivity
import com.weimu.universalview.ktx.*
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

        mBtn1.setOnClickListenerPro {
            setResult(Activity.RESULT_OK)
            onBackPressed()
        }

    }
}
