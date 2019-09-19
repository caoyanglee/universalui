package com.weimu.universalview.widget

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView

/**
 * Author:你需要一台永动机
 * Date:2018/10/27 20:47
 * Description:
 */
class SquareCardView : CardView {


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Set a square layout.
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}