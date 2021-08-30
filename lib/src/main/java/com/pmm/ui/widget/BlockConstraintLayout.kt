package com.pmm.ui.widget

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Author:你需要一台永动机
 * Date:2018/12/21 18:20
 * Description:屏蔽所有的子类点击
 */
class BlockConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }
}