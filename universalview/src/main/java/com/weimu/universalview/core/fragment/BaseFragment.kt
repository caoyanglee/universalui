package com.weimu.universalview.core.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weimu.universalib.ktx.toast
import com.weimu.universalib.origin.mvp.BaseView
import com.weimu.universalib.helper.SnackBarCenter
import com.weimu.universalview.core.dialog.ProgressDialog

/**
 * Author:你需要一台永动机
 * Date:2018/3/8 11:15
 * Description:
 */

abstract class BaseFragment : Fragment(), BaseView {


    private lateinit var mActivity: AppCompatActivity
    private var mContentView: ViewGroup? = null
    private var isInit = false//是否初始化
    protected var isShow = false//是否显示在用户  viewPager


    @LayoutRes
    protected abstract fun getLayoutResID(): Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mActivity = context as AppCompatActivity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeViewAttachBaseViewAction(savedInstanceState)
        beforeViewAttach(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContentView = injectView(inflater, container, savedInstanceState) as ViewGroup
        //设置视图
        return mContentView
    }

    open protected fun injectView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutInflater.from(mActivity).inflate(getLayoutResID(), container, false) as ViewGroup
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        afterViewAttachBaseViewAction(savedInstanceState)
        afterViewAttach(savedInstanceState)
        isInit = true

        if (isShow) onViewPageVisible()
    }

    //baseView的操作
    protected abstract fun beforeViewAttachBaseViewAction(savedInstanceState: Bundle?)

    protected abstract fun afterViewAttachBaseViewAction(savedInstanceState: Bundle?)

    //子View的操作
    protected open fun beforeViewAttach(savedInstanceState: Bundle?) {}

    protected open fun afterViewAttach(savedInstanceState: Bundle?) {}

    //各种上下文的获取
    override fun getAppDataContext(): Context = mActivity.applicationContext

    override fun getContext(): Context = mActivity

    override fun getCurrentActivity(): AppCompatActivity = mActivity

    override fun getContentView(): ViewGroup = mContentView as ViewGroup

    //吐司通知&普通的MD弹窗
    override fun toastSuccess(message: CharSequence) { toast(message) }

    override fun toastFail(message: CharSequence) { toast(message) }


    override fun showProgressBar() { ProgressDialog.show(getContext()) }

    override fun showProgressBar(message: CharSequence) { ProgressDialog.show(getContext(), content = message.toString()) }

    override fun hideProgressBar() { ProgressDialog.hide() }

    //snaker
    override fun showSnackBar(message: CharSequence) { SnackBarCenter.show(getContentView(), message) }

    //打开Activity
    override fun startActivity(intent: Intent) {
        mActivity.startActivity(intent)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        mActivity.startActivityForResult(intent, requestCode)
    }

    //Fragment直接切换show和hide
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isInit) return
        if (hidden) onHide() else onShow()
    }

    //fragment显示
    open fun onShow() {}

    //fragment隐藏
    open fun onHide() {}

    //ViewPager的切换
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isShow = isVisibleToUser
        if (isInit && isShow) {
            onViewPageVisible()
        }
    }

    //fragment隐藏
    open fun onViewPageVisible() {}

}
