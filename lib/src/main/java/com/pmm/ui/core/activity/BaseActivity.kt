package com.pmm.ui.core.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.pmm.ui.core.architecture.mvp.BaseView

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


    override fun getContentView(): ViewGroup = window.decorView.findViewById(android.R.id.content)


}