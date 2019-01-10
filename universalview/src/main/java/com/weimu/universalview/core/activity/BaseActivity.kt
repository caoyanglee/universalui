package com.weimu.universalview.core.activity

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.weimu.universalib.ktx.toast
import com.weimu.universalib.origin.mvp.BaseView
import com.weimu.universalib.helper.SnackBarCenter
import com.weimu.universalib.origin.view.ProgressDialog

/**
 * Author:你需要一台永动机
 * Date:2018/4/8 17:18
 * Description:Activity的基类
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {


    @LayoutRes
    protected abstract fun getLayoutResID(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beforeViewAttachBaseViewAction(savedInstanceState)
        beforeViewAttach(savedInstanceState)
        //绑定视图前
        setContentView(getLayoutResID())//设置视图
        //绑定视图后
        afterViewAttachBaseViewAction(savedInstanceState)
        afterViewAttach(savedInstanceState)
    }

    //baseView的操作
    open fun beforeViewAttachBaseViewAction(savedInstanceState: Bundle?) {}

    open fun afterViewAttachBaseViewAction(savedInstanceState: Bundle?) {}

    //子View的操作
    protected open fun beforeViewAttach(savedInstanceState: Bundle?) {}

    protected open fun afterViewAttach(savedInstanceState: Bundle?) {}

    //各种上下文的获取
    override fun getContext(): Context = this

    override fun getContentView(): ViewGroup = window.decorView.findViewById(android.R.id.content)

    override fun getAppDataContext(): Context = this.getAppDataContext()

    override fun getCurrentActivity(): AppCompatActivity = this;

    //吐司通知&普通的MD弹窗
    override fun toastSuccess(message: CharSequence) { toast(message) }

    override fun toastFail(message: CharSequence) { toast(message) }


    override fun showProgressBar() { ProgressDialog.show(getContext()) }

    override fun showProgressBar(message: CharSequence) { ProgressDialog.show(getContext(), content = message.toString()) }

    override fun hideProgressBar() { ProgressDialog.hide() }

    //snaker
    override fun showSnackBar(message: CharSequence) { SnackBarCenter.show(getContentView(), message) }




}