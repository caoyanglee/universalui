package com.pmm.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * Author:你需要一台永动机
 * Date:2018/11/1 21:13
 * Description: 分割线（横向和竖向）
 */
class Divider : View {


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}