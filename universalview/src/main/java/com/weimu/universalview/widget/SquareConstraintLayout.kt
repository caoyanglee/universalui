package com.weimu.universalview.widget

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet

//方形约束布局
class SquareConstraintLayout : ConstraintLayout {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Set a square layout.
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}
