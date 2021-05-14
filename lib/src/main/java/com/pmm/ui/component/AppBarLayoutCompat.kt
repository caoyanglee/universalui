package com.pmm.ui.component

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.Behavior.DragCallback


/**
 * Author:你需要一台永动机
 * Date:2021/5/14 09:35
 * Description:
 * CoordinatorLayout和AppBarLayout 嵌套滑动时有时appbarlayout无法滑动问题
 */
class AppBarLayoutCompat : AppBarLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        post {
            val layoutParams = this.getLayoutParams() as CoordinatorLayout.LayoutParams
            val behavior = layoutParams.behavior as Behavior

            behavior.setDragCallback(object : DragCallback() {
                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                    return true
                }
            })
        }
    }

}