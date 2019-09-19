package com.pmm.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

//方形相对布局
class SquareRelativeLayout : RelativeLayout {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Set a square layout.
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}
