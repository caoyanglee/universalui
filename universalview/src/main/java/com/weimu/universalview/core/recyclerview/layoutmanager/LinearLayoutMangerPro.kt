package com.weimu.universalview.core.recyclerview.layoutmanager


import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * 解决问题  列表布局专用
 * RecyclerView Bug：IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter的解决方案
 */
class LinearLayoutMangerPro(
        context: Context?,
        orientation: Int = VERTICAL,
        reverseLayout: Boolean = false,
        private val canScrollVertically: Boolean = true//是否可以竖直滚动
) : LinearLayoutManager(context, orientation, reverseLayout) {

    private val TAG = "LinearLayoutMangerPro"

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Log.e(TAG, "meet a IOOBE in RecyclerView")
        }
    }

    override fun canScrollVertically(): Boolean {
        return canScrollVertically
    }
}