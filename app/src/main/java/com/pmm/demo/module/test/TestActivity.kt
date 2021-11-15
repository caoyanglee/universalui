package com.pmm.demo.module.test

import android.Manifest
import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivityV2
import com.pmm.demo.databinding.ActivityTestBinding
import com.pmm.ui.helper.AndroidBug5497Workaround
import com.pmm.ui.ktx.*


class TestActivity : BaseViewActivityV2(R.layout.activity_test) {

    private val vm by lazy { ViewModelProvider(this).get(TestViewModel::class.java) }
    private val mVB by viewBinding(ActivityTestBinding::bind, R.id.container)

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        mVB.mToolBar.with(this)
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

        mVB.mBtn1.click {
            setResult(Activity.RESULT_OK)
            onBackPressed()
        }

        mVB.mBtn5.click {
            this.requestPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                    allGrantedCallback = { toast("拿到权限了") },
                    denyCallback = {
                        toast("权限被拒绝了")
                    },
                    message = "您需要给小盛权限，才能正常访问存储文件哦"
            )
        }
        mVB.mBtn6.click {
            setClipContent("123")
        }
        mVB.mBtn7.click {
            setClipContent(null)
        }

        mVB.mBtn8.click {
            toast( "${getClipContent()}")
        }

        AndroidBug5497Workaround.assistActivity(this, true)
    }

    override fun onResume() {
        super.onResume()
        mVB.vNavigationBar.text = "导航条高度：${px2dip(getNavigationBarHeight().toFloat())}单位"
    }
}
