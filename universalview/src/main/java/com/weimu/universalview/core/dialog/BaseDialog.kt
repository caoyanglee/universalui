package com.weimu.universalview.core.dialog

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

/**
 * Author:你需要一台永动机
 * Date:2019/4/9 23:26
 * Description:专门处理弹窗式Dialog
 */
abstract class BaseDialog : DialogFragment() {
    var onDialogButtonListener: OnDialogButtonListener? = null
    var onDialogActionListener: OnDialogListener? = null

    protected var mContentView: ViewGroup? = null

    protected abstract fun getTagName(): String

    @LayoutRes
    protected abstract fun getLayoutResID(): Int

    //修改宽度使用这个方法
    protected open fun getViewWidth() = WindowManager.LayoutParams.MATCH_PARENT

    protected open fun getViewHeight() = WindowManager.LayoutParams.WRAP_CONTENT


    override fun onStart() {
        super.onStart()
        dialog?.apply {
            val window = this.window
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val windowParams = window.attributes
            windowParams.width = getViewWidth()
            windowParams.height = getViewHeight()
            //windowParams.dimAmount = 0.0f;//设置Dialog外其他区域的alpha值
            window.attributes = windowParams
        }
    }

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContentView = LayoutInflater.from(context).inflate(getLayoutResID(), container, false) as ViewGroup
        //设置视图
        return mContentView
    }

    fun show(fragment: Fragment): BaseDialog {
        showDialog(fragment.childFragmentManager, getTagName())
        return this
    }

    fun show(activity: AppCompatActivity): BaseDialog {
        showDialog(activity.supportFragmentManager, getTagName())
        return this
    }

    private fun showDialog(manager: FragmentManager, tag: String) {
        try {
            show(manager, tag)
        } catch (e: IllegalStateException) {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        }
    }

    //屏蔽DialogFragment的方法
    final override fun show(manager: FragmentManager?, tag: String?) {
        super.show(manager, tag)
    }

    //屏蔽DialogFragment的方法
    final override fun show(transaction: FragmentTransaction?, tag: String?): Int {
        return super.show(transaction, tag)
    }

    interface OnDialogButtonListener {
        fun onPositive(dialog: BaseDialog)

        fun onNegative(dialog: BaseDialog) {}
    }

    //执行positive的操作
    protected fun actionPositiveClick() {
        dismiss()
        onDialogButtonListener?.onPositive(this)

    }

    //执行negative的操作
    protected fun actionNegativeClick() {
        dismiss()
        onDialogButtonListener?.onNegative(this)
    }


    interface OnDialogListener {
        fun onCancel() {}

        fun onDismiss() {}
    }


    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        onDialogActionListener?.onCancel()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        onDialogActionListener?.onDismiss()
    }

}
