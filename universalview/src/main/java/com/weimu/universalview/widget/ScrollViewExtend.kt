package com.weimu.universalview.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.ScrollView

@Deprecated("建议改成 NestedScrollView")
open class ScrollViewExtend : ScrollView {

    var xDistance: Float = 0f
    var yDIstance: Float = 0f
    var xLast: Float = 0f
    var yLast: Float = 0f


    var downX: Int = 0
    var downY: Int = 0
    var mTouchSlop: Int = 0


    constructor(context: Context) : super(context) {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                yDIstance = 0f
                xDistance = 0f
                xLast = ev.x
                yLast = ev.y
                //判断recyclerView
                downX = ev.rawX.toInt()
                downY = ev.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                //Logger.e("移动 " + " x=" + ev.x + " y=" + ev.y + " rawX=" + ev.rawX + " rawY=" + ev.rawY)
                var curX = ev.x
                var curY = ev.y

                xDistance += Math.abs(curX - xLast)
                yDIstance += Math.abs(curY - yLast)
                xLast = curX
                yLast = curY

                if (xDistance > yDIstance)
                    return false

                var moveY = ev.rawY.toInt()
                if (Math.abs(moveY - downY) > mTouchSlop)
                    return true
            }

        }
        return super.onInterceptTouchEvent(ev)
    }

    var isTouchDown: Boolean = false

    /**
     * 滚动事件
     */
    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        //Logger.d("scrollX " + scrollX + " scrollY " + scrollY + " clampedX " + clampedX + " clampedY " + clampedY);
        onScrollToBottom?.onScroll(scrollX, scrollY, clampedX, clampedY)
        if (!clampedY) isTouchDown = false
        if (scrollY != 0 && clampedY) {
            if (!isTouchDown) {
                isTouchDown = true;
                onScrollToBottom?.onScrollToBottom()
            }
        }
    }

    fun setCustomOnScrollLintener(listener: OnScrollListener) {
        onScrollToBottom = listener
    }

    private var onScrollToBottom: OnScrollListener? = null


    interface OnScrollListener {
        fun onScroll(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean)

        fun onScrollToBottom()
    }

}