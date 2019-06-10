package com.weimu.universalview.core.fragment

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weimu.universalview.core.architecture.mvp.BaseView
import com.weimu.universalview.core.dialog.ProgressDialog
import com.weimu.universalview.core.snackerbar.SnackBarCenter
import com.weimu.universalview.ktx.toast

/**
 * Author:你需要一台永动机
 * Date:2019/3/15 17:07
 * Description:
 */
abstract class BaseFragment : Fragment(), BaseView {
    private lateinit var mActivity: AppCompatActivity
    private var mContentView: ViewGroup? = null
    private var isPrepare = false//是否初始化
    private var isViewPagerShow = false
    private var isFirstShow = false

    protected open fun getLayoutUI(): ViewGroup? = null//优先使用这个，没有在拿getLayoutResID的视图

    @LayoutRes
    protected open fun getLayoutResID(): Int = -1

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
        mContentView = getLayoutUI()//设置视图

        if (mContentView == null && getLayoutResID() != -1)
            mContentView = LayoutInflater.from(mActivity).inflate(getLayoutResID(), container, false) as ViewGroup

        return mContentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        afterViewAttachBaseViewAction(savedInstanceState)
        afterViewAttach(savedInstanceState)
        isPrepare = true
        onViewPagerShow(isViewPagerShow)
        if (isViewPagerShow && !isFirstShow) {
            onViewPagerFirstShow()
            isFirstShow = true
        }
    }

    //baseView的操作
    protected open fun beforeViewAttachBaseViewAction(savedInstanceState: Bundle?) {}

    protected open fun afterViewAttachBaseViewAction(savedInstanceState: Bundle?) {}

    //子View的操作
    protected open fun beforeViewAttach(savedInstanceState: Bundle?) {}

    protected open fun afterViewAttach(savedInstanceState: Bundle?) {}

    //各种上下文的获取
    override fun getContext(): Context = mActivity

    override fun getCurrentActivity(): AppCompatActivity = mActivity

    override fun getContentView(): ViewGroup = mContentView as ViewGroup

    //吐司通知&普通的MD弹窗
    override fun toastSuccess(message: CharSequence) {
        toast(message)
    }

    override fun toastFail(message: CharSequence) {
        toast(message)
    }

    override fun showProgressBar() {
        ProgressDialog.show(context)
    }

    override fun showProgressBar(message: CharSequence) {
        ProgressDialog.show(context, message = message.toString())
    }

    override fun hideProgressBar() {
        ProgressDialog.hide()
    }

    override fun getLifeCycle(): Lifecycle = lifecycle

    override fun back() {
        mActivity.onBackPressed()
    }

    //showSnackBar
    override fun showSnackBar(message: CharSequence) {
        SnackBarCenter.show(getContentView(), message)
    }

    //打开Activity
    override fun startActivity(intent: Intent) {
        mActivity.startActivity(intent)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        mActivity.startActivityForResult(intent, requestCode)
    }

    //FrameLayout的切换
    final override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isPrepare) return
        onFrameLayoutShow(!hidden)
    }

    //fragment 在FrameLayout的隐藏显示
    open fun onFrameLayoutShow(show: Boolean) {}

    //ViewPager的切换
    final override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isViewPagerShow = isVisibleToUser
        if (!isPrepare) return
        onViewPagerShow(isViewPagerShow)
        if (isViewPagerShow && !isFirstShow) {
            onViewPagerFirstShow()
            isFirstShow = true
        }
    }

    //fragment 在ViewPager的隐藏显示
    open fun onViewPagerShow(show: Boolean) {}

    //fragment 在啊ViewPager的第一次显示
    open fun onViewPagerFirstShow() {}

}
