package com.weimu.universalview.core.architecture.mvp

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle

/**
 * Author:你需要一台永动机
 * Date:2018/3/8 10:28
 * Description:
 */

interface BaseView {

    //Context & Activity & ContentView
    fun getContext(): Context

    fun getCurrentActivity(): AppCompatActivity

    fun getContentView(): ViewGroup

    //Toast
    fun toastSuccess(message: CharSequence)

    fun toastFail(message: CharSequence)

    //SnackBar
    fun showSnackBar(message: CharSequence)

    //ProgressBar
    fun showProgressBar()

    fun showProgressBar(message: CharSequence)

    fun hideProgressBar()

    //Lifecycle
    fun getLifeCycle(): Lifecycle

    //Back
    fun back()

}
