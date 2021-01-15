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
abstract class BaseActivityV2(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        //superCreate之前
        beforeSuperCreate(savedInstanceState)

        //绑定视图前
        beforeViewAttachBaseViewAction(savedInstanceState)
        beforeViewAttach(savedInstanceState)

        super.onCreate(savedInstanceState)

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

    fun getContentView(): ViewGroup = window.decorView.findViewById(android.R.id.content)


}