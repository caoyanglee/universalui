package com.pmm.demo.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import com.bumptech.glide.Glide
import com.pmm.ui.core.activity.BaseActivity
import com.pmm.ui.core.architecture.mvp.BaseView
import com.pmm.ui.core.StatusNavigationBar

/**
 * Author:你需要一台永动机
 * Date:2018/4/8 17:18
 * Description:Activity的基类
 */
@Deprecated("2021年9月份后删除，请使用BaseFragmentV2")
abstract class BaseViewActivity : BaseActivity(), BaseView, BaseViewInit {

    //superCreate之前
    final override fun beforeSuperCreate(savedInstanceState: Bundle?) {
        StatusNavigationBar.setStatusNavigationBarTransparent(window)
        StatusNavigationBar.setDarkMode(window, true)
    }

    //视图加载前
    final override fun beforeViewAttachBaseViewAction(savedInstanceState: Bundle?) {}

    //视图加载后
    final override fun afterViewAttachBaseViewAction(savedInstanceState: Bundle?) {
        try {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//请求为竖直屏幕
        } catch (e: Exception) {
            //Logger.e("Only fullscreen activities can request orientation")
        }
    }

    override fun onResume() {
        super.onResume()
        Glide.with(this).resumeRequests()
    }

    override fun onPause() {
        super.onPause()
        Glide.with(this).pauseRequests()
    }


}