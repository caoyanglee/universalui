package com.weimu.universalview.widget.swipelist

import android.content.Context
import android.graphics.Rect
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

/**
 * Author:你需要一台永动机
 * Date:2019/3/17 02:22
 * Description:可滑动布局
 */
class SwipeItemLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {


    private var menu: View? = null//菜单  视图
    private var content: View? = null//内容   视图

    private var canSwip = true

    private val dragHelper: ViewDragHelper
    var isOpen: Boolean = false
        private set//是否打开
    var state: Int = 0
        private set//当前状态

    private val rightCallback = object : ViewDragHelper.Callback() {

        // 触摸到View的时候就会回调这个方法。
        // return true表示抓取这个View。
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return content === child
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return super.clampViewPositionVertical(child, top, dy)
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            if (!canSwip) {
                return 0
            }
            return if (left > 0) 0 else if (left < -menu!!.width) -menu!!.width else left
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            // x轴移动速度大于菜单一半，或者已经移动到菜单的一般之后，展开菜单

            //Log.e("ViewDragHelper  xvel=" + xvel + "  yvel=" + yvel);
            if (isOpen && canSwip) {
                if (xvel > menu!!.width || -content!!.left < menu!!.width / 2) {
                    close()
                } else {
                    open()
                }
            } else {
                if (-xvel > menu!!.width || -content!!.left > menu!!.width / 2) {
                    open()
                } else {
                    close()
                }
            }
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return 1
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return 1
        }

        override fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            this@SwipeItemLayout.state = state
        }
    }

    private val outRect = Rect()

    val menuRect: Rect
        get() {
            menu!!.getHitRect(outRect)
            return outRect
        }


    init {
        dragHelper = ViewDragHelper.create(this, rightCallback)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        menu = getChildAt(0)
        content = getChildAt(1)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    fun close() {
        dragHelper.smoothSlideViewTo(content!!, 0, 0)
        isOpen = false
        invalidate()
    }

    fun open() {
        dragHelper.smoothSlideViewTo(content!!, -menu!!.width, 0)
        isOpen = true
        invalidate()
    }

    override fun computeScroll() {
        super.computeScroll()
        if (dragHelper.continueSettling(true)) {
            invalidate()
        }
    }

    override fun setOnClickListener(l: View.OnClickListener?) {
        content!!.setOnClickListener(l)
    }

    fun setSwipeAble(canSwip: Boolean) {
        this.canSwip = canSwip
    }


}
