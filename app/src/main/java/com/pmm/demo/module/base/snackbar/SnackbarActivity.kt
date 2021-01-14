package com.pmm.demo.module.base.snackbar

import android.os.Bundle
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivity
import com.pmm.metro.annotatoin.Station
import com.pmm.ui.ktx.*
import kotlinx.android.synthetic.main.activity_snackbar.*

@Station("/snackbar")
class SnackbarActivity : BaseViewActivity() {

    override fun getLayoutResID(): Int = R.layout.activity_snackbar


    override fun afterViewAttach(savedInstanceState: Bundle?) {
        btn1.click {
            Snackbar.make(getContentView(), "测试信号", Snackbar.LENGTH_SHORT).apply {
                val snackbarView = this.view as FrameLayout//获取SnackBar布局View实例
                snackbarView.bg(ViewBgOption().apply {
                    this.radius = dip2px(8f).toFloat()
                })
                snackbarView.setMargins(b = dip2px(16f), l = dip2px(16f), r = dip2px(16f))
                this.setAction("点我") {
                    toast("什么鬼")
                }
            }.show()
        }
    }
}
