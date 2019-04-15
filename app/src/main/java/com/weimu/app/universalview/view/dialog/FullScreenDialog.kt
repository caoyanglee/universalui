package com.weimu.app.universalview.view.dialog

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import com.weimu.app.universalview.R
import com.weimu.universalview.core.dialog.BaseDialog
import com.weimu.universalview.core.toolbar.StatusBarManager
import kotlinx.android.synthetic.main.dialog_full_screen.*

/**
 * Author:你需要一台永动机
 * Date:2019/4/9 23:43
 * Description:
 */
class FullScreenDialog : BaseDialog() {

    override fun getTagName(): String = "TestDialog"

    override fun getViewWidth(): Int = WindowManager.LayoutParams.MATCH_PARENT

    override fun getViewHeight(): Int = WindowManager.LayoutParams.MATCH_PARENT

    override fun getLayoutResID(): Int = R.layout.dialog_full_screen

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        StatusBarManager.setColor(dialog.window, Color.WHITE)
        StatusBarManager.setLightMode(dialog.window)

        mToolbar.with(activity).apply {
            toolbarHeight = 0f
            showStatusView = true
        }
    }
}