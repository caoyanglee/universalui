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
class AppBarLayoutCompat @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppBarLayout(context, attrs, defStyleAttr) {

    init {
        post {
            val layoutParams = this.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = layoutParams.behavior as Behavior?

            behavior?.setDragCallback(object : DragCallback() {
                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                    return true
                }
            })
        }
    }
}