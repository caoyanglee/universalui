package com.pmm.ui.core.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * Author:你需要一台永动机
 * Date:2018/4/8 17:18
 * Description:Activity的基类
 */
@Deprecated("2021年9月份后删除，请使用BaseFragmentV2")
abstract class BaseActivity : AppCompatActivity() {

    protected open fun getLayoutUI(): ViewGroup? = null//优先使用这个，没有在拿getLayoutResID的视图

    @LayoutRes
    protected open fun getLayoutResID(): Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        //superCreate之前
        beforeSuperCreate(savedInstanceState)

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

    //superCreate之前
    protected open fun beforeSuperCreate(savedInstanceState: Bundle?) {}

    //baseView的操作
    protected open fun beforeViewAttachBaseViewAction(savedInstanceState: Bundle?) {}

    protected open fun afterViewAttachBaseViewAction(savedInstanceState: Bundle?) {}

    //子View的操作
    protected open fun beforeViewAttach(savedInstanceState: Bundle?) {}

    protected open fun afterViewAttach(savedInstanceState: Bundle?) {}

    protected fun getContentView(): ViewGroup = window.decorView.findViewById(android.R.id.content)


}