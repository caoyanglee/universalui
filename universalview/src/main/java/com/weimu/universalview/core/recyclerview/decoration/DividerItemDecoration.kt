package com.weimu.universalview.core.recyclerview.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.weimu.universalib.ktx.dip2px

/**
 * @project KotLinProject
 * @author 艹羊
 * @date 2017/6/16 上午9:43
 * @description
 */
class DividerItemDecoration : RecyclerView.ItemDecoration {
    val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
    val VERTICAL_LIST = LinearLayoutManager.VERTICAL

    val ATTRS = IntArray(android.R.attr.listDivider)

    var mDivider: Drawable? = null
    var mSize = 1
    var mOrientation = VERTICAL_LIST


    var isAddHead = false
    var isAddFoot = false


    constructor(context: Context, orientation: Int, dividerSize: Int, isAddHead: Boolean = false, isAddFoot: Boolean = false) {
        this.isAddHead = isAddHead
        this.isAddFoot = isAddFoot
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        if (mDivider == null) {
            Log.w("weimu", "@android:attr/listDivider was not set in the theme used for this " + "DividerItemDecoration. Please set that attribute all call setDrawable()")
        }
        a.recycle()

        setOrientation(orientation)
        mSize = context.dip2px(dividerSize.toFloat())
    }


    fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST)
            throw IllegalArgumentException("invalid orientation")
        mOrientation = orientation
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?) {
        super.onDraw(c, parent)
        if (mOrientation == VERTICAL_LIST)
            drawVertical(c, parent)
        else
            drawHorizontal(c, parent)
    }

    fun drawVertical(c: Canvas?, parent: RecyclerView?) {
        val left: Int = parent?.paddingLeft!!
        val right: Int = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in (if (isAddHead) 1 else 0)..(if (isAddFoot) childCount - 1 else childCount) - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider?.intrinsicHeight as Int
            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(c)
        }
    }

    fun drawHorizontal(c: Canvas?, parent: RecyclerView?) {
        val top = parent?.paddingTop!!
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in 0..childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + mDivider?.intrinsicHeight as Int
            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect?, itemPosition: Int, parent: RecyclerView?) {
        if (mOrientation == VERTICAL_LIST) {
            outRect?.set(0, 0, 0, mSize)
        } else {
            outRect?.set(0, 0, mSize, 0)
        }
    }

}