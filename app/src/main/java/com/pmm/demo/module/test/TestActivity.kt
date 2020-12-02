package com.pmm.demo.module.test

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.afollestad.assent.Permission
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivity
import com.pmm.ui.helper.AndroidBug5497Workaround
import com.pmm.ui.ktx.*
import kotlinx.android.synthetic.main.activity_test.*


class TestActivity : BaseViewActivity() {

    private val vm by lazy { ViewModelProvider(this).get(TestViewModel::class.java) }

    override fun getLayoutResID(): Int = R.layout.activity_test

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        mToolBar.with(this)
                .navigationIcon {
                    this.setImageResource(R.drawable.universal_arrow_back_white)
                    this.click { onBackPressed() }
                }
                .centerTitle {
                    this.text = "测试"
                }


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
                    Permission.WRITE_EXTERNAL_STORAGE,
                    Permission.READ_EXTERNAL_STORAGE,
                    Permission.CAMERA,
                    allGrantedCallback = { toast("拿到权限了") },
                    allDeniedCallback = {
                        toast("所有权限都拒绝了")
                        true
                    },
                    permanentlyDeniedCallback = {
                        toast("有权限被永久拒绝了")
                        true
                    },
                    message = "您需要给小盛权限，才能正常访问存储文件哦"
            )
        }
        AndroidBug5497Workaround.assistActivity(this, true)
    }

    override fun onResume() {
        super.onResume()
        vNavigationBar.text = "导航条高度：${px2dip(getNavigationBarHeight().toFloat())}单位"
    }
}
