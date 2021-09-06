package com.pmm.demo.module.test

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.afollestad.assent.Permission
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
                    Permission.READ_EXTERNAL_STORAGE,
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


    //检查定位权限
    fun FragmentActivity.requestLocationPermissions(
            allGrantedCallback: () -> Unit,
            permanentlyDeniedCallback: (() -> Boolean)? = null
    ) {
        requestPermission(
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION,
                allGrantedCallback = allGrantedCallback,
                permanentlyDeniedCallback = permanentlyDeniedCallback,
                message = "需要获取您的位置信息才能给你服务"
        )
    }
}
