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
        var needHeaderDivider: Boolean = false,//是否拥用头部
        var needFooterDivider: Boolean = false//是否拥有底部
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
        val itemCount = parent.adapter?.itemCount ?: 0
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val positionInAdapter = parent.getChildAdapterPosition(child)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            var bottom = top + when {
                positionInAdapter == 0 && needHeaderDivider -> 0
                positionInAdapter == itemCount - 1 && needFooterDivider -> 0
                positionInAdapter == itemCount - 2 && needFooterDivider -> 0
                else -> dividerSize
            }
            dividerDrawable.setBounds(left, top, right, bottom)
            dividerDrawable.draw(c)
        }
    }

    private fun drawHorizontal(c: Canvas?, parent: RecyclerView?) {
        val top = parent?.paddingTop!!
        val bottom = parent.height - parent.paddingBottom
        val itemCount = parent.adapter?.itemCount ?: 0
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val positionInAdapter = parent.getChildAdapterPosition(child)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + when {
                positionInAdapter == 0 && needHeaderDivider -> 0
                positionInAdapter == itemCount - 1 && needFooterDivider -> 0
                positionInAdapter == itemCount - 2 && needFooterDivider -> 0
                else -> dividerSize
            }
            dividerDrawable.setBounds(left, top, right, bottom)
            dividerDrawable.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position: Int = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0
        val offset = when {
            position == 0 && needHeaderDivider -> 0
            position == itemCount - 2 && needFooterDivider -> 0
            position == itemCount - 1 -> 0
            else -> dividerSize
        }
        if (this.orientation == VERTICAL_LIST) {
            outRect.bottom = offset
        } else {
            outRect.right = offset
        }
    }

}