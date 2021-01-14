package com.pmm.demo.view.dialog

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import by.kirich1409.viewbindingdelegate.dialogViewBinding
import com.pmm.demo.R
import com.pmm.demo.databinding.DialogFullScreenBinding
import com.pmm.ui.core.dialog.BaseDialog
import com.pmm.ui.core.StatusNavigationBar

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

    override fun getGravity(): Int = Gravity.BOTTOM

    override fun getWindowAnimation(): Int = R.style.BottomToUpDialog

    private val mVB by dialogViewBinding(DialogFullScreenBinding::bind, R.id.container)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        StatusNavigationBar.setColor(dialog?.window, Color.WHITE)
        StatusNavigationBar.setLightMode(dialog?.window)
        mVB.mToolbar.with(activity).apply {
            toolbarHeight = 0
            showStatusView = true
        }
    }
}