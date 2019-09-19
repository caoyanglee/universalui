package com.weimu.universalview.core.recyclerview.layoutmanager

import android.content.Context
import android.graphics.PointF
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * Author:你需要一台永动机
 * Date:2019/3/17 02:13
 * Description:控制滑动速度的LinearLayoutManager
 */
class ScrollSpeedLinearLayoutManger(
        private val context: Context,
        private var speed: Float = 0.03f//毫秒  1英寸/1毫秒=MILLISECONDS_PER_INCH
) : LinearLayoutManager(context) {

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        val linearSmoothScroller = object : LinearSmoothScroller(recyclerView.context) {

            override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                return this@ScrollSpeedLinearLayoutManger.computeScrollVectorForPosition(targetPosition)
            }

            //This returns the milliseconds it takes to
            //scroll one pixel.
            //返回滑动一个pixel需要多少毫秒
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float = speed / displayMetrics.density
        }
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

    //自己在这里用density去乘，希望不同分辨率设备上滑动速度相同
    //0.3f是自己估摸的一个值，可以根据不同需求自己修改
    fun setSpeedSlow() {
        speed = context.resources.displayMetrics.density * 0.3f
    }

    fun setSpeedFast() {
        speed = context.resources.displayMetrics.density * 0.03f
    }
}