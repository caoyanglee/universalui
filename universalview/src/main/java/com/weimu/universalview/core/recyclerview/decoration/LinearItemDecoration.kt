package com.weimu.universalview.core.recyclerview.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Author:你需要一台永动机
 * Date:2019/3/17 01:21
 * Description:竖直和横向
 */
class LinearItemDecoration(
        var context: Context,
        var dividerSize: Int = 0,
        var orientation: Int = VERTICAL_LIST,
        var dividerDrawable: Drawable = ColorDrawable(Color.TRANSPARENT),
        var haveHeader: Boolean = false,//是否拥用头部
        var haveFooter: Boolean = false//是否拥有底部
) : RecyclerView.ItemDecoration() {

    companion object {
        const val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
        const val VERTICAL_LIST = LinearLayoutManager.VERTICAL
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (this.orientation == VERTICAL_LIST)
            drawVertical(c, parent)
        else
            drawHorizontal(c, parent)
    }

    private fun drawVertical(c: Canvas?, parent: RecyclerView?) {
        val left: Int = parent?.paddingLeft!!
        val right: Int = parent.width - parent.paddingRight

        val childCount = parent.childCount

        for (i in (if (haveHeader) 1 else 0) until (if (haveFooter) childCount + 1 else childCount)) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + dividerSize
            dividerDrawable.setBounds(left, top, right, bottom)
            dividerDrawable.draw(c)
        }
    }

    private fun drawHorizontal(c: Canvas?, parent: RecyclerView?) {
        val top = parent?.paddingTop!!
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in (if (haveHeader) 1 else 0) until (if (haveFooter) childCount + 1 else childCount)) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + dividerSize
            dividerDrawable.setBounds(left, top, right, bottom)
            dividerDrawable.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position: Int = parent.getChildAdapterPosition(view)
        if (this.orientation == VERTICAL_LIST) {
            if (position == 0) return
            if (position == 1 && haveHeader) return
            if (position == parent.childCount - 1 && haveFooter) return
            outRect.top = dividerSize
        } else {
            if (position == 0) return
            if (position == 1 && haveHeader) return
            if (position == parent.childCount - 1 && haveFooter) return
            outRect.left = dividerSize
        }
    }

}