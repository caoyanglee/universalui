package com.weimu.universalview.core.recyclerview.decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Author:你需要一台永动机
 * Date:2019/3/17 02:07
 * Description:RecyclerView的边缘不去计算，边缘处使用padding处理 优先使用此Decoration
 */
class GridItemDecoration(
        var spanCount: Int,//几列
        var hGap: Int = 0,//水平方向的间距
        var vGap: Int = 0//竖直方向的间距
) : RecyclerView.ItemDecoration() {

    //0/4=0  1/4=0 2/4=0 3/4=0
    //4/4=1  5/4=1 6/4=1 5/6=1
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position: Int = parent.getChildAdapterPosition(view)
        if (spanCount == 0 || position < 0) return
        //val childCount = parent.childCount
        val raw = position / spanCount//位于第几行
        val column = position % spanCount//位于第几列

        outRect.left = (column * (hGap.toFloat() / spanCount)).toInt()
        outRect.right = (hGap - (column + 1) * (hGap.toFloat() / spanCount)).toInt()
        if (raw != 0) outRect.top = vGap
    }
}