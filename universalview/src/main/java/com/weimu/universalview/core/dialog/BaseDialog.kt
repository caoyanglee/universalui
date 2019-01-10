package com.weimu.universalview.core.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager

/**
 * Author:你需要一台永动机
 * Date:2018/3/19 10:20
 * Description:
 */

abstract class BaseDialog : DialogFragment() {
    protected var mActivity: AppCompatActivity? = null

    protected abstract fun getTagName(): String

    protected var onDialogButtonListener: OnDialogButtonListener? = null
    protected var onDialogActionListener: OnDialogListener? = null

    //修改宽度使用这个方法
    protected open fun getViewWidth() = WindowManager.LayoutParams.WRAP_CONTENT


    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val window = dialog.window
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val windowParams = window.attributes
            windowParams.width = getViewWidth()
            windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            //windowParams.dimAmount = 0.0f;//设置Dialog外其他区域的alpha值
            window.attributes = windowParams
        }
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mActivity = context as AppCompatActivity?
    }


    override fun onDetach() {
        super.onDetach()
        this.mActivity = null
    }

    //使用上下文显示
    fun show(context: Context): BaseDialog {
        if (context is AppCompatActivity) {
            show(context)
        } else {
            throw IllegalArgumentException("invalid context")
        }
        return this
    }

    @JvmOverloads
    fun show(fragment: Fragment, cancelable: Boolean = true): BaseDialog {
        showPro(fragment.childFragmentManager, getTagName())
        isCancelable = cancelable
        return this
    }


    @JvmOverloads
    fun show(activity: AppCompatActivity, cancelable: Boolean = true): BaseDialog {
        showPro(activity.supportFragmentManager, getTagName())
        isCancelable = cancelable
        return this
    }


    private fun showPro(manager: FragmentManager, tag: String) {
        try {
            show(manager, tag)
        } catch (e: IllegalStateException) {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        }

    }


    interface OnDialogButtonListener {
        fun onPositive(dialog: BaseDialog)

        fun onNegative(dialog: BaseDialog) {}
    }

    fun setOnDialogButtonListener(onDialogButtonListener: OnDialogButtonListener): BaseDialog {
        this.onDialogButtonListener = onDialogButtonListener
        return this
    }

    //执行postive的操作
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

    fun setOnDialogListener(onDialogActionListener: OnDialogListener) {
        this.onDialogActionListener = onDialogActionListener
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        if (onDialogActionListener != null) {
            onDialogActionListener!!.onCancel()
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        if (onDialogActionListener != null) {
            onDialogActionListener!!.onDismiss()
        }
    }

}
