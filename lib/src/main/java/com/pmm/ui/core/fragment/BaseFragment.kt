package com.pmm.ui.core.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.pmm.ui.core.architecture.mvp.BaseView

/**
 * Author:你需要一台永动机
 * Date:2019/3/15 17:07
 * Description:
 */
abstract class BaseFragment : Fragment(), BaseView {
    private var mContentView: ViewGroup? = null


    private var isViewPagerShow = false//是否在viewpager显示
    private var isFirstShow = false//第一次显示

    var isInit = false//Fragment是否已经初始化
    var isViewAttached = false //视图是否加载
        private set//不允许进行设置

    protected open fun getLayoutUI(): ViewGroup? = null//优先使用这个，没有在拿getLayoutResID的视图

    @LayoutRes
    protected open fun getLayoutResID(): Int = -1


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeViewAttachBaseViewAction(savedInstanceState)
        beforeViewAttach(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContentView = getLayoutUI()//设置视图
        if (mContentView == null && getLayoutResID() != -1)
            mContentView = LayoutInflater.from(activity).inflate(getLayoutResID(), container, false) as ViewGroup
        return mContentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        afterViewAttachBaseViewAction(savedInstanceState)
        afterViewAttach(savedInstanceState)
        isInit = true
        isViewAttached = true
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

    override fun getContentView(): ViewGroup = mContentView as ViewGroup

    //FrameLayout的切换
    final override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isViewAttached) return
        onFrameLayoutShow(!hidden)
    }

    //fragment 在FrameLayout的隐藏显示
    open fun onFrameLayoutShow(show: Boolean) {}

    //ViewPager的切换
    final override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isViewPagerShow = isVisibleToUser
        if (!isViewAttached) return
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

    override fun onDestroyView() {
        super.onDestroyView()
        isViewAttached = false
    }

}