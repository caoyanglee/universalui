package com.weimu.universalview.core.recyclerview.layoutmanager

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 解决问题  列表布局专用
 * RecyclerView Bug：IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter的解决方案
 */
class GridLayoutManagerPro(
        context: Context?,
        spanCount: Int,
        private val canScrollVertically: Boolean = true//是否可以竖直滚动
) : GridLayoutManager(context, spanCount) {

    private val TAG = "GridLayoutManagerPro"

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