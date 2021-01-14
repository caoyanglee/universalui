package com.pmm.ui.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.pmm.ui.core.architecture.mvp.BaseView

/**
 * Author:你需要一台永动机
 * Date:1/14/21 2:02 PM
 * Description:
 */
abstract class BaseFragmentV2(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId), BaseView {

    private var isViewPagerShow = false//是否在viewpager显示
    private var isFirstShow = false//第一次显示
    private var isFirstFrameLayoutShow = false////是否是在FrameLayout显示

    var isViewCreated = false //视图是否加载
        private set//不允许进行设置

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeViewAttachBaseViewAction(savedInstanceState)
        beforeViewAttach(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        afterViewAttachBaseViewAction(savedInstanceState)
        afterViewAttach(savedInstanceState)
        isViewCreated = true
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

    //FrameLayout的切换
    final override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isViewCreated) return
        if (!isFirstFrameLayoutShow && !hidden) {
            onFrameLayoutFirstShow()
            isFirstFrameLayoutShow = true
        } else {
            onFrameLayoutShow(!hidden)
        }
    }

    //fragment 在FrameLayout的隐藏显示
    open fun onFrameLayoutShow(show: Boolean) {}

    //fragment 在FrameLayout的第一次显示
    open fun onFrameLayoutFirstShow() {}

    //ViewPager的切换
    final override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isViewPagerShow = isVisibleToUser
        if (!isViewCreated) return
        onViewPagerShow(isViewPagerShow)
        if (isViewPagerShow && !isFirstShow) {
            onViewPagerFirstShow()
            isFirstShow = true
        }
    }

    //fragment 在ViewPager的隐藏显示
    open fun onViewPagerShow(show: Boolean) {}

    //fragment 在ViewPager的第一次显示
    open fun onViewPagerFirstShow() {}

    override fun onDestroyView() {
        super.onDestroyView()
        isViewCreated = false
    }

}
