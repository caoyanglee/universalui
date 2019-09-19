package com.weimu.universalview.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Author:你需要一台永动机
 * Date:2018/12/21 18:20
 * Description:屏蔽所有的子类点击
 */
class BlockConstraintLayout : ConstraintLayout {


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }
}