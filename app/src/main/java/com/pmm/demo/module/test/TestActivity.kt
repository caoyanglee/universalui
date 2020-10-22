package com.pmm.demo.module.test

import android.Manifest
import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.afollestad.assent.Permission
import com.pmm.demo.R
import com.pmm.ui.ktx.click
import com.pmm.demo.base.BaseViewActivity
import com.pmm.ui.ktx.requestPermission
import com.pmm.ui.ktx.toast
import kotlinx.android.synthetic.main.activity_test.*


class TestActivity : BaseViewActivity() {

    private val vm by lazy { ViewModelProvider(this).get(TestViewModel::class.java) }

    override fun getLayoutResID(): Int = R.layout.activity_test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.test(this)

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

        mBtn5.click {
            this.requestPermission(
                    permissions = arrayOf(
                            Permission.WRITE_EXTERNAL_STORAGE,
                            Permission.READ_EXTERNAL_STORAGE,
                            Permission.CAMERA
                    ),
                    granted = { toast("拿到权限了") },
                    message = "您需要给小盛权限，才能正常访问存储文件哦"
            )
        }
    }
}
