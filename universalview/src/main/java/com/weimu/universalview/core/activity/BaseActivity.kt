package com.weimu.universalview.core.activity

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.weimu.universalview.core.architecture.mvp.BaseView
import com.weimu.universalview.core.dialog.ProgressDialog
import com.weimu.universalview.core.snackerbar.SnackBarCenter
import com.weimu.universalview.ktx.toast

/**
 * Author:你需要一台永动机
 * Date:2018/4/8 17:18
 * Description:Activity的基类
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {

    protected open fun getLayoutUI(): ViewGroup? = null//优先使用这个，没有在拿getLayoutResID的视图

    @LayoutRes
    protected open fun getLayoutResID(): Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //绑定视图前
        beforeViewAttachBaseViewAction(savedInstanceState)
        beforeViewAttach(savedInstanceState)

        //设置视图
        if (getLayoutUI() != null) {
            setContentView(getLayoutUI())
        } else if (getLayoutResID() != -1) {
            setContentView(getLayoutResID())
        }

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

    override fun getCurrentActivity(): AppCompatActivity = this

    //吐司通知&普通的MD弹窗
    override fun toastSuccess(message: CharSequence) {
        toast(message)
    }

    override fun toastFail(message: CharSequence) {
        toast(message)
    }

    override fun showProgressBar() {
        ProgressDialog.show(getContext())
    }

    override fun showProgressBar(message: CharSequence) {
        ProgressDialog.show(getContext(), content = message.toString())
    }

    override fun hideProgressBar() {
        ProgressDialog.hide()
    }

    override fun getLifeCycle(): Lifecycle = lifecycle

    //showSnackBar
    override fun showSnackBar(message: CharSequence) {
        SnackBarCenter.show(getContentView(), message)
    }


}