package com.pmm.ui.widget.swipelist

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView

/**
 * Author:你需要一台永动机
 * Date:2019/3/17 02:22
 * Description:配合可滑动的Item SwipeItemLayout
 */
class SwipeRecyclerView : RecyclerView {

    private var startX: Float = 0.toFloat()
    private var startY: Float = 0.toFloat()
    private var touchSlop: Int = 0//点击阀门
    private var isChildHandle: Boolean = false
    private var touchView: View? = null
    private var distanceX: Float = 0.toFloat()
    private var distanceY: Float = 0.toFloat()

    /**
     * 当前手指位置的position(屏幕上显示的第一个Item为0)
     */
    private var touchFrame: Rect? = null

    constructor(context: Context) : super(context) {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.actionMasked
        if (action == MotionEvent.ACTION_DOWN) {
            isChildHandle = false
            // 记录手指按下的位置
            startY = ev.y
            startX = ev.x
            distanceX = 0f
            distanceY = 0f
            // 获取按下的那个View
            val position = pointToPosition(startX.toInt(), startY.toInt())
            touchView = getChildAt(position)

            if (hasChildOpen()) {
                // 如果触摸的不是打开的那个View, 关闭所有View，并且拦截所有事件
                if (touchView != null && touchView is SwipeItemLayout && (touchView as SwipeItemLayout).isOpen) {
                    isChildHandle = true // 将事件交给child！
                } else {
                    closeAllSwipeItem()
                    return false
                }
            }
        }
        // 禁用多点触控
        return if (action == MotionEvent.ACTION_POINTER_DOWN) {
            false
        } else super.dispatchTouchEvent(ev)

    }

    // 处理和侧滑菜单冲突
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // 如果竖向滑动，拦截，否则不拦截。
        val action = ev.actionMasked
        when (action) {
            MotionEvent.ACTION_MOVE -> {
                // 获取当前手指位置
                val endY = ev.y
                val endX = ev.x
                distanceX = Math.abs(endX - startX)
                distanceY = Math.abs(endY - startY)

                // 如果child已经持有事件，那么不拦截它的事件，直接return false；
                if (isChildHandle) {
                    return false
                }

                // 如果X轴位移大于Y轴位移，那么将事件交给child处理。
                if (distanceX > touchSlop && distanceX > distanceY) {
                    isChildHandle = true
                    return false
                }
            }
            MotionEvent.ACTION_UP ->
                // state != 1 没有滑动过, 关闭打开的菜单
                if (touchView != null && touchView is SwipeItemLayout) {
                    val swipeItem = this.touchView as SwipeItemLayout?
                    if (swipeItem!!.isOpen && swipeItem.state != 1) {
                        if (distanceX < touchSlop && distanceY < touchSlop) {
                            swipeItem.close()
                        }
                        val rect = swipeItem.menuRect
                        // 如果不是点击在菜单上，拦截点击事件。
                        if (!(startX > rect.left && startX < rect.right && startY > touchView!!.top && startY < touchView!!.bottom)) {
                            return true  // return true，拦截Item点击事件, 但是菜单能接收到。
                        }
                    }
                }
        }
        return super.onInterceptTouchEvent(ev)
    }

    private fun pointToPosition(x: Int, y: Int): Int {
        var frame = touchFrame
        if (frame == null) {
            touchFrame = Rect()
            frame = touchFrame
        }
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility == View.VISIBLE) {
                child.getHitRect(frame)
                if (frame!!.contains(x, y)) {
                    return i
                }
            }
        }
        return -1
    }

    private fun hasChildOpen(): Boolean {
        val count = childCount
        for (i in count - 1 downTo 0) {
            val child = getChildAt(i)
            if (child != null && child is SwipeItemLayout) {
                if (child.isOpen) {
                    return true
                }
            }
        }
        return false
    }

    private fun closeAllSwipeItem() {
        val count = childCount
        for (i in count - 1 downTo 0) {
            val child = getChildAt(i)
            if (child != null && child is SwipeItemLayout) {
                child.close()
            }
        }
    }
}
