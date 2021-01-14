package com.pmm.ui.core.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.pmm.ui.ktx.inflate

/**
 * Author:你需要一台永动机
 * Date:2019/4/9 23:26
 * Description:专门处理弹窗式Dialog
 */
abstract class BaseDialog : DialogFragment() {
    var onPositiveCallback: ((BaseDialog) -> Unit)? = null
    var onNegativeCallback: ((BaseDialog) -> Unit)? = null
    var onCancelCallback: ((BaseDialog) -> Unit)? = null
    var onDismissCallback: ((BaseDialog) -> Unit)? = null

    protected open fun getTagName(): String = this::class.java.simpleName

    @LayoutRes
    protected abstract fun getLayoutResID(): Int

    //修改宽度使用这个方法
    protected open fun getViewWidth() = WindowManager.LayoutParams.MATCH_PARENT

    //设置高度
    protected open fun getViewHeight() = WindowManager.LayoutParams.WRAP_CONTENT

    //设置window显示的动画
    protected open fun getWindowAnimation() = -1//弹窗动画

    //设置gravity
    protected open fun getGravity() = Gravity.NO_GRAVITY

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            val window = this.window
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val windowParams = window.attributes
            windowParams.gravity = getGravity()
            windowParams.width = getViewWidth()
            windowParams.height = getViewHeight()
            if (getWindowAnimation() != -1) windowParams.windowAnimations = getWindowAnimation()
            //windowParams.dimAmount = 0.0f;//设置Dialog外其他区域的alpha值
            window.attributes = windowParams
        }
    }

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //设置视图
        return LayoutInflater.from(context).inflate(getLayoutResID(), container, false) as View
    }

    //使用Fragment
    fun show(fragment: Fragment): BaseDialog {
        show(fragment.childFragmentManager, getTagName())
        return this
    }

    //使用Activity
    fun show(activity: AppCompatActivity): BaseDialog {
        show(activity.supportFragmentManager, getTagName())
        return this
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

//    //屏蔽DialogFragment的方法
//    final override fun show(manager: FragmentManager, tag: String?) {
//        super.show(manager, tag)
//    }
//
//    //屏蔽DialogFragment的方法
//    final override fun show(transaction: FragmentTransaction, tag: String?): Int {
//        return super.show(transaction, tag)
//    }

    //执行positive的操作
    protected fun actionPositiveClick() {
        dismiss()
        onPositiveCallback?.invoke(this)
    }

    //执行negative的操作
    protected fun actionNegativeClick() {
        dismiss()
        onNegativeCallback?.invoke(this)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancelCallback?.invoke(this)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissCallback?.invoke(this)
    }

}
