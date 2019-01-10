package com.weimu.universalview.core.recyclerview.layoutmanager

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log

/**
 * 解决问题  列表布局专用
 * RecyclerView Bug：IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter的解决方案
 */
class WrapContentGridLayoutManager : GridLayoutManager {


    constructor(context: Context?, spanCount: Int) : super(context, spanCount)
    constructor(context: Context?, spanCount: Int, orientation: Int, reverseLayout: Boolean) : super(context, spanCount, orientation, reverseLayout)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Log.e("weimu", "meet a IOOBE in RecyclerView")
        }


    }
}